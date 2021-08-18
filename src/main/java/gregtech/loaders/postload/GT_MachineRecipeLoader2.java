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
        RA.addChemicalRecipe(get(OrePrefixes.dust, Materials.Scheelite, 6), null, Materials.HydrochloricAcid.getFluid(4000L),
                null, get(OrePrefixes.dust, Materials.CalciumChloride, 3), get(OrePrefixes.dust, Materials.TungstigAcid, 7),
                5 * 20, 1920);
        RA.addChemicalRecipe(get(OrePrefixes.dust, Materials.Tungstate, 7), null, Materials.HydrochloricAcid.getFluid(4000L),
                null, get(OrePrefixes.dust, Materials.LithiumChloride, 4), get(OrePrefixes.dust, Materials.TungstigAcid, 7),
                5 * 20, 1920);
        RA.addChemicalRecipe(get(OrePrefixes.dust, Materials.TungstigAcid, 7), GT_Utility.getIntegratedCircuit(1), null,
                Materials.Water.getFluid(3000L), get(OrePrefixes.dust, Materials.TungstenTrioxide, 4), null,
                10*20, 90);

        RA.addBlastRecipe(get(OrePrefixes.dust, Materials.TungstenTrioxide, 4), get(OrePrefixes.dust, Materials.Iron, 2), null,
                null, get(OrePrefixes.ingotHot, Materials.Tungsten, 1), get(OrePrefixes.dust, Materials.BandedIron, 5),
                600*20, 120, 3000);
        RA.addBlastRecipe(get(OrePrefixes.dust, Materials.TungstenTrioxide, 4), null, Materials.Hydrogen.getGas(6000L),
                GT_ModHandler.getSteam(9000), get(OrePrefixes.ingotHot, Materials.Tungsten, 1),null,
                600*20, 120, 3000);
        RA.addBlastRecipe(get(OrePrefixes.dust, Materials.TungstenTrioxide, 8), get(OrePrefixes.dust, Materials.Carbon, 6), null,
                Materials.CarbonDioxide.getGas(9000), get(OrePrefixes.ingotHot, Materials.Tungsten, 2),null,
                1200*20, 120, 3000);

    }
}
