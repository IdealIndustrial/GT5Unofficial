package idealindustrial.impl.autogen.recipes

import idealindustrial.impl.autogen.material.II_Material
import idealindustrial.impl.recipe.BasicMachineRecipe

import static idealindustrial.impl.autogen.material.II_Materials.stream
import static idealindustrial.impl.autogen.material.Prefixes.ingot
import static idealindustrial.impl.autogen.material.Prefixes.plate
import static idealindustrial.impl.oredict.OreDict.exists
import static idealindustrial.impl.oredict.OreDict.get
import static idealindustrial.impl.recipe.RecipeBuilder.basicBuilder

class BenderRecipeGenerator extends RecipeGenerator<BasicMachineRecipe> {

    Set<II_Material> blacklist = new HashSet<>()

    @Override
    void run() {
        stream().filter({ m -> !blacklist.contains(m) })
                .filter({ m -> exists(m, ingot, plate)})
                .forEach({ m ->
                    basicBuilder().addInputs(get(ingot, m, 1))
                            .addOutputs(get(plate, m, 1))
                            .addEnergyValues(4, 1, 30)
                            .addTo(map)

                })
    }
}
