package idealindustrial.impl.autogen.recipes

import idealindustrial.impl.autogen.material.II_Material
import idealindustrial.impl.recipe.BasicMachineRecipe

import static idealindustrial.impl.autogen.material.II_Materials.stream
import static idealindustrial.impl.autogen.material.Prefixes.nuggetBig
import static idealindustrial.impl.autogen.material.Prefixes.nuggetBigHot
import static idealindustrial.impl.autogen.material.submaterial.MaterialAutogenInfo.HeatType.PrimitiveForge
import static idealindustrial.impl.oredict.OreDict.exists
import static idealindustrial.impl.oredict.OreDict.get
import static idealindustrial.impl.recipe.RecipeBuilder.basicBuilder

class PrimitiveForgeRecipeGenerator extends RecipeGenerator<BasicMachineRecipe> {

    Set<II_Material> blacklist = new HashSet<>()

    @Override
    void run() {
        stream().filter({ m -> !blacklist.contains(m) })
                .filter({ m -> m.getAutogenInfo().isHeatTypeAllowed(PrimitiveForge) })
                .filter({ m -> exists(m, nuggetBig, nuggetBigHot) })
                .forEach({ m ->
                    basicBuilder().addInputs(get(nuggetBig, m, 1))
                            .addOutputs(get(nuggetBigHot, m, 1))
                            .addEnergyValues(0, 0, m.getAutogenInfo().heatingHardness * 20)
                            .addTo(map)

                })
    }
}
