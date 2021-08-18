package idealindustrial.tile;

import gregtech.api.util.GT_ItsNotMyFaultException;
import gregtech.api.util.GT_Utility;
import idealindustrial.II_Core;
import idealindustrial.II_Values;
import idealindustrial.textures.II_TextureManager;
import idealindustrial.tile.base.II_BaseTileImpl;
import idealindustrial.util.misc.II_TileUtil;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public class II_Item_Machines
        extends ItemBlock {
    public static Item INSTANCE;

    public II_Item_Machines(Block par1) {
        super(par1);
        setMaxDamage(0);
        setHasSubtypes(true);
        setCreativeTab(II_Core.II_MAIN_TAB);
        INSTANCE = this;
    }

    @SuppressWarnings("unchecked")
    public void addInformation(ItemStack aStack, EntityPlayer aPlayer, List aList, boolean par4) {
        aList.add("GG");
    }

    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        return false;
    }

    public String getUnlocalizedName(ItemStack aStack) {
        int damage = getDamage(aStack);

        return "ii.itemmachine." + damage;
    }

    public String getItemStackDisplayName(ItemStack aStack) {
        return super.getItemStackDisplayName(aStack);
    }

    public void onCreated(ItemStack aStack, World aWorld, EntityPlayer aPlayer) {
        super.onCreated(aStack, aWorld, aPlayer);
        short tDamage = (short) getDamage(aStack);
        if ((tDamage < 0) || ((tDamage >= II_Values.metaTiles.length) && (II_Values.metaTiles[tDamage] != null))) {
            //II_Values.metaTiles[tDamage].onCreated(aStack, aWorld, aPlayer);
        }
    }

    @SuppressWarnings("unchecked")
    public boolean placeBlockAt(ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int side, float hitX, float hitY, float hitZ, int aMeta) {
        short tDamage = (short) getDamage(aStack);
        if (tDamage > 0) {
            if (II_Values.metaTiles[tDamage] == null) {
                return false;
            }
            int tMetaData = II_TileUtil.classToMeta(II_Values.metaTiles[tDamage].getBaseTileClass());
            if (!aWorld.setBlock(aX, aY, aZ, this.field_150939_a, tMetaData, 3)) {
                return false;
            }
            if (aWorld.getBlock(aX, aY, aZ) != this.field_150939_a) {
                throw new GT_ItsNotMyFaultException("Failed to place Block even though World.setBlock returned true. It COULD be MCPC/Bukkit causing that. In case you really have that installed, don't report this Bug to me, I don't know how to fix it.");
            }
            if (aWorld.getBlockMetadata(aX, aY, aZ) != tMetaData) {
                throw new GT_ItsNotMyFaultException("Failed to set the MetaValue of the Block even though World.setBlock returned true. It COULD be MCPC/Bukkit causing that. In case you really have that installed, don't report this Bug to me, I don't know how to fix it.");
            }
            II_BaseTileImpl tTileEntity = (II_BaseTileImpl) aWorld.getTileEntity(aX, aY, aZ);
            if (tTileEntity != null) {
                tTileEntity.setInitialValuesAsNBT(tTileEntity.isServerSide() ? aStack.getTagCompound() : null, tDamage);
                tTileEntity.onPlaced();
            }
        } else if (!aWorld.setBlock(aX, aY, aZ, this.field_150939_a, tDamage, 3)) {
            return false;
        }
        if (aWorld.getBlock(aX, aY, aZ) == this.field_150939_a) {
            this.field_150939_a.onBlockPlacedBy(aWorld, aX, aY, aZ, aPlayer, aStack);
            this.field_150939_a.onPostBlockPlaced(aWorld, aX, aY, aZ, tDamage);
        }
        return true;
    }

    @Override
    public void registerIcons(IIconRegister register) {
        II_TextureManager.INSTANCE.initItems(register);
    }
}
