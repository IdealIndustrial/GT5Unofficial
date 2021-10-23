package idealindustrial.blocks.plants;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import gnu.trove.list.TLongList;
import gnu.trove.list.array.TLongArrayList;
import gregtech.api.net.GT_Packet_ByteStream;
import idealindustrial.blocks.base.Tile32k;
import idealindustrial.render.CustomRenderer;
import idealindustrial.render.IFastRenderedTileEntity;
import idealindustrial.textures.ITexture;
import idealindustrial.tile.interfaces.ISyncedTileEntity;
import idealindustrial.util.HPoint;
import idealindustrial.util.world.DimensionChunkData;
import idealindustrial.util.world.underbedrock.UnderbedrockLayer;
import idealindustrial.util.world.underbedrock.impl.ChunkData;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;

import java.util.ArrayList;
import java.util.List;

import static gregtech.common.GT_Network.NW;

public class TilePlants extends TileEntity implements Tile32k, IFastRenderedTileEntity, ISyncedTileEntity {

    PlantDef def;
    PlantStats stats;
    int xp, level;
    boolean isRaw = false;

    @Override
    public boolean canUpdate() {
        return false;
    }

    @Override
    public void updateEntity() {
        if (worldObj.isRemote) {
            return;
        }
        if (def == null || stats == null) {
            worldObj.setBlock(xCoord, yCoord, zCoord, Blocks.air);
            return;
        }
        if (isRaw) {
            if (!def.allowedSoil.apply(worldObj.getBlock(xCoord, yCoord - 1, zCoord), worldObj.getBlockMetadata(xCoord, yCoord - 1, zCoord))) {
                worldObj.setBlock(xCoord, yCoord, zCoord, Blocks.air);
                return;
            }
            isRaw = false;
        }
        if (!checkEnvironment()) {
            worldObj.setBlock(xCoord, yCoord, zCoord, Blocks.air);
            return;
        }
        if (def.superFastGrowth && worldObj.rand.nextInt(10000) < def.additionalGrowthChance) {
            worldObj.scheduleBlockUpdate(xCoord, yCoord, zCoord, BlockPlants.INSTANCE, Plants.TICK_RATE);
        }
        if (level < def.maxLevel) {
            int add = calculateXp();
            if (add == 0) {
                return;
            }
            xp += add;
            if (xp > def.xpPerLevel[level]) {
                xp = 0;
                level++;
                syncTileEntity();
                return;
            }
        }
        if (def.canSpread && level == def.maxLevel) {
            spread();
            level = def.levelAfterHarvest;
            syncTileEntity();
        }
    }

    private void spread() {
        TLongList list = new TLongArrayList();
        for (int x = -2; x <= 2; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -2; z <= 2; z++) {
                    if (x == 0 && y == 0 && z == 0) {
                        continue;
                    }
                    if (canReplace(xCoord + x, yCoord + y, zCoord + z)) {
                        list.add(HPoint.toLong(x + xCoord, y + yCoord, z + zCoord));
                    }
                }
            }
        }
        if (!list.isEmpty()) {
            list.shuffle(worldObj.rand);
            HPoint h = new HPoint(list.get(0));
            placeTile(worldObj, h.x, h.y, h.z, def.mutate(0, worldObj.rand), stats.mutate(0, worldObj.rand));
            if (def.superFastGrowth && def.additionalGrowthChance / 2 < worldObj.rand.nextInt(10000)) {
                worldObj.scheduleBlockUpdate(h.x, h.y, h.z, BlockPlants.INSTANCE, Plants.TICK_RATE);
            }
        }

    }

    private boolean canReplace(int x, int y, int z) {
        Block under = worldObj.getBlock(x, y - 1, z);
        return worldObj.getBlock(x, y, z).isReplaceable(worldObj, x, y, z) && (under == Blocks.grass || under == Blocks.dirt);
    }

    private boolean checkEnvironment() {
        return true;
    }

    private int calculateXp() {
        int out = stats.growth + 20;
        UnderbedrockLayer<ChunkData> layer = DimensionChunkData.getLayer(worldObj);
        ChunkData data = layer.get(xCoord >> 4, zCoord >> 4);
        int minerals = data.getMinerals();
        double supply = ((double) minerals) / def.minerals;
        if (supply < 0.5) {
            worldObj.setBlock(xCoord, yCoord, zCoord, Blocks.air);
            return 0;
        }
        data.setMinerals(Math.max(0, minerals - def.mineralConsumption));
        layer.flagAsModified(xCoord >> 4, zCoord >> 4);
        if (supply < 1d) {
            return (int) (supply * out);
        }
        return out;
    }

    @Override
    public void setValuesFromMeta(int meta) {
        def = Plants.get(meta);
        if (def != null) {
            stats = def.getDefaultStats();
        }
    }

    @Override
    public int getMeta() {
        return Plants.getID(def);
    }

    @Override
    public ITexture[][] getTextures() {
        return new ITexture[0][];
    }

    @Override
    public ITexture[][] getTextures(ItemStack aStack, byte aFacing, boolean aActive, boolean aRedstone, boolean placeCovers) {
        return new ITexture[0][];
    }

    @Override
    public ITexture[][] getTextures(boolean tCovered) {
        return new ITexture[0][];
    }

    @Override
    public void rebakeMap() {

    }

    @Override
    public Packet getDescriptionPacket() {
        syncTileEntity();
        return super.getDescriptionPacket();
    }

    public void syncTileEntity() {
        ByteArrayDataOutput stream = ByteStreams.newDataOutput(10);
        writeTile(stream);
        NW.sendPacketToAllPlayersInRange(worldObj, new GT_Packet_ByteStream(xCoord, yCoord, zCoord, stream), xCoord, zCoord);
    }


    public void setPlant(PlantDef def, PlantStats stats) {
        this.def = def;
        this.stats = stats;
        syncTileEntity();
    }

    public static void placeTile(World world, int x, int y, int z, PlantDef def, PlantStats stats) {
        world.setBlock(x, y, z, BlockPlants.INSTANCE);
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TilePlants) {
            ((TilePlants) te).setPlant(def, stats);
        }
    }

    public static void plateTileRawGen(World world, int x, int y, int z, PlantDef def, PlantStats stats) {
        world.setBlock(x, y, z, BlockPlants.INSTANCE);
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TilePlants) {
            TilePlants plant = (TilePlants) te;
            plant.setPlant(def, stats);
            plant.isRaw = true;
        }
    }

    public ArrayList<ItemStack> getDrops() {
        ArrayList<ItemStack> out = new ArrayList<>();
        if (def != null && def.drops.size() > 0) {
            for (int i = 0; 5 * i < stats.gain; i++) {
                out.add(def.drops.next(worldObj.rand).toMCStack());
            }
        }
        return out;
    }

    public List<String> getPlantInfo() {
        List<String> out = new ArrayList<>();
        if (def == null) {
            out.add("Empty plant");
            return out;
        }
        out.add("Plant: " + def.name);
        out.add("Stats: " + stats.growth + "/" + stats.gain);
        out.add("Level: " + level);
        out.add("Xp: " + xp + "/" + (level < def.xpPerLevel.length ? "" + def.xpPerLevel[level] : "Max Level"));
        return out;
    }

    @Override
    public void writeTile(ByteArrayDataOutput stream) {
        stream.writeInt(getMeta());
        stream.writeInt(level);
    }

    @Override
    public void readTile(ByteArrayDataInput stream) {
        setValuesFromMeta(stream.readInt());
        level = stream.readInt();
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
    }

    @Override
    public CustomRenderer getCustomRenderer() {
        return new CustomRenderer() {
            @Override
            public boolean renderWorldBlock(IBlockAccess world, IFastRenderedTileEntity tileEntity, int x, int y, int z, Block block, RenderBlocks renderBlocks) {
                TilePlants plants = (TilePlants) tileEntity;
                if (plants.def == null) {
                    return true;
                }
                IIcon icon = def.textures.length < plants.level || def.textures[plants.level] == null ? null : def.textures[plants.level].getIcon();
                if (icon == null) {
                    return true;
                }
                Tessellator tes = Tessellator.instance;
                tes.setColorRGBA_F(1F, 1F, 1F, 1F);
                tes.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
//                renderItemTwoSides(tes, plants.xCoord, plants.yCoord, plants.zCoord, icon, 0.25, 0.25, 0, 1);
//                renderItemTwoSides(tes, plants.xCoord, plants.yCoord, plants.zCoord, icon, 0.75, 0.75, 0, 1);
//                renderItemTwoSides(tes, plants.xCoord, plants.yCoord, plants.zCoord, icon, 0, 1, 0.25, 0.25);
//                renderItemTwoSides(tes, plants.xCoord, plants.yCoord, plants.zCoord, icon, 0, 1, 0.75, 0.75);
                if (def.renderType == PlantDef.RenderType.Sharp) {
                    renderIconX(tes, plants.xCoord + 0.25, plants.yCoord, plants.zCoord, icon);
                    renderIconX(tes, plants.xCoord + 0.75, plants.yCoord, plants.zCoord, icon);
                    renderIconZ(tes, plants.xCoord, plants.yCoord, plants.zCoord + 0.25, icon);
                    renderIconZ(tes, plants.xCoord, plants.yCoord, plants.zCoord + 0.75, icon);
                }
                else {
                    renderIconTwoSides(tes, xCoord, yCoord, zCoord, icon, 0, 1, 0, 1);
                    renderIconTwoSides(tes, xCoord, yCoord, zCoord, icon, 1, 0, 0, 1);
                }
                return true;
            }

            private void renderIconX(Tessellator tes, double x, double y, double z, IIcon icon) {
                renderIconTwoSides(tes, x, y, z, icon, 0, 0, 0, 1);
            }

            private void renderIconZ(Tessellator tes, double x, double y, double z, IIcon icon) {
                renderIconTwoSides(tes, x, y, z, icon, 0, 1, 0, 0);
            }

            private void renderIconTwoSides(Tessellator tes, double x, double y, double z, IIcon icon, double x1, double x2, double z1, double z2) {
                double minU = icon.getInterpolatedU(0D);
                double minV = icon.getInterpolatedV(0D);
                double maxU = icon.getInterpolatedU(16D);
                double maxV = icon.getInterpolatedV(16D);
                tes.addVertexWithUV(x + x1, y + 1, z + z1, minU, minV);
                tes.addVertexWithUV(x + x1, y, z + z1, minU, maxV);
                tes.addVertexWithUV(x + x2, y, z + z2, maxU, maxV);
                tes.addVertexWithUV(x + x2, y + 1, z + z2, maxU, minV);

                tes.addVertexWithUV(x + x2, y + 1, z + z2, maxU, minV);
                tes.addVertexWithUV(x + x2, y, z + z2, maxU, maxV);
                tes.addVertexWithUV(x + x1, y, z + z1, minU, maxV);
                tes.addVertexWithUV(x + x1, y + 1, z + z1, minU, minV);

            }


            @Override
            public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Block block, RenderBlocks renderBlocks, int meta) {

            }
        };
    }
}
