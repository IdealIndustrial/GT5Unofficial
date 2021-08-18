package idealindustrial.itemgen.recipes;

import idealindustrial.itemgen.material.II_Material;

import static idealindustrial.itemgen.material.Prefixes.ingot;
import static idealindustrial.itemgen.material.Prefixes.plate;
import static idealindustrial.itemgen.oredict.II_OreDict.get;
import static idealindustrial.itemgen.recipes.RecipeAction.plateBending;
import static idealindustrial.recipe.II_RecipeBuilder.basicBuilder;
import static idealindustrial.recipe.II_RecipeMaps.sBenderRecipes;

public class II_BenderAutogenRecipes extends II_AutogenRecipeAdder {

    protected II_BenderAutogenRecipes() {

    }

    @Override
    public void addRecipes(II_Material material) {
        if (material.getAutogenInfo().isActionAllowed(plateBending) && !isBlackListed(material, plateBending)) {
            sBenderRecipes.addRecipe(basicBuilder()
                    .addInputs(get(ingot, material, 1))
                    .addOutputs(get(plate, material, 1))
                    .addEnergyValues(20, 20)
                    .construct());
        }
    }
}
