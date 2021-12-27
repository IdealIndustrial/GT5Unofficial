package idealindustrial.impl.recipe;

import idealindustrial.api.tile.fluid.FluidHandler;
import idealindustrial.api.tile.inventory.RecipedInventory;
import idealindustrial.impl.item.stack.II_ItemStack;
import idealindustrial.impl.item.stack.II_StackSignature;
import net.minecraftforge.fluids.FluidStack;

public class ShapedMachineRecipe extends BasicMachineRecipe {
    public ShapedMachineRecipe(II_StackSignature[] inputs, II_ItemStack[] outputs, FluidStack[] fluidInputs, FluidStack[] fluidOutputs, RecipeEnergyParams params) {
        super(inputs, outputs, fluidInputs, fluidOutputs, params);
    }

    @Override
    public boolean isInputEqualStacks(RecipedInventory inventory, FluidHandler fluidInputs, boolean doConsume) {
        assert inventory.isIndexed();
        if (inputs.length > inventory.size()) {
            return false;
        }
        for (int i = 0; i < inputs.length; i++) {
            if (inputs[i] == null) {
                if (inventory.getII(i) != null) {
                    return false;
                }
                continue;
            }
            II_ItemStack is = inventory.getII(i);
            if (is == null || !inputs[i].isEqual(is) || inputs[i].amount > is.amount) {
                return false;
            }
            if (doConsume) {
                inventory.reduce(i, inputs[i].amount);
            }
        }
        return true;
    }
}
