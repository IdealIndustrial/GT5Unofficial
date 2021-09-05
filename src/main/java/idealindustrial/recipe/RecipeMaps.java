package idealindustrial.recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeMaps {
    public static List<RecipeMap<?>> allRecipeMaps = new ArrayList<>();
    public static RecipeMap<BasicMachineRecipe> benderRecipes = new BasicRecipeMap<>("Bender Recipes", true, false,
            new RecipeGuiParamsBuilder(1, 1, 0, 0, 0).construct(), BasicMachineRecipe.class);

    public static void optimize() {
        for (RecipeMap<?> map : allRecipeMaps) {
            map.optimize();
        }
    }
}
