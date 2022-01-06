package idealindustrial.impl.autogen.recipes

import idealindustrial.impl.autogen.material.II_Material
import idealindustrial.impl.autogen.material.MaterialStack
import idealindustrial.impl.autogen.material.Prefixes
import idealindustrial.impl.item.behaviors.BehaviorPrimitiveMold
import idealindustrial.impl.item.stack.CheckType
import idealindustrial.impl.item.stack.II_ItemStack
import idealindustrial.impl.item.stack.II_StackSignature
import idealindustrial.impl.recipe.BasicMachineRecipe

import static idealindustrial.impl.autogen.material.II_Materials.bronze
import static idealindustrial.impl.autogen.material.II_Materials.stream
import static idealindustrial.impl.autogen.material.Prefixes.*
import static idealindustrial.impl.autogen.material.submaterial.MaterialAutogenInfo.HeatType.PrimitiveForge
import static idealindustrial.impl.oredict.OreDict.exists
import static idealindustrial.impl.oredict.OreDict.get
import static idealindustrial.impl.recipe.RecipeBuilder.basicBuilder
import static idealindustrial.util.misc.II_StreamUtil.list

class PrimitiveForgeRecipeGenerator extends RecipeGenerator<BasicMachineRecipe> {

    Set<II_Material> blacklist = new HashSet<>()

    List<II_Material> alloys = list(bronze)
    List<Prefixes> alloyInputPrefixes = list(ingot, dust)
    List<Prefixes> moldPrefixes = list(ingot, plate, toolHeadPickaxe)

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

        alloys.forEach({ alloy ->
            List<MaterialStack> subMaterials = alloy.chemicalInfo.subMaterials
//            alloyInputPrefixes.forEach({inPref ->
            List<II_StackSignature> inputs = subMaterials.stream().map({ ms -> ms.forPrefix(dust) }).collect()
            int amount = inputs.stream().mapToInt({ is -> is.amount() }).sum()
            moldPrefixes.forEach({ mold ->
                II_ItemStack emptyMold = BehaviorPrimitiveMold.getMold(mold, null, amount)
                II_ItemStack fullMold = BehaviorPrimitiveMold.getMold(mold, alloy, amount)
                basicBuilder()
                        .addInputs(inputs)
                        .addInputs(new II_StackSignature(emptyMold, CheckType.DIRECT))
                        .addOutputs(fullMold)
                        .addEnergyValues(0, 0, alloy.autogenInfo.heatingHardness)
                        .addTo(map)
            })

//            })
        })
    }
}
