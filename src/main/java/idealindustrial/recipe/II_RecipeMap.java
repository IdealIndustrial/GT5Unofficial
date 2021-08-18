package idealindustrial.recipe;

import idealindustrial.util.fluid.II_FluidHandler;
import idealindustrial.util.inventory.II_RecipedInventory;
import java.util.List;

public interface II_RecipeMap<R extends II_Recipe> {

    R findRecipe(II_RecipedInventory inventory, II_FluidHandler fluidInputs, II_MachineEnergyParams params);

    boolean addRecipe(R recipe);

    List<R> getAllRecipes();

    //todo reinit call on IDs reFuck
}
