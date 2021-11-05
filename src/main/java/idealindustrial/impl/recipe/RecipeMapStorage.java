package idealindustrial.impl.recipe;

import com.google.gson.Gson;
import idealindustrial.api.recipe.IMachineRecipe;
import idealindustrial.api.recipe.RecipeMap;
import idealindustrial.util.json.ArrayObject;
import idealindustrial.util.json.JsonUtil;

public class RecipeMapStorage<R extends IMachineRecipe>{
    String fileName;
    Class<R> recipeType;
    RecipeMap<R> map;

    public RecipeMapStorage(String fileName, Class<R> recipeType, RecipeMap<R> map) {
        this.fileName = fileName;
        this.recipeType = recipeType;
        this.map = map;
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

    public ArrayObject<R> toArrayObject() {
        return new ArrayObject<R>(map.getAllRecipes().toArray((R[])new IMachineRecipe[0]));
    }

}
