package idealindustrial.util.fluid;

import cpw.mods.fml.common.FMLCommonHandler;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

//just hackery class to sync fluid display inventory with client
public class II_FluidInvReprImpl implements II_FluidInventoryRepresentation {

    int size;
    FluidStack[] in, out;
    ItemStack[] stacks;
    boolean isClient;

    public II_FluidInvReprImpl(FluidStack[] in, FluidStack[] out) {
        this.size = in.length + out.length;
        this.in = in;
        this.out = out;
        this.stacks = new ItemStack[size];
        this.isClient = FMLCommonHandler.instance().getEffectiveSide().isClient();
    }

    @Override
    public int getSizeInventory() {
        return size;
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        if (isClient) {//no fluids on client so fluids are converted to items and sent to client
            return stacks[i];
        }
        if (i < getInSize()) {
            return GT_Utility.getFluidDisplayStack(in[i], true);
        }
        i -= getInSize();
        if (i < out.length) {
            return GT_Utility.getFluidDisplayStack(out[i], true);
        }
        return null;
    }

    @Override
    public ItemStack decrStackSize(int i, int amount) {
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack stack) {
        if (isClient) {//set for client when processing item packet
            stacks[i] = stack;
        }
    }

    @Override
    public String getInventoryName() {
        return "name";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public void markDirty() {

    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer inv) {
        return true;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack) {
        return false;
    }

    @Override
    public int getInSize() {
        return in.length;
    }
}
