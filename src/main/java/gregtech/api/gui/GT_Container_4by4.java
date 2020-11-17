package gregtech.api.gui;

import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

import java.util.function.Supplier;

public class GT_Container_4by4 extends GT_ContainerMetaTile_Machine {

    public GT_Container_4by4(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity) {
        this(aInventoryPlayer, aTileEntity, Slot::new);
    }

    public GT_Container_4by4(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity, SlotSupplier<Slot> aSupplier) {
        super(aInventoryPlayer, aTileEntity, aSupplier);
    }

    @Override
    public void addSlots(InventoryPlayer aInventoryPlayer) {
        addSlotToContainer(mSupplier.provide(mTileEntity, 0, 53, 8));
        addSlotToContainer(mSupplier.provide(mTileEntity, 1, 71, 8));
        addSlotToContainer(mSupplier.provide(mTileEntity, 2, 89, 8));
        addSlotToContainer(mSupplier.provide(mTileEntity, 3, 107, 8));
        addSlotToContainer(mSupplier.provide(mTileEntity, 4, 53, 26));
        addSlotToContainer(mSupplier.provide(mTileEntity, 5, 71, 26));
        addSlotToContainer(mSupplier.provide(mTileEntity, 6, 89, 26));
        addSlotToContainer(mSupplier.provide(mTileEntity, 7, 107, 26));
        addSlotToContainer(mSupplier.provide(mTileEntity, 8, 53, 44));
        addSlotToContainer(mSupplier.provide(mTileEntity, 9, 71, 44));
        addSlotToContainer(mSupplier.provide(mTileEntity, 10, 89, 44));
        addSlotToContainer(mSupplier.provide(mTileEntity, 11, 107, 44));
        addSlotToContainer(mSupplier.provide(mTileEntity, 12, 53, 62));
        addSlotToContainer(mSupplier.provide(mTileEntity, 13, 71, 62));
        addSlotToContainer(mSupplier.provide(mTileEntity, 14, 89, 62));
        addSlotToContainer(mSupplier.provide(mTileEntity, 15, 107, 62));
    }

    @Override
    public int getSlotCount() {
        return 16;
    }

    @Override
    public int getShiftClickSlotCount() {
        return 16;
    }
}
