package idealindustrial.teststuff;

import idealindustrial.api.recipe.RecipeMap;
import idealindustrial.impl.autogen.material.II_Material;
import idealindustrial.impl.autogen.material.II_Materials;
import idealindustrial.impl.recipe.RecipeMaps;
import idealindustrial.impl.recipe.ShapedMachineRecipe;

import static idealindustrial.impl.autogen.material.Prefixes.*;
import static idealindustrial.impl.oredict.OreDict.get;
import static idealindustrial.impl.recipe.RecipeBuilder.shapedBuilder;

public class TestRecipes implements Runnable {
    @Override
    public void run() {
        RecipeMap<ShapedMachineRecipe> anvilMap = RecipeMaps.getMap("primitive anvil recipes");
        II_Material mat = II_Materials.arsenicBronze;
        shapedBuilder().addInputs(
                        get(nuggetBigHot, mat, 1), get(nuggetBigHot, mat, 1), get(nuggetBigHot, mat, 1),
                        get(nugget, mat, 1), null, get(nugget, mat, 1)
                )
                .addOutputs(get(toolHeadPickaxe, mat, 1))
                .addEnergyValues(0, 0, 1)
                .addTo(anvilMap);
    }
}
