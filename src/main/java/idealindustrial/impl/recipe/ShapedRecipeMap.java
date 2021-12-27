package idealindustrial.impl.recipe;

import idealindustrial.api.recipe.IMachineRecipe;
import idealindustrial.api.recipe.IRecipeGuiParams;
import idealindustrial.api.recipe.RecipeMap;
import idealindustrial.api.tile.fluid.FluidHandler;
import idealindustrial.api.tile.inventory.InternalInventory;
import idealindustrial.api.tile.inventory.RecipedInventory;

import java.util.ArrayList;
import java.util.List;

@Deprecated //use regular recipe map
public class ShapedRecipeMap<R extends IMachineRecipe> extends AbstractRecipeMap<R> {

    List<R> recipes = new ArrayList<>();

    public ShapedRecipeMap(String name, IRecipeGuiParams params) {
        super(name, params);
    }

    @Override
    public R findRecipe(RecipedInventory inventory, InternalInventory special, FluidHandler fluidInputs, MachineEnergyParams params) {
        assert inventory.isIndexed();
        //todo: O(n^2) is bad, may be optimize
        for (R recipe : recipes) {
            if (recipe.isInputEqualStacks(inventory, fluidInputs, false)) {
                return recipe;
            }
        }
        return null;
    }

    @Override
    public boolean addRecipe(R recipe) {
        recipes.add(recipe);
        return true;//todo: add check for conflicts
    }

    @Override
    public List<R> getAllRecipes() {
        return recipes;
    }

    @Override
    public RecipeMap<R> newEmpty() {
        return new ShapedRecipeMap<>(name, getGuiParams());
    }
}
