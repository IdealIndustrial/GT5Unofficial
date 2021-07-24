package idealindustrial.tile.base;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IClickableTileEntity {
    
    boolean onRightClick(EntityPlayer player, ItemStack item, int side, float hitX, float hitY, float hitZ);
    boolean onLeftClick(EntityPlayer player, ItemStack item, int side, float hitX, float hitY, float hitZ);

}
