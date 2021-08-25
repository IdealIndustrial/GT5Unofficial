package idealindustrial.autogen.material.submaterial;

import idealindustrial.autogen.recipes.RecipeAction;

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
