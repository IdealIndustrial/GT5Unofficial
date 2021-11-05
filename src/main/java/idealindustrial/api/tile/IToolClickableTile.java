package idealindustrial.api.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IToolClickableTile extends IClickableTileEntity {
    boolean onSoftHammerClick(EntityPlayer player, ItemStack item, int side);
    boolean onWrenchClick(EntityPlayer player, ItemStack item, int side, float hitX, float hitY, float hitZ);
    boolean onScrewdriverClick(EntityPlayer player, ItemStack item, int side);
    boolean onCrowbarClick(EntityPlayer player, ItemStack item, int side);
}
