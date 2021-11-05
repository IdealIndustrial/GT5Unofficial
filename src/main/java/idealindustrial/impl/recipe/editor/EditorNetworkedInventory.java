package idealindustrial.impl.recipe.editor;

import idealindustrial.api.recipe.IRecipeGuiParams;
import idealindustrial.api.tile.host.NetworkedInventory;
import idealindustrial.api.tile.fluid.FluidHandler;
import idealindustrial.impl.tile.fluid.FluidInventoryReprepresentationImpl;
import idealindustrial.api.tile.fluid.FluidInventoryRepresentation;
import idealindustrial.impl.tile.fluid.MultiFluidHandler;
import idealindustrial.impl.tile.inventory.ArrayRecipedInventory;
import idealindustrial.api.tile.inventory.InternalInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class EditorNetworkedInventory implements NetworkedInventory {

    InternalInventory in, out, special;
    FluidHandler inTank, outTank;
    String name;

    public EditorNetworkedInventory(String name, IRecipeGuiParams params) {
        this.name = name;
        this.in = new ArrayRecipedInventory(params.getItemsIn().length, 64);
        this.out = new ArrayRecipedInventory(params.getItemsOut().length, 64);
        this.special = new ArrayRecipedInventory(params.getItemSpecial().length, 64);
        this.inTank = new MultiFluidHandler(params.getFluidsIn().length, 10000);
        this.outTank = new MultiFluidHandler(params.getFluidsOut().length, 10000);
    }

    @Override
    public InternalInventory getIn() {
        return in;
    }

    @Override
    public InternalInventory getOut() {
        return out;
    }

    @Override
    public InternalInventory getSpecial() {
        return special;
    }

    @Override
    public boolean hasFluidTank() {
        return true;
    }

    @Override
    public FluidHandler getInTank() {
        return inTank;
    }

    @Override
    public FluidHandler getOutTank() {
        return outTank;
    }

    @Override
    public FluidInventoryRepresentation getFluidRepresentation() {
        return new FluidInventoryReprepresentationImpl(inTank.getFluids(), outTank.getFluids());
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }

    @Override
    public int getSizeInventory() {
        return 0;
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        if (i < getIn().size()) {
            return getIn().get(i);
        }
        i -= getIn().size();
        if (i < getOut().size()) {
            return getOut().get(i);
        }
        return null;
    }

    @Override
    public ItemStack decrStackSize(int i, int amount) {
        if (i < getIn().size()) {
            return getIn().reduce(i, amount);
        }
        i -= getIn().size();
        if (i < getOut().size()) {
            return getOut().reduce(i, amount);
        }
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack is) {
        if (i < getIn().size()) {
            getIn().set(i, is);
            return;
        }
        i -= getIn().size();
        if (i < getOut().size()) {
             getOut().set(i, is);
        }
    }

    @Override
    public String getInventoryName() {
        return name;
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
    public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
        return true;
    }
}
