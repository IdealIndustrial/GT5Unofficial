package idealindustrial.recipe;

import idealindustrial.tile.gui.base.component.II_Slot;
import idealindustrial.tile.gui.base.component.SlotSupplier;
import net.minecraft.inventory.IInventory;

public class GuiSlotDefinition extends GuiObjectDefinition {
    protected SlotSupplier slotSupplier;

    public GuiSlotDefinition() {

    }

    public GuiSlotDefinition(int x, int y, int textureID, SlotSupplier slotSupplier) {
        this.x = x;
        this.y = y;
        this.textureID = textureID;
        this.slotSupplier = slotSupplier;
    }

    public II_Slot construct(IInventory inventory, int id) {
        return slotSupplier.provide(inventory, id, x, y, textureID);
    }


}