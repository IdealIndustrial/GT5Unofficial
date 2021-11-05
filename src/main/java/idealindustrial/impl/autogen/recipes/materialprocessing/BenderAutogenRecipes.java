package idealindustrial.impl.autogen.recipes.materialprocessing;

import idealindustrial.impl.autogen.material.II_Material;
import idealindustrial.impl.recipe.RecipeMaps;

import static idealindustrial.impl.autogen.material.Prefixes.ingot;
import static idealindustrial.impl.autogen.material.Prefixes.plate;
import static idealindustrial.impl.oredict.OreDict.get;
import static idealindustrial.impl.oredict.OreDict.getMain;
import static idealindustrial.impl.autogen.recipes.RecipeAction.plateBending;
import static idealindustrial.impl.recipe.RecipeBuilder.basicBuilder;

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
