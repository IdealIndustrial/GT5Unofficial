package idealindustrial.recipe;

import idealindustrial.tile.gui.base.component.II_Slot;
import idealindustrial.tile.gui.base.component.II_SlotSupplier;
import net.minecraft.inventory.IInventory;

public class II_GuiSlotDefinition extends II_GuiObjectDefinition {
    protected II_SlotSupplier slotSupplier;

    public II_GuiSlotDefinition() {

    }

    public II_GuiSlotDefinition(int x, int y, int textureID, II_SlotSupplier slotSupplier) {
        this.x = x;
        this.y = y;
        this.textureID = textureID;
        this.slotSupplier = slotSupplier;
    }

    public II_Slot construct(IInventory inventory, int id) {
        return slotSupplier.provide(inventory, id, x, y, textureID);
    }


}
