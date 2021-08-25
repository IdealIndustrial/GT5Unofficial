package idealindustrial.recipe;

import idealindustrial.util.fluid.II_FluidHandler;
import idealindustrial.util.inventory.II_RecipedInventory;
import idealindustrial.util.item.II_StackSignature;

import java.util.List;
import java.util.Set;

public interface II_RecipeMap<R extends II_Recipe> {

    R findRecipe(II_RecipedInventory inventory, II_FluidHandler fluidInputs, II_MachineEnergyParams params);

    boolean addRecipe(R recipe);

    List<R> getAllRecipes();

    void optimize();

    String getName();

    II_RecipeGuiParams getGuiParams();

    Set<R> getCraftingRecipes(II_StackSignature signature);

    Set<R> getUsageRecipes(II_StackSignature signature);

    II_RecipeMapStorage<R> getJsonReflection();

//    boolean isInputEqualStacks(R recipe, II_RecipedInventory inventory, II_FluidHandler fluidInputs, boolean doConsume);

    //todo reinit call on IDs reFuck
}
