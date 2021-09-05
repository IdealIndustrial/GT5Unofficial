package idealindustrial.autogen.recipes;

import com.google.gson.JsonParseException;
import idealindustrial.II_Core;
import idealindustrial.autogen.recipes.materialprocessing.AutogenRecipes;
import idealindustrial.recipe.*;
import idealindustrial.util.json.ArrayObject;

import java.io.InputStream;
import java.io.InputStreamReader;

public class RecipeManager {

    public static void load() {
        initAutogenFromCore();
        loadOtherRecipes();
        RecipeMaps.optimize();
    }

    private static void loadOtherRecipes() {
        for (RecipeMap<?> recipeMap : RecipeMaps.allRecipeMaps) {
            initRecipeMap(recipeMap);
        }
    }

    private static <T extends IMachineRecipe> void initRecipeMap(RecipeMap<T> map) {
        RecipeMapStorage<T> storage = map.getJsonReflection();
        InputStream stream = BasicMachineRecipe.class.getResourceAsStream(storage.getFileName());
        if (stream != null) {
            try {
                ArrayObject<T> recipes = storage.getGson().fromJson(new InputStreamReader(stream), ArrayObject.getType(storage.getRecipeType()));
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
        AutogenRecipes.loadAndInit(stream == null ? null : new InputStreamReader(stream));
    }
}

