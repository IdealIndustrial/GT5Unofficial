package gregtech.api.gui;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public interface SlotSupplier<T extends Slot> {
    T provide(IInventory aInventory, int aIndex, int aX, int aY);
}
