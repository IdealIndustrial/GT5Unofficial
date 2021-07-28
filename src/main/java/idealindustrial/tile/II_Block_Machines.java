package idealindustrial.tile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.GregTech_API;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.IDebugableBlock;
import gregtech.api.interfaces.tileentity.ICoverable;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.BaseMetaPipeEntity;
import gregtech.api.metatileentity.BaseMetaTileEntity;
import gregtech.api.metatileentity.BaseTileEntity;
import gregtech.api.objects.XSTR;
import gregtech.api.util.GT_Utility;
import gregtech.common.blocks.GT_Material_Machines;
import gregtech.common.render.GT_Renderer_Block;
import idealindustrial.II_Core;
import idealindustrial.II_Values;
import idealindustrial.itemgen.blocks.base.II_Base_Block;
import idealindustrial.tile.base.IClickableTileEntity;
import idealindustrial.tile.base.II_BaseTile;
import idealindustrial.tile.base.II_BaseTileImpl;
import idealindustrial.tools.II_ToolRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class II_Block_Machines
        extends II_Base_Block
        implements IDebugableBlock, ITileEntityProvider {

    public II_Block_Machines() {
        super(II_Item_Machines.class, "ii.blockmachines", new GT_Material_Machines());
        GregTech_API.registerMachineBlock(this, -1);
        setHardness(1.0F);
        setResistance(10.0F);
        setStepSound(soundTypeMetal);
        setCreativeTab(II_Core.II_MAIN_TAB);
        this.isBlockContainer = true;
    }

    public String getHarvestTool(int aMeta) {
        return "wrench";
    }

    public int getHarvestLevel(int aMeta) {
        return aMeta % 4;
    }

    protected boolean canSilkHarvest() {
        return false;
    }

    public void onNeighborChange(IBlockAccess aWorld, int aX, int aY, int aZ, int aTileX, int aTileY, int aTileZ) {
        TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
        if ((tTileEntity instanceof BaseTileEntity)) {
            ((BaseTileEntity) tTileEntity).onAdjacentBlockChange(aTileX, aTileY, aTileZ);
        }
    }

    public void onNeighborBlockChange(World aWorld, int aX, int aY, int aZ, Block aBlock) {
        TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
        if ((tTileEntity instanceof BaseMetaPipeEntity)) {
            ((BaseMetaPipeEntity) tTileEntity).onNeighborBlockChange(aX, aY, aZ);
        }
    }

    public void onBlockAdded(World aWorld, int aX, int aY, int aZ) {
        super.onBlockAdded(aWorld, aX, aY, aZ);
        if (GregTech_API.isMachineBlock(this, aWorld.getBlockMetadata(aX, aY, aZ))) {
            GregTech_API.causeMachineUpdate(aWorld, aX, aY, aZ);
        }
    }

    public String getUnlocalizedName() {
        return "gt.blockmachines";
    }

    public String getLocalizedName() {
        return StatCollector.translateToLocal(getUnlocalizedName() + ".name");
    }

    public int getFlammability(IBlockAccess aWorld, int aX, int aY, int aZ, ForgeDirection face) {
        return 0;
    }

    public int getFireSpreadSpeed(IBlockAccess aWorld, int aX, int aY, int aZ, ForgeDirection face) {
        return (GregTech_API.sMachineFlammable) && (aWorld.getBlockMetadata(aX, aY, aZ) == 0) ? 100 : 0;
    }

    public int getRenderType() {
        if (GT_Renderer_Block.INSTANCE == null) {
            return super.getRenderType();
        }
        return GT_Renderer_Block.INSTANCE.mRenderID;
    }

    public boolean isFireSource(World aWorld, int aX, int aY, int aZ, ForgeDirection side) {
        return (GregTech_API.sMachineFlammable) && (aWorld.getBlockMetadata(aX, aY, aZ) == 0);
    }

    public boolean isFlammable(IBlockAccess aWorld, int aX, int aY, int aZ, ForgeDirection face) {
        return (GregTech_API.sMachineFlammable) && (aWorld.getBlockMetadata(aX, aY, aZ) == 0);
    }

    public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess aWorld, int aX, int aY, int aZ) {
        return false;
    }

    public boolean canConnectRedstone(IBlockAccess var1, int var2, int var3, int var4, int var5) {
        return true;
    }

    public boolean canBeReplacedByLeaves(IBlockAccess aWorld, int aX, int aY, int aZ) {
        return false;
    }

    public boolean isNormalCube(IBlockAccess aWorld, int aX, int aY, int aZ) {
        return false;
    }

    public boolean hasTileEntity(int aMeta) {
        return true;
    }

    public boolean hasComparatorInputOverride() {
        return true;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public boolean canProvidePower() {
        return true;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public TileEntity createNewTileEntity(World aWorld, int aMeta) {
        return createTileEntity(aWorld, aMeta);
    }

    public IIcon getIcon(IBlockAccess aIBlockAccess, int aX, int aY, int aZ, int aSide) {
        return Textures.BlockIcons.MACHINE_LV_SIDE.getIcon();
    }

    public IIcon getIcon(int aSide, int aMeta) {
        return Textures.BlockIcons.MACHINE_LV_SIDE.getIcon();
    }


    @Override
    public void setBlockBoundsForItemRender() {
        super.setBlockBounds(0, 0, 0, 1, 1, 1);
    }

    public float getBlockHardness(World aWorld, int aX, int aY, int aZ) {
        return super.getBlockHardness(aWorld, aX, aY, aZ);
    }


    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile == null) {
            return false;
        }
        ItemStack item = player.inventory.getCurrentItem();
        if (player.isSneaking() && (item == null || !II_ToolRegistry.allowShiftClickTile(item))) {
            return false;
        }
        if ((tile instanceof IClickableTileEntity)) {
            return ((IClickableTileEntity) tile).onRightClick(player, item, side, hitX, hitY, hitZ);
        }
        return false;
    }

    public void onBlockClicked(World aWorld, int aX, int aY, int aZ, EntityPlayer aPlayer) {

    }

    public int getDamageValue(World aWorld, int aX, int aY, int aZ) {
        TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
        if ((tTileEntity instanceof II_BaseTile)) {
            return ((II_BaseTile) tTileEntity).getMetaTileID();
        }
        return 0;
    }

    public void onBlockExploded(World aWorld, int aX, int aY, int aZ, Explosion aExplosion) {
        TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
        if ((tTileEntity instanceof BaseMetaTileEntity)) {
            ((BaseMetaTileEntity) tTileEntity).doEnergyExplosion();
        }
        super.onBlockExploded(aWorld, aX, aY, aZ, aExplosion);
    }

    public void breakBlock(World aWorld, int aX, int aY, int aZ, Block par5, int par6) {
        GregTech_API.causeMachineUpdate(aWorld, aX, aY, aZ);
        TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
        if ((tTileEntity instanceof II_BaseTile)) {
            II_BaseTile tGregTechTileEntity = (II_BaseTile) tTileEntity;
            Random tRandom = new XSTR();
            for (int i = 0; i < tGregTechTileEntity.getSizeInventory(); i++) {
                ItemStack tItem = tGregTechTileEntity.getStackInSlot(i);
                if ((tItem != null) && (tItem.stackSize > 0) && (tGregTechTileEntity.isValidSlot(i))) {
                    EntityItem tItemEntity = new EntityItem(aWorld, aX + tRandom.nextFloat() * 0.8F + 0.1F, aY + tRandom.nextFloat() * 0.8F + 0.1F, aZ + tRandom.nextFloat() * 0.8F + 0.1F, new ItemStack(tItem.getItem(), tItem.stackSize, tItem.getItemDamage()));
                    if (tItem.hasTagCompound()) {
                        tItemEntity.getEntityItem().setTagCompound((NBTTagCompound) tItem.getTagCompound().copy());
                    }
                    tItemEntity.motionX = (tRandom.nextGaussian() * 0.0500000007450581D);
                    tItemEntity.motionY = (tRandom.nextGaussian() * 0.0500000007450581D + 0.2000000029802322D);
                    tItemEntity.motionZ = (tRandom.nextGaussian() * 0.0500000007450581D);
                    aWorld.spawnEntityInWorld(tItemEntity);
                    tItem.stackSize = 0;
                    tGregTechTileEntity.setInventorySlotContents(i, null);
                }
            }
        }
        super.breakBlock(aWorld, aX, aY, aZ, par5, par6);
        aWorld.removeTileEntity(aX, aY, aZ);
    }

    public ArrayList<ItemStack> getDrops(World aWorld, int aX, int aY, int aZ, int aMeta, int aFortune) {
        TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
        if ((tTileEntity instanceof II_BaseTile)) {
            return ((II_BaseTile) tTileEntity).getDrops();
        }
        return new ArrayList<>();
    }

    @Override
    public boolean removedByPlayer(World aWorld, EntityPlayer aPlayer, int aX, int aY, int aZ, boolean aWillHarvest) {
        if (aWillHarvest) {
            return true; // This delays deletion of the block until after getDrops
        } else {
            return super.removedByPlayer(aWorld, aPlayer, aX, aY, aZ, false);
        }
    }

    @Override
    public void harvestBlock(World aWorld, EntityPlayer aPlayer, int aX, int aY, int aZ, int aMeta) {
        super.harvestBlock(aWorld, aPlayer, aX, aY, aZ, aMeta);
        aWorld.setBlockToAir(aX, aY, aZ);
    }
//
//    public int getComparatorInputOverride(World aWorld, int aX, int aY, int aZ, int aSide) {
//        TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
//        if (((tTileEntity instanceof II_BaseTile))) {
//            return ((II_BaseTile) tTileEntity).getComparatorValue((byte) aSide);
//        }
//        return 0;
//    }
//
//    public int isProvidingWeakPower(IBlockAccess aWorld, int aX, int aY, int aZ, int aSide) {
//        if ((aSide < 0) || (aSide > 5)) {
//            return 0;
//        }
//        TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
//        if (((tTileEntity instanceof II_BaseTile))) {
//            return ((II_BaseTile) tTileEntity).getOutputRedstoneSignal(GT_Utility.getOppositeSide(aSide));
//        }
//        return 0;
//    }
//
//    public int isProvidingStrongPower(IBlockAccess aWorld, int aX, int aY, int aZ, int aSide) {
//        if ((aSide < 0) || (aSide > 5)) {
//            return 0;
//        }
//        TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
//        if (((tTileEntity instanceof II_BaseTile))) {
//            return ((II_BaseTile) tTileEntity).getStrongOutputRedstoneSignal(GT_Utility.getOppositeSide(aSide));
//        }
//        return 0;
//    }

    public void dropBlockAsItemWithChance(World aWorld, int aX, int aY, int aZ, int par5, float chance, int par7) {
        if (!aWorld.isRemote) {
            TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
            if ((tTileEntity != null) && (chance < 1.0F)) {
                if (((tTileEntity instanceof BaseMetaTileEntity)) && (GregTech_API.sMachineNonWrenchExplosions)) {
                    ((BaseMetaTileEntity) tTileEntity).doEnergyExplosion();
                }
            } else {
                super.dropBlockAsItemWithChance(aWorld, aX, aY, aZ, par5, chance, par7);
            }
        }
    }

    public boolean isSideSolid(IBlockAccess aWorld, int aX, int aY, int aZ, ForgeDirection aSide) {
        if (aWorld.getBlockMetadata(aX, aY, aZ) == 0) {
            return true;
        }
        TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
        if (tTileEntity != null) {
            if ((tTileEntity instanceof BaseMetaTileEntity)) {
                return true;
            }
            if (((tTileEntity instanceof BaseMetaPipeEntity)) && ((((BaseMetaPipeEntity) tTileEntity).mConnections & 0xFFFFFFC0) != 0)) {
                return true;
            }
            if (((tTileEntity instanceof ICoverable)) && (((ICoverable) tTileEntity).getCoverIDAtSide((byte) aSide.ordinal()) != 0)) {
                return true;
            }
        }
        return false;
    }

    public int getLightOpacity(IBlockAccess aWorld, int aX, int aY, int aZ) {
        TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
        if (tTileEntity == null) {
            return 0;
        }
//        if ((tTileEntity instanceof II_BaseTile)) {
//            return ((II_BaseTile) tTileEntity).getLightOpacity();
//        }
        return  0;
    }

    public int getLightValue(IBlockAccess aWorld, int aX, int aY, int aZ) {
        TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
//        if ((tTileEntity instanceof BaseMetaTileEntity)) {
//            return ((BaseMetaTileEntity) tTileEntity).getLightValue();
//        }
        return 0;
    }

    public TileEntity createTileEntity(World aWorld, int aMeta) {
        return new II_BaseTileImpl();
    }

    public float getExplosionResistance(Entity par1Entity, World aWorld, int aX, int aY, int aZ, double explosionX, double explosionY, double explosionZ) {
        TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
//        if (((tTileEntity instanceof II_BaseTile))) {
//            return ((II_BaseTile) tTileEntity).getBlastResistance((byte) 6);
//        }
        return 10.0F;
    }

    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
        for (int i = 1; i < II_Values.metaTiles.length; i++) {
            if (II_Values.metaTiles[i] != null) {
                par3List.add(new ItemStack(par1, 1, i));
            }
        }
    }

    public void onBlockPlacedBy(World aWorld, int aX, int aY, int aZ, EntityLivingBase aPlayer, ItemStack aStack) {
//        TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
//        if (tTileEntity == null) {
//            return;
//        }
//        if ((tTileEntity instanceof II_BaseTile)) {
//            II_BaseTile var6 = (II_BaseTile) tTileEntity;
//            if (aPlayer == null) {
//                var6.setFrontFacing((byte) 1);
//            } else {
//                int var7 = MathHelper.floor_double(aPlayer.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3;
//                int var8 = Math.round(aPlayer.rotationPitch);
//                if ((var8 >= 65) && (var6.isValidFacing((byte) 1))) {
//                    var6.setFrontFacing((byte) 1);
//                } else if ((var8 <= -65) && (var6.isValidFacing((byte) 0))) {
//                    var6.setFrontFacing((byte) 0);
//                } else {
//                    switch (var7) {
//                        case 0:
//                            var6.setFrontFacing((byte) 2);
//                            break;
//                        case 1:
//                            var6.setFrontFacing((byte) 5);
//                            break;
//                        case 2:
//                            var6.setFrontFacing((byte) 3);
//                            break;
//                        case 3:
//                            var6.setFrontFacing((byte) 4);
//                    }
//                }
//            }
//        }
    }

    public ArrayList<String> getDebugInfo(EntityPlayer aPlayer, int aX, int aY, int aZ, int aLogLevel) {
        TileEntity tTileEntity = aPlayer.worldObj.getTileEntity(aX, aY, aZ);
        if ((tTileEntity instanceof BaseMetaTileEntity)) {
            return ((BaseMetaTileEntity) tTileEntity).getDebugInfo(aPlayer, aLogLevel);
        }
        if ((tTileEntity instanceof BaseMetaPipeEntity)) {
            return ((BaseMetaPipeEntity) tTileEntity).getDebugInfo(aPlayer, aLogLevel);
        }
        return null;
    }

    public boolean recolourBlock(World aWorld, int aX, int aY, int aZ, ForgeDirection aSide, int aColor) {
        TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
        if ((tTileEntity instanceof II_BaseTile)) {
            if (((II_BaseTile) tTileEntity).getColorization() == (byte) ((aColor ^ 0xFFFFFFFF) & 0xF)) {
                return false;
            }
            ((II_BaseTile) tTileEntity).setColorization((byte) ((aColor ^ 0xFFFFFFFF) & 0xF));
            return true;
        }
        return false;
    }
}
