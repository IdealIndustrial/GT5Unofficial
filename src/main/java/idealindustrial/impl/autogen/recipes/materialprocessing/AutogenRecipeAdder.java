package idealindustrial.impl.autogen.recipes.materialprocessing;

import idealindustrial.impl.autogen.material.II_Material;
import idealindustrial.impl.autogen.recipes.RecipeAction;

import java.util.HashSet;
import java.util.Set;

public abstract class AutogenRecipeAdder {
    public Set<AutogenRecipeDefinition> blackList = new HashSet<>();

    private transient final AutogenRecipeDefinition definition = new AutogenRecipeDefinition(null, null);

    public boolean isBlackListed(II_Material material, RecipeAction action) {
        definition.material = material;
        definition.action = action;
        return blackList.contains(definition);
    }

    public abstract void addRecipes(II_Material material);
}
