package gregtech.api.gui;

import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

public class GT_Container_1by1 extends GT_ContainerMetaTile_Machine {
    public int a = 0;

    public GT_Container_1by1(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity) {
        super(aInventoryPlayer, aTileEntity);
    }

    public GT_Container_1by1(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity, SlotSupplier<Slot> aSupplier) {
        super(aInventoryPlayer, aTileEntity, aSupplier);
    }

    @Override
    public void addSlots(InventoryPlayer aInventoryPlayer) {
        addSlotToContainer(mSupplier.provide(mTileEntity, 0, 80, 35));
    }

    @Override
    public int getSlotCount() {
        return 1;
    }

    @Override
    public int getShiftClickSlotCount() {
        return 1;
    }
}
