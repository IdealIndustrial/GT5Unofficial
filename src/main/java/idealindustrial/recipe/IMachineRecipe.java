package idealindustrial.recipe;

import idealindustrial.util.fluid.FluidHandler;
import idealindustrial.util.inventory.interfaces.RecipedInventory;
import idealindustrial.util.item.HashedStack;
import idealindustrial.util.item.II_ItemStack;
import idealindustrial.util.item.II_StackSignature;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public interface IMachineRecipe {


    List<HashedStack> getAllPossibleInputs(); // all possible inputs for this recipe

    II_StackSignature[] getInputs();

    II_ItemStack[] getOutputs();

    FluidStack[] getFluidInputs();

    FluidStack[] getFluidOutputs();

    RecipeEnergyParams recipeParams();

    boolean isInputEqualStacks(RecipedInventory inventory, FluidHandler fluidInputs, boolean doConsume);

    void optimize();
}
