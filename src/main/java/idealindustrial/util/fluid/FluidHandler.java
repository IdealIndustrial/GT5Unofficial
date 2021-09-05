package idealindustrial.util.fluid;

import idealindustrial.util.misc.II_NBTSerializable;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;

public interface FluidHandler extends IFluidHandler, II_NBTSerializable, Iterable<FluidStack> {

    FluidStack[] getFluids();

    default FluidStack get(int i) {
        return null;
    }

    default void set(int i, FluidStack stack) {

    }

    default int capacity() {
        return 0;
    }

}
