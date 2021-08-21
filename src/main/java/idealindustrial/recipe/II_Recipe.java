package idealindustrial.recipe;

import idealindustrial.util.fluid.II_FluidHandler;
import idealindustrial.util.inventory.II_RecipedInventory;
import idealindustrial.util.item.II_HashedStack;
import idealindustrial.util.item.II_ItemStack;
import idealindustrial.util.item.II_StackSignature;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public interface II_Recipe {


    List<II_HashedStack> getAllPossibleInputs(); // all possible inputs for this recipe

    II_StackSignature[] getInputs();

    II_ItemStack[] getOutputs();

    FluidStack[] getFluidInputs();

    FluidStack[] getFluidOutputs();

    II_RecipeEnergyParams recipeParams();

    boolean isInputEqualStacks(II_RecipedInventory inventory, II_FluidHandler fluidInputs, boolean doConsume);

    void optimize();
}
