package idealindustrial.impl.recipe;

import com.google.gson.Gson;
import idealindustrial.api.recipe.IMachineRecipe;
import idealindustrial.api.recipe.RecipeMap;
import idealindustrial.util.json.ArrayObject;
import idealindustrial.util.json.JsonUtil;

public class RecipeMapStorage<R extends IMachineRecipe>{
    String recipesFileName, autogenFileName;
    Class<R> recipeType;
    RecipeMap<R> map;

    public RecipeMapStorage(String recipesFileName, String autogenFileName, Class<R> recipeType, RecipeMap<R> map) {
        this.recipesFileName = recipesFileName;
        this.autogenFileName = autogenFileName;
        this.recipeType = recipeType;
        this.map = map;
    }

    public String getRecipesFileName() {
        return recipesFileName;
    }

    public String getAutogenFileName() {
        return autogenFileName;
    }

    public Class<R> getRecipeType() {
        return recipeType;
    }

    public Gson getGson() {
        return JsonUtil.recipeDefaultGson;
    }

    @SuppressWarnings("unchecked")
    public ArrayObject<R> toArrayObject() {
        return new ArrayObject<>(map.getAllRecipes().toArray((R[])new IMachineRecipe[0]));
    }

}
