package idealindustrial.impl.tile.fluid;

import idealindustrial.api.tile.fluid.FluidHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

import java.util.Iterator;

@SuppressWarnings("NullableProblems")
public class EmptyTank implements FluidHandler {

    public static final EmptyTank INSTANCE = new EmptyTank();

    private EmptyTank() {

    }

    @Override
    public void nbtSave(NBTTagCompound tag, String prefix) {

    }

    @Override
    public void nbtLoad(NBTTagCompound tag, String prefix) {

    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        return null;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return false;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[0];
    }

    @Override
    public FluidStack[] getFluids() {
        return new FluidStack[0];
    }

    @Override
    public Iterator<FluidStack> iterator() {
        return new Iterator<FluidStack>() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public FluidStack next() {
                return null;
            }
        };
    }
}
