package idealindustrial.blocks.ores;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import cpw.mods.fml.common.FMLCommonHandler;
import gregtech.api.net.GT_Packet_ByteStream;
import idealindustrial.autogen.material.II_Material;
import idealindustrial.autogen.material.II_Materials;
import idealindustrial.autogen.material.Prefixes;
import idealindustrial.autogen.material.submaterial.WorldOreInfo;
import idealindustrial.blocks.II_Blocks;
import idealindustrial.blocks.base.Tile32k;
import idealindustrial.render.IFastRenderedTileEntity;
import idealindustrial.textures.ITexture;
import idealindustrial.textures.RenderedTexture;
import idealindustrial.textures.TextureUtil;
import idealindustrial.tile.interfaces.ISyncedTileEntity;
import idealindustrial.util.misc.II_StreamUtil;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.stream.Stream;

import static gregtech.common.GT_Network.NW;

public class TileOres extends TileEntity implements IFastRenderedTileEntity, ISyncedTileEntity, Tile32k {
    final static Prefixes[] prefixOrder = new Prefixes[]{Prefixes.ore, Prefixes.oreSmall};
    public static final TileOres tempTile = new TileOres();

    II_Material material = II_Materials.iron;
    Prefixes prefix = Prefixes.ore;

    Block block = Blocks.stone;
    int meta = 0;

    ITexture[][] textures = new ITexture[6][2];

    public TileOres() {
        if (FMLCommonHandler.instance().getEffectiveSide().isClient()) {
            rebakeMap();
        }
    }

    @Override
    public boolean canUpdate() {
        return false;
    }

    @Override
    public ITexture[][] getTextures() {
        return textures;
    }

    @Override
    public ITexture[][] getTextures(ItemStack aStack, byte aFacing, boolean aActive, boolean aRedstone, boolean placeCovers) {
        int damage = aStack.getItemDamage();
        Block block = Blocks.stone;
        int meta = 0;
        II_Material material = II_Materials.materialForID(damage % 1000);
        if (material == null) {
            material = II_Materials.iron;
        }
        Prefixes prefix = prefixOrder[Math.min(damage / 1000, prefixOrder.length - 1)];
        ITexture[][] textures = new ITexture[6][2];
        for (int i = 0; i < 6; i++) {
            textures[i][0] = TextureUtil.copyTexture(block, meta, i);
            textures[i][1] = new RenderedTexture(material, prefix);
        }
        return textures;
    }

    @Override
    public ITexture[][] getTextures(boolean tCovered) {
        return textures;
    }

    @Override
    public void rebakeMap() {
        for (int i = 0; i < 6; i++) {
            textures[i][0] = TextureUtil.copyTexture(block, meta, i);
            textures[i][1] = new RenderedTexture(material, prefix);
        }
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

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("mat", material.getID());
        nbt.setInteger("pref", prefix.ordinal());
        nbt.setInteger("block", Block.getIdFromBlock(block));
        nbt.setInteger("bmeta", meta);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        if (Stream.of("mat, pref", "block", "bmeta").anyMatch(s -> !nbt.hasKey(s))) {
            material = II_Materials.iron;
            prefix = Prefixes.ore;
            block = Blocks.stone;
            meta = 0;
            return;
        }
        material = II_Materials.materialForID(nbt.getInteger("mat"));
        prefix = Prefixes.values()[nbt.getInteger("pref")];
        block = Block.getBlockById(nbt.getInteger("block"));
        meta = nbt.getInteger("bmeta");
        syncTileEntity();
    }

    @Override
    public void writeTile(ByteArrayDataOutput stream) {
        stream.writeInt(material.getID());
        stream.writeInt(prefix.ordinal());
        stream.writeInt(Block.getIdFromBlock(block));
        stream.writeInt(meta);
    }

    @Override
    public void readTile(ByteArrayDataInput stream) {
        material = II_Materials.materialForID(stream.readInt());
        prefix = Prefixes.values()[stream.readInt()];
        block = Block.getBlockById(stream.readInt());
        meta = stream.readInt();
        rebakeMap();
        if (worldObj != null) {
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }

    @Override
    public void setValuesFromMeta(int meta) {
        material = II_Materials.materialForID(meta % 1000);
        if (material == null) {
            material = II_Materials.iron;
        }
        prefix = meta / 1000 < prefixOrder.length ? prefixOrder[meta / 1000] : Prefixes.ore;
        syncTileEntity();
    }

    public static void replaceBlock(World world, int x, int y, int z, int meta) {
        Block block = world.getBlock(x, y, z);
        int blockMeta = world.getBlockMetadata(x, y, z);
        if (!block.isOpaqueCube()) {
            block = Blocks.stone;
            blockMeta = 0;
        }
        world.setBlock(x, y, z, II_Blocks.INSTANCE.blockOres, 0, 3);
        TileOres tile = (TileOres) world.getTileEntity(x, y, z);
        if (tile != null) {
            tile.setValuesFromMeta(meta);
            tile.block = block;
            tile.meta = blockMeta;
            tile.syncTileEntity();
        } else {
            int a = 0;
        }
    }

    public static int getMeta(II_Material material, Prefixes prefix) {
        int m = II_StreamUtil.indexOf(prefixOrder, prefix);
        assert m != -1;
        return m * 1000 + material.getID();
    }

    public static int getMeta(int materialID, Prefixes prefix) {
        int m = II_StreamUtil.indexOf(prefixOrder, prefix);
        assert m != -1;
        return m * 1000 + materialID;
    }

    public int getMeta() {
        return getMeta(material, prefix);
    }

    public ArrayList<ItemStack> getDrops() {
        WorldOreInfo info = material.getOreInfo();
        if (info == null) {
            return new ArrayList<ItemStack>() {{
                add(new ItemStack(II_Blocks.INSTANCE.blockOres, 1, 0));
            }};
        }
        return info.apply(prefix, worldObj.rand);
    }
    
}
