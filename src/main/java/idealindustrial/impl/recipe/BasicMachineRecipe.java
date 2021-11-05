package idealindustrial.impl.recipe;

import idealindustrial.api.recipe.IMachineRecipe;
import idealindustrial.api.tile.fluid.FluidHandler;
import idealindustrial.api.tile.inventory.RecipedInventory;
import idealindustrial.impl.item.stack.HashedStack;
import idealindustrial.impl.item.stack.II_ItemStack;
import idealindustrial.impl.item.stack.II_StackSignature;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public class BasicMachineRecipe implements IMachineRecipe {

    protected II_StackSignature[] inputs;
    protected II_ItemStack[] outputs;
    protected FluidStack[] fluidInputs, fluidOutputs;
    protected RecipeEnergyParams params;


    public BasicMachineRecipe(II_StackSignature[] inputs, II_ItemStack[] outputs, FluidStack[] fluidInputs, FluidStack[] fluidOutputs, RecipeEnergyParams params) {
        this.inputs = inputs;
        this.outputs = outputs;
        this.fluidInputs = fluidInputs;
        this.fluidOutputs = fluidOutputs;
        this.params = params;
    }

    @Override
    public List<HashedStack> getAllPossibleInputs() {
        List<HashedStack> out = new ArrayList<>();
        for (II_StackSignature stack : inputs) {
            out.addAll(stack.correspondingStacks());
        }
        return out;
    }

    @Override
    public II_StackSignature[] getInputs() {
        return inputs;
    }

    @Override
    public II_ItemStack[] getOutputs() {
        return outputs;
    }

    @Override
    public FluidStack[] getFluidInputs() {
        return fluidInputs;
    }

    @Override
    public FluidStack[] getFluidOutputs() {
        return fluidOutputs;
    }

    @Override
    public RecipeEnergyParams recipeParams() {
        return params;
    }

    @Override
    public boolean isInputEqualStacks(RecipedInventory inventory, FluidHandler fluidInputs, boolean doConsume) {
        for (II_StackSignature signature : inputs) {
            if (inventory.hasMatch(signature)) {
                if (doConsume) {
                    inventory.extract(signature);
                }
            } else {
                return false;
            }
        }
        for (FluidStack fluid : fluidInputs) {
            FluidStack st = fluidInputs.drain(ForgeDirection.UNKNOWN, fluid, false);
            if (st == null || st.amount < fluid.amount) {
                return false;
            }
            if (doConsume) {
                fluidInputs.drain(ForgeDirection.UNKNOWN, fluid, true);
            }
        }
        return true;
    }

    @Override
    public void optimize() {
        for (II_StackSignature input : inputs) {
            input.optimize();
        }
    }
}
