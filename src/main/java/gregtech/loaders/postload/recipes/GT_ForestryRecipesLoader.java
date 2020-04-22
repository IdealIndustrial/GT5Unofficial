package gregtech.loaders.postload.recipes;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

public class GT_ForestryRecipesLoader implements Runnable {

	private final static String aTextForestry = "Forestry";

    @Override
    public void run() {
	    
	GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getModItem(aTextForestry, "ffarm", 1L, 0));
	GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getModItem(aTextForestry, "ffarm", 1L, 2));
	GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getModItem(aTextForestry, "ffarm", 1L, 3));
	GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getModItem(aTextForestry, "ffarm", 1L, 4));
	GT_ModHandler.removeRecipeByOutput(GT_ModHandler.getModItem(aTextForestry, "ffarm", 1L, 5));	    
	    
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{
			ItemList.IC2_Item_Casing_Copper.get(4L), 
			GT_OreDictUnificator.get(OrePrefixes.screw,Materials.Steel, 4L), 
			GT_ModHandler.getModItem(aTextForestry, "thermionicTubes", 1L, 1),
			GT_ModHandler.getModItem("minecraft", "stonebrick", 1L, 0), 
			GT_Utility.getIntegratedCircuit(5)},
			Materials.Redstone.getMolten(144),
			GT_ModHandler.getModItem(aTextForestry, "ffarm", 1L, 0), 600, 30);

        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{
			ItemList.Electric_Motor_LV.get(1L),
			GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Steel, 4),
			GT_ModHandler.getModItem(aTextForestry, "ffarm", 1L, 0),
			GT_Utility.getIntegratedCircuit(5)},
			Materials.Redstone.getMolten(144),
			GT_ModHandler.getModItem(aTextForestry, "ffarm", 1L, 2), 600, 30);
			
        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{
			ItemList.Conveyor_Module_LV.get(2L),
			ItemList.Electric_Motor_LV.get(1L),
			GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Steel, 1),
			GT_ModHandler.getModItem("minecraft", "hopper", 1L, 0),
			GT_ModHandler.getModItem(aTextForestry, "ffarm", 1L, 0),
			GT_Utility.getIntegratedCircuit(5)}, 
			Materials.Redstone.getMolten(144),
			GT_ModHandler.getModItem(aTextForestry, "ffarm", 1L, 3), 600, 30); 

        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{
			ItemList.Electric_Pump_LV.get(2L),
			ItemList.Electric_Motor_LV.get(1L),
			GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Steel, 1),
			GT_OreDictUnificator.get(OrePrefixes.ring,Materials.Rubber, 1L),
			GT_ModHandler.getModItem(aTextForestry, "ffarm", 1L, 0),
			GT_Utility.getIntegratedCircuit(5)}, 
			Materials.Redstone.getMolten(144),
			GT_ModHandler.getModItem(aTextForestry, "ffarm", 1L, 4), 600, 30);

        GT_Values.RA.addAssemblerRecipe(new ItemStack[]{
			ItemList.Electric_Motor_LV.get(1L),
			GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Steel, 1), 
			GT_OreDictUnificator.get(OrePrefixes.cableGt01,Materials.Tin, 1L),
			GT_OreDictUnificator.get(OrePrefixes.circuit,Materials.Basic, 2),
			GT_ModHandler.getModItem(aTextForestry, "ffarm", 1L, 0),
			GT_Utility.getIntegratedCircuit(5)}, 
			Materials.Redstone.getMolten(144),
			GT_ModHandler.getModItem(aTextForestry, "ffarm", 1L, 5), 600, 30);
		
    }
}
