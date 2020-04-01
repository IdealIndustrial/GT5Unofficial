package gregtech.loaders.postload.recipes;

import gregtech.GT_Mod;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;

public class GT_GraviSuiteRecipesLoader implements Runnable {

    @Override
    public void run() {

        if (GT_Mod.gregtechproxy.mDisableIC2Cables) {
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getModItem("GraviSuite", "advJetpack", 1));
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GraviSuite", "advJetpack", 1), new Object[]{"PJP", "BLB", "WCW", 'P', OrePrefixes.plateAlloy.get(Materials.Carbon), 'J', GT_ModHandler.getIC2Item("electricJetpack", 1), 'B', GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 1, 6), 'L', GT_ModHandler.getModItem("GraviSuite", "advLappack", 1), 'W', OrePrefixes.wireGt04.get(Materials.Platinum), 'C', OrePrefixes.circuit.get(Materials.Advanced)});

            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 3, 1));
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 3, 1), new Object[]{"CCC", "WWW", "CCC", 'C', GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 1), 'W', OrePrefixes.wireGt01.get(Materials.Superconductor)});
        }

        GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getModItem("GraviSuite", "advNanoChestPlate", 1, GT_Values.W));
        GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GraviSuite", "advNanoChestPlate", 1, GT_Values.W), new Object[]{"CJC", "CNC", "WPW", 'C', OrePrefixes.plateAlloy.get(Materials.Carbon), 'J', GT_ModHandler.getModItem("GraviSuite", "advJetpack", 1, GT_Values.W), 'N', GT_ModHandler.getIC2Item("nanoBodyarmor", 1L), 'W', OrePrefixes.wireGt04.get(Materials.Platinum), 'P', OrePrefixes.circuit.get(Materials.Data)});

    }
}
