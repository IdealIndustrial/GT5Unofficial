package idealindustrial.util.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class InternalInventoryWrapper implements IInventory {

    protected InternalInventory internalInventory;

    public InternalInventoryWrapper(InternalInventory internalInventory) {
        this.internalInventory = internalInventory;
    }

    @Override
    public int getSizeInventory() {
        return internalInventory.size();
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return internalInventory.get(i);
    }

    @Override
    public ItemStack decrStackSize(int i, int amount) {
        return internalInventory.reduce(i, amount);
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack is) {
        internalInventory.set(i, is);
    }

    @Override
    public String getInventoryName() {
        return internalInventory.getClass().getSimpleName();
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void markDirty() {

    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
        return true;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack is) {
        return true;
    }
}
