package gregtech.api.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

import java.util.function.Function;

public class GT_Slot_ClosedInteraction extends Slot {

    protected final Function<EntityPlayer, Boolean> canInteract;
    public GT_Slot_ClosedInteraction(IInventory inventory, int slotID, int x, int y, Function<EntityPlayer, Boolean> canInteract) {
        super(inventory, slotID, x, y);
        this.canInteract = canInteract;
    }

    @Override
    public boolean canTakeStack(EntityPlayer player) {
        return canInteract.apply(player);
    }
}
