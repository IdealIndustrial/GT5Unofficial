package idealindustrial.recipe;

import com.google.gson.Gson;
import idealindustrial.util.json.JsonUtil;

public class RecipeMapStorage<R extends IMachineRecipe>{
    String fileName;
    Class<R> recipeType;

    public RecipeMapStorage(String fileName, Class<R> recipeType) {
        this.fileName = fileName;
        this.recipeType = recipeType;
    }

    public String getFileName() {
        return fileName;
    }

    public Class<R> getRecipeType() {
        return recipeType;
    }

    public Gson getGson() {
        return JsonUtil.recipeDefaultGson;
    }

}
