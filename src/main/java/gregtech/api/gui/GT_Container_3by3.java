package gregtech.api.gui;

import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

public class GT_Container_3by3 extends GT_ContainerMetaTile_Machine {

    public GT_Container_3by3(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity) {
        super(aInventoryPlayer, aTileEntity);
    }

    public GT_Container_3by3(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity, SlotSupplier<Slot> aSupplier) {
        super(aInventoryPlayer, aTileEntity, aSupplier);
    }

    @Override
    public void addSlots(InventoryPlayer aInventoryPlayer) {
        addSlotToContainer(mSupplier.provide(mTileEntity, 0, 62, 17));
        addSlotToContainer(mSupplier.provide(mTileEntity, 1, 80, 17));
        addSlotToContainer(mSupplier.provide(mTileEntity, 2, 98, 17));
        addSlotToContainer(mSupplier.provide(mTileEntity, 3, 62, 35));
        addSlotToContainer(mSupplier.provide(mTileEntity, 4, 80, 35));
        addSlotToContainer(mSupplier.provide(mTileEntity, 5, 98, 35));
        addSlotToContainer(mSupplier.provide(mTileEntity, 6, 62, 53));
        addSlotToContainer(mSupplier.provide(mTileEntity, 7, 80, 53));
        addSlotToContainer(mSupplier.provide(mTileEntity, 8, 98, 53));
    }

    @Override
    public int getSlotCount() {
        return 9;
    }

    @Override
    public int getShiftClickSlotCount() {
        return 9;
    }
}
