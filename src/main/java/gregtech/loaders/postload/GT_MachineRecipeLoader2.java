package gregtech.loaders.postload;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import gregtech.api.util.GT_Utility;

import static gregtech.api.enums.GT_Values.RA;
import static gregtech.api.util.GT_OreDictUnificator.get;

//just another recipe class cause first one is too large and laggy
public class GT_MachineRecipeLoader2 implements Runnable{
    @Override
    public void run() {
        addTungstenProcessing();

        RA.addElectrolyzerRecipe(get(OrePrefixes.dust, Materials.AluminiumSulfite, 20), null, null,
               Materials.Oxygen.getGas(9000L), get(OrePrefixes.dust, Materials.Aluminium, 8), get(OrePrefixes.dust, Materials.Sulfur, 3), null, null, null, null,
                null, 20*20, 90);
    }

    private void addTungstenProcessing() {
        RA.addChemicalRecipe(get(OrePrefixes.dust, Materials.Scheelite, 1), null, Materials.HydrochloricAcid.getFluid(2000L),
                null, get(OrePrefixes.dust, Materials.CalciumChloride, 1), get(OrePrefixes.dust, Materials.TungstigAcid, 1),
                5 * 20, 1920);
        RA.addChemicalRecipe(get(OrePrefixes.dust, Materials.Tungstate, 1), null, Materials.HydrochloricAcid.getFluid(2000L),
                null, get(OrePrefixes.dust, Materials.LithiumChloride, 2), get(OrePrefixes.dust, Materials.TungstigAcid, 1),
                5 * 20, 1920);
        RA.addChemicalRecipe(get(OrePrefixes.dust, Materials.TungstigAcid, 1), GT_Utility.getIntegratedCircuit(1), null,
                Materials.Water.getFluid(1000L), get(OrePrefixes.dust, Materials.TungstenTrioxide, 1), null,
                10*20, 90);

        RA.addBlastRecipe(get(OrePrefixes.dust, Materials.TungstenTrioxide, 1), get(OrePrefixes.dust, Materials.Iron, 2), null,
                null, get(OrePrefixes.ingotHot, Materials.Tungsten, 1), get(OrePrefixes.dust, Materials.BandedIron, 1),
                600*20, 120, 3000);
        RA.addBlastRecipe(get(OrePrefixes.dust, Materials.TungstenTrioxide, 1), null, Materials.Hydrogen.getGas(3000L),
                GT_ModHandler.getSteam(3000), get(OrePrefixes.ingotHot, Materials.Tungsten, 1),null,
                600*20, 120, 3000);
        RA.addBlastRecipe(get(OrePrefixes.dust, Materials.TungstenTrioxide, 2), get(OrePrefixes.dust, Materials.Carbon, 3), null,
                Materials.CarbonDioxide.getGas(3000), get(OrePrefixes.ingotHot, Materials.Tungsten, 2),null,
                600*20, 120, 3000);

    }
}
