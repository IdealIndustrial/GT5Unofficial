package gregtech.common.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.gui.GT_ContainerMetaTile_Machine;
import gregtech.api.gui.GT_Slot_Holo;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.common.tileentities.generators.GT_MetaTileEntity_Creative_Generator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class GT_Container_CreativeGenerator
        extends GT_ContainerMetaTile_Machine {

    public int aTier = 0;
    public int ampers = 1;

    public GT_Container_CreativeGenerator(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity) {
        super(aInventoryPlayer, aTileEntity);
    }

    @Override
    public void addSlots(InventoryPlayer aInventoryPlayer) {
        addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 2, 26, 5, false, false, 1));
        addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 2, 26, 23, false, false, 1));

        addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 2, 134, 5, false, false, 1));
        addSlotToContainer(new GT_Slot_Holo(this.mTileEntity, 2, 134, 23, false, false, 1));
    }

    @Override
    public ItemStack slotClick(int aSlotIndex, int aMouseclick, int aShifthold, EntityPlayer aPlayer) {
        if (aSlotIndex < 0 || aShifthold == 6) {
            return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
        }
        Slot tSlot = (Slot) this.inventorySlots.get(aSlotIndex);
        if ((tSlot != null) && (this.mTileEntity.getMetaTileEntity() != null)) {
            switch (aSlotIndex) {
                case 0:
                    ((GT_MetaTileEntity_Creative_Generator) this.mTileEntity.getMetaTileEntity()).aTier -= (aShifthold == 1 ? 4 : 1);
                    ((GT_MetaTileEntity_Creative_Generator) this.mTileEntity.getMetaTileEntity()).aTier = Math.max(0, ((GT_MetaTileEntity_Creative_Generator) this.mTileEntity.getMetaTileEntity()).aTier);
                    return null;
                case 1:
                    ((GT_MetaTileEntity_Creative_Generator) this.mTileEntity.getMetaTileEntity()).ampers /= (aShifthold == 1 ? 8 : 2);
                    ((GT_MetaTileEntity_Creative_Generator) this.mTileEntity.getMetaTileEntity()).ampers = Math.max(1, ((GT_MetaTileEntity_Creative_Generator) this.mTileEntity.getMetaTileEntity()).ampers);
                    return null;
                case 2:
                    ((GT_MetaTileEntity_Creative_Generator) this.mTileEntity.getMetaTileEntity()).aTier += (aShifthold == 1 ? 4 : 1);
                    ((GT_MetaTileEntity_Creative_Generator) this.mTileEntity.getMetaTileEntity()).aTier = Math.min(9, ((GT_MetaTileEntity_Creative_Generator) this.mTileEntity.getMetaTileEntity()).aTier);
                    return null;
                case 3:
                    ((GT_MetaTileEntity_Creative_Generator) this.mTileEntity.getMetaTileEntity()).ampers *= (aShifthold == 1 ? 8 : 2);
                    ((GT_MetaTileEntity_Creative_Generator) this.mTileEntity.getMetaTileEntity()).ampers = Math.min(512, ((GT_MetaTileEntity_Creative_Generator) this.mTileEntity.getMetaTileEntity()).ampers);
                    return null;
            }
        }
        return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        if ((this.mTileEntity.isClientSide()) || (this.mTileEntity.getMetaTileEntity() == null)) {
            return;
        }
        this.aTier = ((GT_MetaTileEntity_Creative_Generator) this.mTileEntity.getMetaTileEntity()).aTier;
        this.ampers = ((GT_MetaTileEntity_Creative_Generator) this.mTileEntity.getMetaTileEntity()).ampers;

        for (Object crafter : this.crafters) {
            ICrafting var1 = (ICrafting) crafter;
            var1.sendProgressBarUpdate(this, 110, this.aTier);
            var1.sendProgressBarUpdate(this, 112, this.ampers);
        }
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2) {
        super.updateProgressBar(par1, par2);
        switch (par1) {
            case 110:
                this.aTier = par2;
                break;
            case 112:
                this.ampers = par2;
                break;
        }
    }
}
