package idealindustrial.tile.gui.base.component;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class II_SlotHolo extends II_Slot{
    public II_SlotHolo(IInventory inventory, int id, int x, int y, int texture) {
        super(inventory, id, x, y, texture);
    }

    @Override
    public boolean canTakeStack(EntityPlayer p_82869_1_) {
        return false;
    }

    @Override
    public boolean isItemValid(ItemStack p_75214_1_) {
        return false;
    }

}
