package gregtech.loaders.postload.recipes;

import gregtech.api.enums.Materials;
import gregtech.api.util.GT_ModHandler;
import net.minecraftforge.fluids.FluidRegistry;

import static gregtech.api.enums.GT_Values.RA;
//todo properly name
public class GT_JuicesRecipeLoader implements Runnable{
    @Override
    public void run() {
        // Максимальная вместительность бочки 5000, выход должен быть кратен 250
        RA.addFermentingBarrelRecipe(FluidRegistry.getFluidStack("milk", 50),                       FluidRegistry.getFluidStack("potion.mundane", 2500),        24000);
        RA.addFermentingBarrelRecipe(FluidRegistry.getFluidStack("potion.lemonjuice", 5000),        FluidRegistry.getFluidStack("potion.limoncello", 2500),     24000);
        RA.addFermentingBarrelRecipe(FluidRegistry.getFluidStack("potion.applejuice", 5000),        FluidRegistry.getFluidStack("potion.cider", 2500),          24000);
        RA.addFermentingBarrelRecipe(FluidRegistry.getFluidStack("potion.goldenapplejuice", 5000),  FluidRegistry.getFluidStack("potion.goldencider", 2500),    24000);
        RA.addFermentingBarrelRecipe(FluidRegistry.getFluidStack("potion.idunsapplejuice", 5000),   FluidRegistry.getFluidStack("potion.notchesbrew", 2500),    24000);
        RA.addFermentingBarrelRecipe(FluidRegistry.getFluidStack("potion.reedwater", 5000),         FluidRegistry.getFluidStack("potion.rum", 2500),            24000);
        RA.addFermentingBarrelRecipe(FluidRegistry.getFluidStack("potion.rum", 5000),               FluidRegistry.getFluidStack("potion.piratebrew", 2500),     24000);
        RA.addFermentingBarrelRecipe(FluidRegistry.getFluidStack("potion.grapejuice", 5000),        FluidRegistry.getFluidStack("potion.wine", 2500),           24000);
        RA.addFermentingBarrelRecipe(FluidRegistry.getFluidStack("potion.wheatyjuice", 5000),       FluidRegistry.getFluidStack("potion.scotch", 2500),         24000);
        RA.addFermentingBarrelRecipe(FluidRegistry.getFluidStack("potion.scotch", 5000),            FluidRegistry.getFluidStack("potion.glenmckenner", 2500),   24000);
        RA.addFermentingBarrelRecipe(FluidRegistry.getFluidStack("potion.wheatyhopsjuice", 5000),   FluidRegistry.getFluidStack("potion.beer", 2500),           24000);
        RA.addFermentingBarrelRecipe(FluidRegistry.getFluidStack("potion.hopsjuice", 5000),         FluidRegistry.getFluidStack("potion.darkbeer", 2500),       24000);
        RA.addFermentingBarrelRecipe(FluidRegistry.getFluidStack("potion.darkbeer", 5000),          FluidRegistry.getFluidStack("potion.dragonblood", 2500),    24000);
    }
}
