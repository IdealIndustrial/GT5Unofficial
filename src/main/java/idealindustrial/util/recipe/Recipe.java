package idealindustrial.util.recipe;

import idealindustrial.util.item.II_HashedStack;
import idealindustrial.util.item.II_ItemStack;
import idealindustrial.util.item.II_StackSignature;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public interface Recipe {


    List<II_HashedStack> getAllPossibleInputs(); // all possible inputs for this recipe

    II_StackSignature[] getInputs();

    II_ItemStack[] getOutputs();

    FluidStack[] getFluidInputs();

    FluidStack[] getFluidOutputs();

    long energy();

    long duration();

    MachineParams recipeParams();
}
