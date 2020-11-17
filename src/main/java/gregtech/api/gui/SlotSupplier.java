package gregtech.api.gui;

import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public interface SlotSupplier<T extends Slot> {
    T provide(IGregTechTileEntity mTileEntity, int aIndex, int aX, int aY);
}
