package idealindustrial.autogen.recipes;

import idealindustrial.autogen.material.II_Material;

public class AutogenRecipeDefinition {
    protected II_Material material;
    protected RecipeAction action;

    public AutogenRecipeDefinition(II_Material material, RecipeAction action) {
        this.material = material;
        this.action = action;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AutogenRecipeDefinition that = (AutogenRecipeDefinition) o;
        return material == that.material &&
                action == that.action;
    }

    @Override
    public int hashCode() {
        return material.hashCode() * 31 +  action.hashCode();
    }
}
