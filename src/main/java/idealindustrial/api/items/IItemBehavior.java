package idealindustrial.api.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;

import java.util.List;

public interface IItemBehavior {

    default ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player) {
        return is;
    }

    default boolean onItemUse(ItemStack is, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        return false;
    }


    default boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        return false;
    }

    default boolean addSubItems(Item item, int damage, CreativeTabs tab, List<ItemStack> list) {
        return false;
    }

    default boolean renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object... data) {
        return false;
    }

    default boolean loadIcon() {
        return true;
    }
}
