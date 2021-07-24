package idealindustrial.tile.gui.base.component;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class II_Slot extends Slot {

    public final int texture;

    public II_Slot(IInventory inventory, int id, int x, int y, int texture) {
        super(inventory, id, x, y);
        this.texture = texture;
    }
}
