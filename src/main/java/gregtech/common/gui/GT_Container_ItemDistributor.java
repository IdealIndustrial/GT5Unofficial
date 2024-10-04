package gregtech.common.gui;

import gregtech.api.gui.GT_ContainerMetaTile_Machine;
import gregtech.api.gui.GT_Slot_Holo;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.util.GT_Utility;
import gregtech.common.tileentities.automation.GT_MetaTileEntity_ItemDistributor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;

public class GT_Container_ItemDistributor
        extends GT_ContainerMetaTile_Machine {
    public GT_Container_ItemDistributor(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity) {
        super(aInventoryPlayer, aTileEntity);
    }

    public void addSlots(InventoryPlayer aInventoryPlayer) {
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 9; x++) {
                addSlotToContainer(new Slot(this.mTileEntity, x + y * 9, 8 + x * 18, 5 + y * 18));
            }
        }
        addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 27, 8, 63, false, true, 1));
        addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 27, 26, 63, false, true, 1));
        addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 27, 44, 63, false, true, 1));
    }

    public ItemStack slotClick(int aSlotIndex, int aMouseclick, int aShifthold, EntityPlayer aPlayer) {
        if (aSlotIndex < 27) {
            return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
        }
        Slot tSlot = (Slot) this.inventorySlots.get(aSlotIndex);
        if (tSlot != null) {
            if (this.mTileEntity.getMetaTileEntity() == null) {
                return null;
            }
            if (aSlotIndex == 27) {
                ((GT_MetaTileEntity_ItemDistributor) this.mTileEntity.getMetaTileEntity()).bOutput = (!((GT_MetaTileEntity_ItemDistributor) this.mTileEntity.getMetaTileEntity()).bOutput);
                if (((GT_MetaTileEntity_ItemDistributor) this.mTileEntity.getMetaTileEntity()).bOutput) {
                    aPlayer.addChatComponentMessage(new ChatComponentTranslation("Interaction_DESCRIPTION_Index_116"));
                } else {
                    aPlayer.addChatComponentMessage(new ChatComponentTranslation("Interaction_DESCRIPTION_Index_117"));
                }
                return null;
            }
            if (aSlotIndex == 28) {
                ((GT_MetaTileEntity_ItemDistributor) this.mTileEntity.getMetaTileEntity()).bRedstoneIfFull = (!((GT_MetaTileEntity_ItemDistributor) this.mTileEntity.getMetaTileEntity()).bRedstoneIfFull);
                if (((GT_MetaTileEntity_ItemDistributor) this.mTileEntity.getMetaTileEntity()).bRedstoneIfFull) {
                    aPlayer.addChatComponentMessage(new ChatComponentTranslation("Interaction_DESCRIPTION_Index_118"));
                } else {
                    aPlayer.addChatComponentMessage(new ChatComponentTranslation("Interaction_DESCRIPTION_Index_119"));
                }
                return null;
            }
            if (aSlotIndex == 29) {
                ((GT_MetaTileEntity_ItemDistributor) this.mTileEntity.getMetaTileEntity()).bInvert = (!((GT_MetaTileEntity_ItemDistributor) this.mTileEntity.getMetaTileEntity()).bInvert);
                if (((GT_MetaTileEntity_ItemDistributor) this.mTileEntity.getMetaTileEntity()).bInvert) {
                    aPlayer.addChatComponentMessage(new ChatComponentTranslation("Interaction_DESCRIPTION_Index_120"));
                } else {
                    aPlayer.addChatComponentMessage(new ChatComponentTranslation("Interaction_DESCRIPTION_Index_121"));
                }
                return null;
            }
        }
        return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
    }

    public int getSlotCount() {
        return 27;
    }

    public int getShiftClickSlotCount() {
        return 27;
    }
}
