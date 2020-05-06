package gregtech.loaders.oreprocessing;

import gregtech.api.enums.Dyes;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.interfaces.IOreRecipeRegistrator;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;

import java.util.Locale;

public class ProcessingDye implements IOreRecipeRegistrator {
    public ProcessingDye() {
        OrePrefixes.dye.add(this);
    }

    public void registerOre(OrePrefixes aPrefix, Materials aMaterial, String aOreDictName, String aModName, ItemStack aStack) {
        Dyes aDye = Dyes.get(aOreDictName);
        if ((aDye.mIndex >= 0) && (aDye.mIndex < 16) &&
                (GT_Utility.getContainerItem(aStack, true) == null)) {
            GT_ModHandler.addAlloySmelterRecipe(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.Glass, 8L), GT_Utility.copyAmount(1L, new Object[]{aStack}), new ItemStack(Blocks.stained_glass, 8, 15 - aDye.mIndex), 200, 8, false);
            GT_ModHandler.addAlloySmelterRecipe(new ItemStack(Blocks.glass, 8, 32767), GT_Utility.copyAmount(1L, new Object[]{aStack}), new ItemStack(Blocks.stained_glass, 8, 15 - aDye.mIndex), 200, 8, false);
            GT_Values.RA.addMixerRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_Utility.getIntegratedCircuit(4), null, null, Materials.Water.getFluid(216L), FluidRegistry.getFluidStack("dye.watermixed." + aDye.name().toLowerCase(Locale.ENGLISH), 192), null, 16, 4);
            GT_Values.RA.addMixerRecipe(GT_Utility.copyAmount(1L, new Object[]{aStack}), GT_Utility.getIntegratedCircuit(4), null, null, GT_ModHandler.getDistilledWater(288L), FluidRegistry.getFluidStack("dye.watermixed." + aDye.name().toLowerCase(Locale.ENGLISH), 216), null, 16, 4);
            GT_Values.RA.addChemicalRecipe(GT_Utility.copyAmount(4L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.cell, Materials.PolyvinylAcetate, 1), GT_ModHandler.getDistilledWater(1000L), FluidRegistry.getFluidStack("dye.chemical." + aDye.name().toLowerCase(Locale.ENGLISH), 1152), GT_OreDictUnificator.get(OrePrefixes.cell, Materials.Empty, 1), 400, 16);
            GT_Values.RA.addChemicalRecipe(GT_Utility.copyAmount(4L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.cell, Materials.PolyvinylAcetate, 1), Materials.Acetone.getFluid(1000L), FluidRegistry.getFluidStack("dye.chemical." + aDye.name().toLowerCase(Locale.ENGLISH), 1728), GT_OreDictUnificator.get(OrePrefixes.cell, Materials.Empty, 1), 400, 16);
            GT_Values.RA.addChemicalRecipe(GT_Utility.copyAmount(4L, new Object[]{aStack}), GT_OreDictUnificator.get(OrePrefixes.cell, Materials.PolyvinylAcetate, 1), Materials.MethylAcetate.getFluid(1000L), FluidRegistry.getFluidStack("dye.chemical." + aDye.name().toLowerCase(Locale.ENGLISH), 1728), GT_OreDictUnificator.get(OrePrefixes.cell, Materials.Empty, 1), 400, 16);
        }
    }
}
