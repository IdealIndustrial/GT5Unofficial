package gregtech.loaders.postload.recipes;

import gregtech.api.enums.Materials;
import gregtech.api.util.GT_ModHandler;
import net.minecraftforge.fluids.FluidRegistry;

import static gregtech.api.enums.GT_Values.RA;
//todo properly name
public class GT_JuicesRecipeLoader implements Runnable{
    @Override
    public void run() {
        RA.addFermentingBarrelRecipe(GT_ModHandler.getWater(5000), Materials.Oxygen.getGas(5000), 1000); //interesting =)
        RA.addFermentingBarrelRecipe(GT_ModHandler.getLava(5000), FluidRegistry.getFluidStack("potion.applejuice", 5000), 500);
    }
}
