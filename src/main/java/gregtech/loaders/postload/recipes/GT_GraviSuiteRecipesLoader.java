package gregtech.loaders.postload.recipes;

import gregtech.GT_Mod;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.ToolDictNames;

public class GT_GraviSuiteRecipesLoader implements Runnable {

    @Override
    public void run() {

        if (GT_Mod.gregtechproxy.mDisableIC2Cables) {
            //GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getModItem("GraviSuite", "advJetpack", 1));
            //GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GraviSuite", "advJetpack", 1), new Object[]{"PJP", "BLB", "WCW", 'P', OrePrefixes.plateAlloy.get(Materials.Carbon), 'J', GT_ModHandler.getIC2Item("electricJetpack", 1), 'B', GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 1, 6), 'L', GT_ModHandler.getModItem("GraviSuite", "advLappack", 1), 'W', OrePrefixes.wireGt04.get(Materials.Platinum), 'C', OrePrefixes.circuit.get(Materials.Advanced)});

            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 3, 1));
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 3, 1), new Object[]{"CCC", "WWW", "CCC", 'C', GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem", 1), 'W', OrePrefixes.wireGt01.get(Materials.Superconductor)});
        }

            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getModItem("GraviSuite", "advNanoChestPlate", 1, GT_Values.W));
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GraviSuite", "advNanoChestPlate", 1, GT_Values.W), 
			new Object[]{"CJC", "TNT", "WPW", 'C', OrePrefixes.plateAlloy.get(Materials.Carbon), 
			'T', OrePrefixes.plate.get(Materials.TungstenSteel), 
			'J', GT_ModHandler.getModItem("GraviSuite", "advJetpack", 1, GT_Values.W), 
			'N', GT_ModHandler.getModItem("IC2","itemArmorNanoChestplate", 1, GT_Values.W), 
			'W', OrePrefixes.wireGt12.get(Materials.Platinum), 
			'P', OrePrefixes.circuit.get(Materials.Elite)});
            
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getModItem("GraviSuite", "advLappack", 1, GT_Values.W));
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GraviSuite", "advLappack", 1, GT_Values.W), 
			new Object[]{"CEC", "EJE", "WPW", 'C', OrePrefixes.plateAlloy.get(Materials.Carbon), 
			'J', GT_ModHandler.getModItem("IC2","itemArmorEnergypack", 1L, GT_Values.W), 
			'E', GT_ModHandler.getModItem("IC2","itemBatCrystal", 1L, GT_Values.W),  
			'W', OrePrefixes.wireGt04.get(Materials.Platinum), 
			'P', OrePrefixes.circuit.get(Materials.Data)});
            
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getModItem("GraviSuite", "advJetpack", 1, GT_Values.W));
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GraviSuite", "advJetpack", 1, GT_Values.W), 
			new Object[]{"CJC", "EXE", "YZY", 'C', OrePrefixes.plateAlloy.get(Materials.Carbon), 
			'J',  GT_ModHandler.getModItem("IC2", "itemArmorJetpackElectric", 1, GT_Values.W), 
			'E', OrePrefixes.plate.get(Materials.Titanium), 
			'X', GT_ModHandler.getModItem("IC2", "itemArmorAlloyChestplate", 1L), 
			'Z', OrePrefixes.circuit.get(Materials.Data),  
			'Y', OrePrefixes.wireGt02.get(Materials.Platinum)});
			
            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getModItem("GraviSuite", "vajra", 1, GT_Values.W));
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GraviSuite", "vajra", 1, GT_Values.W), 
			new Object[]{"PBP", "KVK", "PCP", 
			'K', OrePrefixes.plateAlloy.get(Materials.Carbon), 
			'P', OrePrefixes.plate.get(Materials.Neutronium), 
			'V', GT_ModHandler.getModItem("GraviSuite", "itemSimpleItem",1, 5), 
			'B', OrePrefixes.battery.get(Materials.Master),
			'C', OrePrefixes.circuit.get(Materials.Master)});

            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getModItem("GraviSuite", "advDDrill", 1, GT_Values.W));
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GraviSuite", "advDDrill", 1, GT_Values.W), 
			new Object[]{"SDT", "GMG", "PBP", 
			'D', OrePrefixes.toolHeadDrill.get(Materials.HSSG),
			'P', OrePrefixes.plate.get(Materials.HSSG), 
			'G', OrePrefixes.gearGtSmall.get(Materials.HSSG),
			'S', OrePrefixes.screw.get(Materials.HSSG),
			'B', GT_ModHandler.getModItem("IC2","itemBatChargeLamaCrystal", 1L, GT_Values.W),
			'M', ItemList.Electric_Motor_EV.get(1L, new Object[0]),
			'T', ToolDictNames.craftingToolScrewdriver});

            GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getModItem("GraviSuite", "advChainsaw", 1, GT_Values.W));
            GT_ModHandler.addCraftingRecipe(GT_ModHandler.getModItem("GraviSuite", "advChainsaw", 1, GT_Values.W), 
			new Object[]{"SDT", "GMG", "PBP", 
			'D', OrePrefixes.toolHeadChainsaw.get(Materials.HSSG),
			'P', OrePrefixes.plate.get(Materials.HSSG), 
			'G', OrePrefixes.gearGtSmall.get(Materials.HSSG),
			'S', OrePrefixes.screw.get(Materials.HSSG),
			'B', GT_ModHandler.getModItem("IC2","itemBatChargeLamaCrystal", 1L, GT_Values.W),
			'M', ItemList.Electric_Motor_EV.get(1L, new Object[0]),
			'T', ToolDictNames.craftingToolScrewdriver});
    }
}
