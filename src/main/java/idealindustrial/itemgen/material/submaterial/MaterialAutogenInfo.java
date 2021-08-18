package idealindustrial.itemgen.material.submaterial;

import idealindustrial.itemgen.material.Prefixes;
import idealindustrial.itemgen.recipes.RecipeAction;

import java.util.HashSet;
import java.util.Set;

public class MaterialAutogenInfo {
    protected Set<RecipeAction> actions;

    public MaterialAutogenInfo(Set<RecipeAction> actions) {
        this.actions = actions;
    }

    public boolean isActionAllowed(RecipeAction action) {
        return actions.contains(action);
    }
}
