package idealindustrial.util.fluid;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class II_SingleFluidHandler implements II_FluidHandler {

    FluidStack fluid;
    int maxSize;

    public II_SingleFluidHandler(int size) {
        maxSize = size;
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        assert resource != null;
        if (fluid == null) {
            int amount = Math.min(maxSize, resource.amount);
            if (doFill) {
                fluid = resource;
                fluid.amount = amount;
            }
            return amount;
        }
        if (resource.getFluid() == fluid.getFluid()) {
            int space = maxSize - fluid.amount;
            int amount = Math.min(space, resource.amount);
            if (doFill) {
                fluid.amount += amount;
            }
            return amount;
        }
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        assert resource != null;
        if (fluid != null && fluid.getFluid() == resource.getFluid()) {
            int drain = Math.min(fluid.amount, resource.amount);
            FluidStack f = fluid.copy();
            f.amount = drain;
            if (doDrain) {
                fluid.amount -= drain;
                if (fluid.amount == 0) {
                    fluid = null;
                }
            }
            return f;
        }
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        if (fluid != null) {
            int drain = Math.min(maxDrain, fluid.amount);
            FluidStack f = fluid.copy();
            f.amount = drain;
            if (doDrain) {
                fluid.amount -= drain;
                if (fluid.amount == 0) {
                    fluid = null;
                }
            }
            return f;
        }
        return null;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        assert fluid != null;
        return this.fluid == null || this.fluid.amount < maxSize && this.fluid.getFluid() == fluid;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        assert fluid != null;
        return this.fluid != null && this.fluid.getFluid() == fluid;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[]{new FluidTankInfo(fluid, maxSize)};
    }

    @Override
    public void nbtSave(NBTTagCompound tag, String prefix) {
        NBTTagCompound fl = new NBTTagCompound();
        if (fluid != null) {
            fluid.writeToNBT(fl);
            tag.setTag(prefix + "tank", fl);
        }

    }

    @Override
    public void nbtLoad(NBTTagCompound tag, String prefix) {
        NBTTagCompound fl = tag.getCompoundTag(prefix + "tank");
        if (fl == null) {
            fluid = null;
        }
        else {
            fluid = FluidStack.loadFluidStackFromNBT(fl);
        }
    }

    @Override
    public FluidStack[] getFluids() {
        return new FluidStack[]{fluid};
    }
}
