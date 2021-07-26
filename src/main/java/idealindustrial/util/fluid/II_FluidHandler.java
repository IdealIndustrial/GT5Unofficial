package idealindustrial.util.fluid;

import idealindustrial.util.misc.II_NBTSerializable;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;

public interface II_FluidHandler extends IFluidHandler, II_NBTSerializable {

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
