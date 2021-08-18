package idealindustrial.recipe;

import idealindustrial.util.item.II_HashedStack;
import idealindustrial.util.item.II_ItemStack;
import idealindustrial.util.item.II_StackSignature;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public class II_BasicRecipe implements II_Recipe {

    protected II_StackSignature[] inputs;
    protected II_ItemStack[] outputs;
    protected FluidStack[] fluidInputs, fluidOutputs;
    protected II_MachineEnergyParams params;


    public II_BasicRecipe(II_StackSignature[] inputs, II_ItemStack[] outputs, FluidStack[] fluidInputs, FluidStack[] fluidOutputs, II_MachineEnergyParams params) {
        this.inputs = inputs;
        this.outputs = outputs;
        this.fluidInputs = fluidInputs;
        this.fluidOutputs = fluidOutputs;
        this.params = params;
    }

    @Override
    public List<II_HashedStack> getAllPossibleInputs() {
        List<II_HashedStack> out = new ArrayList<>();
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
    public II_MachineEnergyParams recipeParams() {
        return params;
    }
}
