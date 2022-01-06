package idealindustrial.impl.autogen.recipes

import idealindustrial.impl.autogen.material.II_Material
import idealindustrial.impl.oredict.OreInfo
import idealindustrial.impl.recipe.BasicMachineRecipe
import idealindustrial.impl.recipe.RecipeBuilder
import idealindustrial.impl.registries.FuelRegistry

import static idealindustrial.impl.autogen.material.II_Materials.*
import static idealindustrial.impl.autogen.material.Prefixes.*
import static idealindustrial.impl.oredict.OreDict.get
import static idealindustrial.impl.recipe.RecipeBuilder.basicBuilder

class PrimitiveFurnaceRecipeGenerator extends RecipeGenerator<BasicMachineRecipe> {

    static class Entry {
        II_Material oreMaterial
        II_Material ingotMaterial
        int requiredFuelValue

        Entry(II_Material oreMaterial, II_Material ingotMaterial, int requiredFuelValue) {
            this.oreMaterial = oreMaterial
            this.ingotMaterial = ingotMaterial
            this.requiredFuelValue = requiredFuelValue
        }
    }

    static List<Entry> entries = new ArrayList<>()
    static List<OreInfo> fuels = new ArrayList<>()

    static void add(II_Material oreMaterial, II_Material ingotMaterial, int requiredFuelValue) {
        entries.add(new Entry(oreMaterial, ingotMaterial, requiredFuelValue))
    }

    static {
        fuels.add(get(gem, coal))
        fuels.add(get(block, coal))

        add(cassiterite, tin, 1600)
    }


    @Override
    void run() {
        entries.forEach({ e ->
            fuels.stream().forEach({ fuel ->
                int outputAmount = e.oreMaterial.chemicalInfo.countElement(e.ingotMaterial.chemicalInfo.getAsElement())

                int fuelNeeded = outputAmount * e.requiredFuelValue
                int fuelValue = FuelRegistry.getFuelValue(fuel)
                double fuelAmountD = ((double)fuelNeeded) / fuelValue
                int fuelAmount = (int) Math.ceil(fuelAmountD)
                int recipeAmount = (int) (1 / fuelAmountD)
                RecipeBuilder<BasicMachineRecipe> builder = basicBuilder()
                        .addInput(dust, e.oreMaterial, e.oreMaterial.chemicalInfo.atoms)
                        .addOutput(ingot, e.ingotMaterial, outputAmount)
                        .addEnergyValues(0, 0, fuelNeeded.intdiv(10))
                if (recipeAmount > 1) {
                    builder.multiply(recipeAmount)
                    outputAmount *= recipeAmount;
                }
                builder
                        .addInput(fuel, fuelAmount)
                        .multiply(Math.max(1, 60 / outputAmount as int))
                        .addTo(map)


            })
        })
    }
}
