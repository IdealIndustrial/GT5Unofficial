package idealindustrial.recipe;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import idealindustrial.util.json.II_JsonUtil;

public class II_RecipeMapStorage <R extends II_Recipe>{
    String fileName;
    Class<R> recipeType;

    public II_RecipeMapStorage(String fileName, Class<R> recipeType) {
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
        return II_JsonUtil.recipeDefaultGson;
    }

}
