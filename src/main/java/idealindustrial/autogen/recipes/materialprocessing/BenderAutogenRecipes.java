package idealindustrial.autogen.recipes.materialprocessing;

import idealindustrial.autogen.material.II_Material;
import idealindustrial.recipe.RecipeMaps;

import static idealindustrial.autogen.material.Prefixes.ingot;
import static idealindustrial.autogen.material.Prefixes.plate;
import static idealindustrial.autogen.oredict.OreDict.get;
import static idealindustrial.autogen.oredict.OreDict.getMain;
import static idealindustrial.autogen.recipes.RecipeAction.plateBending;
import static idealindustrial.recipe.RecipeBuilder.basicBuilder;

public class BenderAutogenRecipes extends AutogenRecipeAdder {

    protected BenderAutogenRecipes() {

    }

    @Override
    public void addRecipes(II_Material material) {
        if (material.getAutogenInfo().isActionAllowed(plateBending) && !isBlackListed(material, plateBending)) {
            RecipeMaps.benderRecipes.addRecipe(basicBuilder()
                    .addInputs(get(ingot, material, 1))
                    .addOutputs(getMain(plate, material, 1))
                    .addEnergyValues(20, 1,20)
                    .construct());
        }
    }
}
