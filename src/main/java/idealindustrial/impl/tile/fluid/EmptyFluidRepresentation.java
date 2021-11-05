package idealindustrial.impl.tile.fluid;

import idealindustrial.api.tile.fluid.FluidInventoryRepresentation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class EmptyFluidRepresentation implements FluidInventoryRepresentation {
    public static final FluidInventoryRepresentation INSTANCE = new EmptyFluidRepresentation();

    private EmptyFluidRepresentation() {

    }

    @Override
    public int getInSize() {
        return 0;
    }

    @Override
    public int getOutSize() {
        return 0;
    }

    @Override
    public FluidStack[] parseInputsClient() {
        return new FluidStack[0];
    }

    @Override
    public FluidStack[] parseOutputsClient() {
        return new FluidStack[0];
    }

    @Override
    public int getSizeInventory() {
        return 0;
    }

    @Override
    public ItemStack getStackInSlot(int p_70301_1_) {
        return null;
    }

    @Override
    public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_) {
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_) {

    }

    @Override
    public String getInventoryName() {
        return null;
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 0;
    }

    @Override
    public void markDirty() {

    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
        return false;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
        return false;
    }
}
