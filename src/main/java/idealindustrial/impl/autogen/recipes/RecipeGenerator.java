package idealindustrial.impl.autogen.recipes;

import idealindustrial.api.recipe.IMachineRecipe;
import idealindustrial.api.recipe.RecipeMap;

public abstract class RecipeGenerator<R extends IMachineRecipe> implements Runnable {
    protected RecipeMap<R> map;

    public void setMap(RecipeMap<R> map) {
        this.map = map;
    }

    protected void add(R recipe) {
        map.addRecipe(recipe);
    }

}
