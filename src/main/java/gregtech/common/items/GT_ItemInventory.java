package gregtech.common.items;

import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class GT_ItemInventory implements IInventory {
    ItemStack[] mContents;
    boolean mDropItems;
    String mName;


    public GT_ItemInventory(int aSize, boolean aDropItemsOnClosing, String aName) {
        mContents = new ItemStack[aSize];
        mDropItems = aDropItemsOnClosing;
        mName = aName;
    }
    @Override
    public int getSizeInventory() {
        return mContents.length;
    }

    @Override
    public ItemStack getStackInSlot(int aIndex) {
        return mContents[aIndex];
    }

    @Override
    public ItemStack decrStackSize(int aIndex, int aAmount) {
        ItemStack tStack = getStackInSlot(aIndex), rStack = GT_Utility.copy(tStack);
        if (tStack != null) {
            if (tStack.stackSize <= aAmount) {
                setInventorySlotContents(aIndex, null);
            } else {
                rStack = tStack.splitStack(aAmount);
                if (tStack.stackSize == 0)
                    setInventorySlotContents(aIndex, null);
            }
        }
        return rStack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int aIndex) {
        if (mDropItems)
            return mContents[aIndex];
        return null;
    }

    @Override
    public void setInventorySlotContents(int aIndex, ItemStack aStack) {
        mContents[aIndex] = aStack;
    }

    @Override
    public String getInventoryName() {
        return mName;
    }

    @Override
    public boolean hasCustomInventoryName() {
        return true;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void markDirty() {
        /**/
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer aPlayer) {
        return true;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    public void saveToNBT(NBTTagCompound aNBT) {
        for (int i = 0; i < mContents.length; i++) {
            if (mContents[i] != null)
                aNBT.setTag("s"+i, mContents[i].writeToNBT(new NBTTagCompound()));
            else
                aNBT.removeTag("s"+i);
        }
    }

    public void loadFromNBT(NBTTagCompound aNBT) {
        for (int i = 0; i < mContents.length; i++) {
            if (aNBT.hasKey("s"+i))
                mContents[i] = ItemStack.loadItemStackFromNBT(aNBT.getCompoundTag("s"+i));
        }
    }

    @Override
    public boolean isItemValidForSlot(int aIndex, ItemStack aStack) {
        return true;
    }
}
