package idealindustrial.api.recipe;

import idealindustrial.impl.item.stack.CheckType;
import idealindustrial.impl.recipe.RecipeEnergyParams;
import idealindustrial.api.tile.fluid.FluidHandler;
import idealindustrial.api.tile.inventory.RecipedInventory;
import idealindustrial.impl.item.stack.HashedStack;
import idealindustrial.impl.item.stack.II_ItemStack;
import idealindustrial.impl.item.stack.II_StackSignature;
import net.minecraftforge.fluids.FluidStack;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public interface IMachineRecipe {


    List<HashedStack> getAllPossibleInputs(); // all possible inputs for this recipe

    II_StackSignature[] getInputs();

    II_ItemStack[] getOutputs();

    FluidStack[] getFluidInputs();

    FluidStack[] getFluidOutputs();

    RecipeEnergyParams recipeParams();

    boolean isInputEqualStacks(RecipedInventory inventory, FluidHandler fluidInputs, boolean doConsume);

    void optimize();


    default boolean containsInput(II_StackSignature signature) {
        return Arrays.stream(getInputs()).filter(Objects::nonNull).map(II_StackSignature::getAsStack).anyMatch(signature::equals);
    }

    default boolean containsOutput(II_StackSignature signature) {
        return Arrays.stream(getOutputs()).filter(Objects::nonNull).anyMatch(signature::equals);
    }
}
