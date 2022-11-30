package gregtech.api.gui;

import cpw.mods.fml.common.FMLCommonHandler;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.BaseMetaTileEntity;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * NEVER INCLUDE THIS FILE IN YOUR MOD!!!
 * <p/>
 * The Container I use for all my Basic Machines
 */
public class GT_Container_PrimitiveMiner extends GT_ContainerMetaTile_Machine {

    private int lastUsedId;
    private int basePadding;
    private int btnSize;
    private int powerPerClick;
    private int hungryDurationPerOperation;
    private int foodPerOperation;
    public GT_Container_PrimitiveMiner(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity, int powerPerClick, int hungryDurationPerOperation, int foodPerOperation) {
        super(aInventoryPlayer, aTileEntity);
        this.powerPerClick = powerPerClick;
        this.hungryDurationPerOperation = hungryDurationPerOperation;
        this.foodPerOperation = foodPerOperation;
    }

    public GT_Container_PrimitiveMiner(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity, boolean bindInventory) {
        super(aInventoryPlayer, aTileEntity, bindInventory);
    }

    @Override
    public void addSlots(InventoryPlayer aInventoryPlayer) {
        lastUsedId = 1;
        basePadding = 8;
        btnSize = 18;
        addInputSlots();
        addOutputSlots();
        addButtonSlots();
    }

    public void addInputSlots() {
        for (int i = 0; i < 4; i++) {
            addSlotToContainer(mSupplier.provide(mTileEntity, lastUsedId, basePadding, basePadding+i*btnSize));
            lastUsedId++;
        }
    }
    public void addOutputSlots() {
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 6; x++) {
                addSlotToContainer(new GT_Slot_Output(mTileEntity, lastUsedId, 62+x*btnSize, basePadding+y*btnSize));
                lastUsedId++;
            }
        }
    }
    public void addButtonSlots(){
        addSlotToContainer(new GT_Slot_Holo(mTileEntity, lastUsedId,35,17,false,false,0));
        lastUsedId++;
    }

    @Override
    public ItemStack slotClick(int aSlotIndex, int aMouseclick, int aShifthold, EntityPlayer aPlayer) {
        if(aSlotIndex == 28){
            BaseMetaTileEntity te = (BaseMetaTileEntity) mTileEntity;
            if(te.getStoredSteam() + powerPerClick <= te.getSteamCapacity()){
                te.increaseStoredSteam(powerPerClick, false);
                GT_Utility.drainMusclePlayerPower(aPlayer, hungryDurationPerOperation, foodPerOperation);
                if(mProgressTime < 0) mProgressTime = 0;
            }
        }
        return super.slotClick(aSlotIndex, aMouseclick, aShifthold, aPlayer);
    }

    @Override
    public int getSlotCount() {
        return 28;
    }

    @Override
    public int getShiftClickSlotCount() {
        return 4;
    }
}
