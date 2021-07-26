package idealindustrial.util.fluid;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

import java.util.Arrays;

public class II_MultiFluidHandler implements II_FluidHandler {

    public final int size;
    public int capacity;
    public FluidStack[] fluids;

    public II_MultiFluidHandler(int size, int capacity) {
        this.size = size;
        this.capacity = capacity;
        this.fluids = new FluidStack[size];
    }

    @Override
    public void nbtSave(NBTTagCompound tag, String prefix) {
        NBTTagCompound tankTag = new NBTTagCompound();
        for (int i = 0; i < size; i++) {
            if (fluids[i] != null) {
                tankTag.setTag("" + i, fluids[i].writeToNBT(new NBTTagCompound()));
            }
        }
        tag.setTag(prefix + "Tank", tankTag);
    }

    @Override
    public void nbtLoad(NBTTagCompound tag, String prefix) {
        NBTTagCompound tankTag = tag.getCompoundTag(prefix + "Tank");
        if (tankTag == null) {
            return;
        }
        for (int i = 0; i < size; i++) {
            NBTTagCompound fluidTag = tankTag.getCompoundTag("" + i);
            if (fluidTag != null) {
                fluids[i] = FluidStack.loadFluidStackFromNBT(fluidTag);
            }
        }
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        int freeSpace = -1;
        for (int i = 0; i < size; i++) {
            if (fluids[i] == null) {
                freeSpace = i;
                continue;
            }
            if (fluids[i].getFluid() == resource.getFluid()) {
                int fill = Math.min(capacity - fluids[i].amount, resource.amount);
                if (doFill) {
                    fluids[i].amount += fill;
                }
                return fill;
            }
        }
        if (freeSpace != -1) {
            int fill = Math.min(capacity, resource.amount);
            if (doFill) {
                fluids[freeSpace] = new FluidStack(resource, fill);
            }
            return fill;
        }
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        for (int i = 0; i < size; i++) {
            if (fluids[i] != null && fluids[i].getFluid() == resource.getFluid()) {
                int drain = Math.min(resource.amount, fluids[i].amount);
                if (doDrain) {
                    if ((fluids[i].amount -= drain) == 0) {
                        fluids[i] = null;
                    }
                }
                return new FluidStack(resource, drain);

            }
        }
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        for (int i = 0; i < size; i++) {
            if (fluids[i] != null) {
                if (doDrain) {
                    FluidStack ret = new FluidStack(fluids[i], Math.min(maxDrain, fluids[i].amount));
                    if ((fluids[i].amount -= ret.amount) == 0) {
                        fluids[i] = null;
                    }
                    return ret;
                }
                return new FluidStack(fluids[i], Math.min(maxDrain, fluids[i].amount));
            }
        }
        return null;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        for (int i = 0; i < size; i++) {
            if (fluids[i] == null || fluids[i].getFluid() == fluid) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return true;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return Arrays.stream(fluids).map(f -> new FluidTankInfo(f, capacity)).toArray(FluidTankInfo[]::new);
    }

    @Override
    public FluidStack[] getFluids() {
        return fluids;
    }

    @Override
    public FluidStack get(int i) {
        return fluids[i];
    }

    @Override
    public void set(int i, FluidStack stack) {
        fluids[i] = stack;
    }

    @Override
    public int capacity() {
        return capacity;
    }
}
