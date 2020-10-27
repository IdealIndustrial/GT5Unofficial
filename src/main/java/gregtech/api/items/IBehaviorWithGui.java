package gregtech.api.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IBehaviorWithGui {

    Object getClientGui(EntityPlayer aPlayer, ItemStack aHeldItem, int aData);

    Object getServerGui(EntityPlayer aPlayer, ItemStack aHeldItem, int aData);
}
