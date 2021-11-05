package idealindustrial.impl.tile;

import idealindustrial.II_Core;
import idealindustrial.II_Values;
import idealindustrial.impl.tile.host.HostTileImpl;
import idealindustrial.api.tile.host.HostMachineTile;
import idealindustrial.util.misc.II_TileUtil;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

public class Item_Machines
        extends ItemBlock {
    public static Item INSTANCE;

    public Item_Machines(Block par1) {
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

    @Override
    public String getUnlocalizedName() {
        return super.getUnlocalizedName();
    }

    public String getUnlocalizedName(ItemStack aStack) {
        int damage = getDamage(aStack);
        return "ii.itemmachine." + damage + ".name";
    }

    public String getItemStackDisplayName(ItemStack aStack) {
        return StatCollector.translateToLocal(this.getUnlocalizedName(aStack));
    }

    public void onCreated(ItemStack aStack, World aWorld, EntityPlayer aPlayer) {
        super.onCreated(aStack, aWorld, aPlayer);
        short tDamage = (short) getDamage(aStack);
        if ((tDamage < 0) || ((tDamage >= II_Values.TILES.length) && (II_Values.TILES[tDamage] != null))) {
            //II_Values.metaTiles[tDamage].onCreated(aStack, aWorld, aPlayer);
        }
    }

    @SuppressWarnings("unchecked")
    public boolean placeBlockAt(ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int side, float hitX, float hitY, float hitZ, int aMeta) {
        short tDamage = (short) getDamage(aStack);
        if (tDamage > 0) {
            if (II_Values.TILES[tDamage] == null) {
                return false;
            }
            int tMetaData = II_TileUtil.classToMeta(II_Values.TILES[tDamage].getBaseTileClass());
            if (!aWorld.setBlock(aX, aY, aZ, this.field_150939_a, tMetaData, 3)) {
                return false;
            }
            HostTileImpl tTileEntity = (HostTileImpl) aWorld.getTileEntity(aX, aY, aZ);
            if (tTileEntity != null) {
                tTileEntity.setInitialValuesAsNBT(tTileEntity.isServerSide() ? aStack.getTagCompound() : null, tDamage);
                if (tTileEntity instanceof HostMachineTile) {
                    ((HostMachineTile) tTileEntity).placedByPlayer(aPlayer);
                }
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

    }
}
