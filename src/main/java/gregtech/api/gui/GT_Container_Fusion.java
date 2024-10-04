package gregtech.api.gui;

import cpw.mods.fml.common.FMLCommonHandler;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * NEVER INCLUDE THIS FILE IN YOUR MOD!!!
 * <p/>
 * The Container I use for all my Basic Machines
 */
public class GT_Container_Fusion extends GT_Container_MultiMachine {
    public GT_Container_Fusion(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity) {
        super(aInventoryPlayer, aTileEntity);
    }

    public GT_Container_Fusion(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity, boolean bindInventory) {
        super(aInventoryPlayer, aTileEntity, bindInventory);
    }

    @Override
    public void addSlots(InventoryPlayer aInventoryPlayer) {
        addSlotToContainer(new Slot(mTileEntity, 1, 155, 5));
        addSlotToContainer(new GT_Slot_Holo(mTileEntity,2,155,23,false,true,1));
    }

    /*@Override
    public ItemStack slotClick(int aSlotIndex, int aMouseclick, int aShifthold, EntityPlayer aPlayer) {
        if(aSlotIndex == 1){
            if(FMLCommonHandler.instance().getEffectiveSide().isClient())
                return null;
            if(mTileEntity!=null){
                if (mTileEntity.isAllowedToWork())
                    mTileEntity.disableWorking();
                else
                    mTileEntity.enableWorking();
            }
            return null;
        }
        return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
    }*/

    @Override
    public int getSlotCount() {
        return 2;
    }

    @Override
    public int getShiftClickSlotCount() {
        return 1;
    }
}
