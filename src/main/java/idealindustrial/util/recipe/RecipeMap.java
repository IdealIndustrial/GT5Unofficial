package idealindustrial.util.recipe;

import idealindustrial.util.inventory.II_InternalInventory;
import idealindustrial.util.inventory.II_RecipedInventory;
import idealindustrial.util.item.II_ItemStack;
import net.minecraftforge.fluids.FluidStack;

public interface RecipeMap {

    Recipe findRecipe(II_RecipedInventory inventory, FluidStack[] fluidInputs, MachineParams params);

    boolean addRecipe(Recipe recipe);

    //todo reinit call on IDs reFuck
}
