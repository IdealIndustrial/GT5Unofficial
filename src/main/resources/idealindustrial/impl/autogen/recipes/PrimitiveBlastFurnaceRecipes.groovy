package idealindustrial.impl.autogen.recipes


import idealindustrial.impl.recipe.BasicMachineRecipe

import static idealindustrial.impl.autogen.material.II_Materials.*
import static idealindustrial.impl.autogen.material.Prefixes.block
import static idealindustrial.impl.oredict.OreDict.get
import static idealindustrial.impl.recipe.RecipeBuilder.basicBuilder

class PrimitiveBlastFurnaceRecipeGenerator extends RecipeGenerator<BasicMachineRecipe> {


    @Override
    void run() {
        basicBuilder()
                .addInputs(get(block, iron, 10), get(block, coal, 10))
                .addOutputs(get(block, steel, 1))
                .addEnergyValues(0, 0, 20 * 200)
                .addTo(map)
    }
}
