package idealindustrial.recipe;

import java.util.ArrayList;
import java.util.List;

public class II_RecipeMaps {
    public static List<II_RecipeMap<?>> allRecipeMaps = new ArrayList<>();
    public static II_RecipeMap<II_BasicRecipe> benderRecipes = new II_BasicRecipeMap<>("Bender Recipes", true, false,
            new II_RecipeGuiParamsBuilder(1, 1, 0, 0, 0).construct(), II_BasicRecipe.class);

    public static void optimize() {
        for (II_RecipeMap<?> map : allRecipeMaps) {
            map.optimize();
        }
    }
}
