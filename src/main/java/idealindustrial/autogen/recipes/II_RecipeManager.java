package idealindustrial.autogen.recipes;

import com.google.gson.JsonParseException;
import idealindustrial.II_Core;
import idealindustrial.autogen.recipes.materialprocessing.II_AutogenRecipes;
import idealindustrial.recipe.*;
import idealindustrial.util.json.II_ArrayObject;

import java.io.InputStream;
import java.io.InputStreamReader;

public class II_RecipeManager {

    public static void load() {
        initAutogenFromCore();
        loadOtherRecipes();
        II_RecipeMaps.optimize();
    }

    private static void loadOtherRecipes() {
        for (II_RecipeMap<?> recipeMap : II_RecipeMaps.allRecipeMaps) {
            initRecipeMap(recipeMap);
        }
    }

    private static <T extends II_Recipe> void initRecipeMap(II_RecipeMap<T> map) {
        II_RecipeMapStorage<T> storage = map.getJsonReflection();
        InputStream stream = II_BasicRecipe.class.getResourceAsStream(storage.getFileName());
        if (stream != null) {
            try {
                II_ArrayObject<T> recipes = storage.getGson().fromJson(new InputStreamReader(stream), II_ArrayObject.getType(storage.getRecipeType()));
                for (T recipe : recipes.contents) {
                    map.addRecipe(recipe);
                }
            } catch (JsonParseException e) {
                e.printStackTrace();
            }
        }
    }


    private static void initAutogenFromCore() {
        InputStream stream = II_Core.class.getResourceAsStream("autogen.json");
        II_AutogenRecipes.loadAndInit(stream == null ? null : new InputStreamReader(stream));
    }
}

