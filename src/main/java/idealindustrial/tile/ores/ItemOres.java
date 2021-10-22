package idealindustrial.tile.ores;

import idealindustrial.II_Core;
import idealindustrial.II_Values;
import idealindustrial.autogen.material.II_Material;
import idealindustrial.autogen.material.II_Materials;
import idealindustrial.autogen.material.Prefixes;
import idealindustrial.tile.host.HostTileImpl;
import idealindustrial.tile.interfaces.host.HostMachineTile;
import idealindustrial.util.misc.II_TileUtil;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public class ItemOres extends ItemBlock {
    public static ItemOres INSTANCE;

    public ItemOres(Block p_i45328_1_) {
        super(p_i45328_1_);
        setMaxDamage(0);
        setHasSubtypes(true);
        setCreativeTab(II_Core.II_MATERIAL_TAB);
        INSTANCE = this;
    }

    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        return false;
    }


    public boolean placeBlockAt(ItemStack aStack, EntityPlayer aPlayer, World aWorld, int aX, int aY, int aZ, int side, float hitX, float hitY, float hitZ, int aMeta) {
        short tDamage = (short) getDamage(aStack);
        if (tDamage >= 0) {
            if (!aWorld.setBlock(aX, aY, aZ, this.field_150939_a, 0, 3)) {
                return false;
            }
            TileOres tTileEntity = (TileOres) aWorld.getTileEntity(aX, aY, aZ);
            if (tTileEntity != null) {
                tTileEntity.setValuesFromDamage(tDamage);
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

    public String getUnlocalizedName(ItemStack aStack) {
        return this.field_150939_a.getUnlocalizedName() + "." + getDamage(aStack);
    }

}
