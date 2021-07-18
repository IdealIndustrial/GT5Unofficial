package gregtech.api.metatileentity.reimplement.basetile;

import gregtech.api.GregTech_API;
import gregtech.api.interfaces.tileentity.IHasInventory;
import gregtech.api.metatileentity.MetaTileEntity;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import idealindustrial.util.inventory.II_InternalInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.stream.IntStream;

public abstract class BaseTileEntity4_Inventory extends BaseTileEntity3_UpdateEvents implements IHasInventory {

    protected boolean inventoryModified = false;
    II_InternalInventory in, out;
    int[] slots;
    int inSize, outSize;

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        in.nbtSave(tag, "in");
        out.nbtSave(tag, "out");
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        in.nbtLoad(tag, "in");
        out.nbtLoad(tag, "out");
    }

    @Override
    public void onPostTick(long timer, boolean serverSide) {
        super.onPostTick(timer, serverSide);
        inventoryModified = false;
    }

    @Override
    protected void setMetaTileEntity(MetaTileEntity metaTileEntity) {
        super.setMetaTileEntity(metaTileEntity);
        if (metaTileEntity.hasInventory()) {
            in = metaTileEntity.getInputsHandler();
            out = metaTileEntity.getOutputsHandler();
            inSize = in.size();
            outSize = out.size();
            slots = IntStream.iterate(0, i -> i + 1).limit(inSize + outSize).toArray();
        } else {
            slots = new int[0];
        }
    }

    @Override
    public boolean hasInventoryBeenModified() {
        return inventoryModified;
    }

    @Override
    public boolean isValidSlot(int i) {
        return false;//todo remove
    }

    @Override
    public boolean addStackToSlot(int aIndex, ItemStack aStack) {//todo reomve
        if (GT_Utility.isStackInvalid(aStack)) return true;
        if (aIndex < 0 || aIndex >= getSizeInventory()) return false;
        ItemStack tStack = getStackInSlot(aIndex);
        if (GT_Utility.isStackInvalid(tStack)) {
            setInventorySlotContents(aIndex, aStack);
            return true;
        }
        aStack = GT_OreDictUnificator.get(aStack);
        if (GT_Utility.areStacksEqual(tStack, aStack) && tStack.stackSize + aStack.stackSize <= Math.min(aStack.getMaxStackSize(), getInventoryStackLimit())) {
            tStack.stackSize += aStack.stackSize;
            return true;
        }
        return false;
    }

    @Override
    public boolean addStackToSlot(int aIndex, ItemStack aStack, int aAmount) {
        return addStackToSlot(aIndex, GT_Utility.copyAmount(aAmount, aStack));//todo remove
    }


    @Override
    public int getSizeInventory() {
        if (alive()) {
            return inSize + outSize;
        }
        return 0;
    }

    @Override
    public ItemStack getStackInSlot(int aIndex) {
        if (alive()) {
            assert aIndex > inSize && aIndex < inSize + outSize;
            return out.get(aIndex - inSize);
        }
        return null;
    }

    @Override
    public ItemStack decrStackSize(int aIndex, int aAmount) {
        if (alive()) {
            assert aIndex > inSize && aIndex < inSize + outSize;
            inventoryModified = true;
            return out.reduce(aIndex - inSize, aAmount);
        }
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int aIndex, ItemStack aStack) {
        inventoryModified = true;
        if (alive()) {
            assert aIndex < inSize;
            in.set(aIndex, aStack);
        }
    }

    @Override
    public String getInventoryName() {
        if (alive()) return metaTileEntity.getInventoryName();
        if (GregTech_API.METATILEENTITIES[metaTileID] != null)
            return GregTech_API.METATILEENTITIES[metaTileID].getInventoryName();
        return "";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }


    @Override
    public boolean isUseableByPlayer(EntityPlayer aPlayer) {
        return alive() && timer > 40 && getTileEntityOffset(0, 0, 0) == this && aPlayer.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64 && metaTileEntity.isAccessAllowed(aPlayer);
    }


    @Override
    public int getInventoryStackLimit() {
        if (alive()) return metaTileEntity.getInventoryStackLimit();
        return 64;
    }

    @Override
    public void openInventory() {
        if (alive()) metaTileEntity.onOpenGUI();
    }

    @Override
    public void closeInventory() {
        if (alive()) metaTileEntity.onCloseGUI();
    }

    @Override
    public boolean isItemValidForSlot(int aIndex, ItemStack aStack) {
        return alive();
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int aSide) {
        if (alive() && letsItemsOut(aSide) && letsItemsOut(aSide))
            return slots;
        return new int[0];
    }

    /**
     * Can put aStack into Slot at Side
     */
    @Override
    public boolean canInsertItem(int aIndex, ItemStack aStack, int aSide) {
        return alive() && letsItemsIn(aSide) && aIndex < in.size() && in.allowInput(aIndex, aStack);
    }

    /**
     * Can pull aStack out of Slot from Side
     */
    @Override
    public boolean canExtractItem(int aIndex, ItemStack aStack, int aSide) {
        return alive() && letsItemsOut(aSide) && aIndex >= inSize && aIndex < inSize + outSize;
    }

    protected boolean letsItemsIn(int side) {
        return false;
    }

    protected boolean letsItemsOut(int side) {
        return false;
    }
}
