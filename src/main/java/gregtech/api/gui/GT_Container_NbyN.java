package gregtech.api.gui;

import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;

public class GT_Container_NbyN extends GT_ContainerMetaTile_Machine {

    int n;

    public GT_Container_NbyN(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity, int N) {
        super(aInventoryPlayer, aTileEntity, 9*(N-4), (N-4)*18, false);

        n = N;

        if (mTileEntity != null && mTileEntity.getMetaTileEntity() != null) {
            addSlots(aInventoryPlayer);
            if (doesBindPlayerInventory()) bindPlayerInventory(aInventoryPlayer);
            detectAndSendChanges();
        } else {
            aInventoryPlayer.player.openContainer = aInventoryPlayer.player.inventoryContainer;
        }

    }

    @Override
    public void addSlots(InventoryPlayer aInventoryPlayer) {
        for (int j = 0; j < n; j++){
            for (int i = 0; i < n; i++){
                int a =n*j+i, b=53+i*18, c=8+j*18;
                addSlotToContainer( new Slot(mTileEntity, n*j+i, 53-(9-(n-4))+i*18, 8+j*18));
            }
        }
    }

    @Override
    public int getSlotCount() {
        return n*n;
    }

    @Override
    public int getShiftClickSlotCount() {
        return n*n;
    }
}
