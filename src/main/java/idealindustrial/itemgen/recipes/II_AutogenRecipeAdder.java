package idealindustrial.itemgen.recipes;

import com.google.gson.GsonBuilder;
import idealindustrial.itemgen.material.II_Material;
import scala.actors.threadpool.Arrays;

import java.util.HashSet;
import java.util.Set;

public abstract class II_AutogenRecipeAdder {
    public Set<AutogenRecipeDefinition> blackList = new HashSet<>();

    private final AutogenRecipeDefinition definition = new AutogenRecipeDefinition(null, null);

    public boolean isBlackListed(II_Material material, RecipeAction action) {
        definition.material = material;
        definition.action = action;
        return blackList.contains(definition);
    }

    public abstract void addRecipes(II_Material material);
}
