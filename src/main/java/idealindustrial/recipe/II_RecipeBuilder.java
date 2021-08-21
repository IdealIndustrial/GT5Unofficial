package idealindustrial.recipe;

import idealindustrial.util.item.II_ItemStack;
import idealindustrial.util.item.II_StackSignature;
import idealindustrial.util.misc.Defaultable;
import idealindustrial.util.misc.II_Util;
import net.minecraftforge.fluids.FluidStack;

import java.util.Arrays;

public abstract class II_RecipeBuilder<R extends II_Recipe> {

    protected Defaultable<II_StackSignature[]> inputs = II_Util.makeDefault(new II_StackSignature[0]);
    protected Defaultable<II_ItemStack[]> outputs = II_Util.makeDefault(new II_ItemStack[0]);
    protected Defaultable<FluidStack[]> fluidInputs = II_Util.makeDefault(new FluidStack[0]), fluidOutputs = II_Util.makeDefault(new FluidStack[0]);
    protected Defaultable<II_RecipeEnergyParams> machineParams = II_Util.makeDefault(new II_RecipeEnergyParams( 0, 0, 20));

    public abstract R construct();

    public II_RecipeBuilder<R> addInputs(II_StackSignature... inputs) {
        this.inputs.set(inputs);
        return this;
    }

    public II_RecipeBuilder<R> addOutputs(II_ItemStack... outputs) {
        this.outputs.set(Arrays.stream(outputs)
                .map(is -> is instanceof II_StackSignature ? ((II_StackSignature) is).getAsStack() : is)
                .toArray(II_ItemStack[]::new)
        );
        return this;
    }

    public II_RecipeBuilder<R> addFluidInputs(FluidStack... fluids) {
        this.fluidInputs.set(fluids);
        return this;
    }

    public II_RecipeBuilder<R> addFluidOutputs(FluidStack... fluids) {
        this.fluidOutputs.set(fluids);
        return this;
    }

    public II_RecipeBuilder<R> addEnergyValues(long usage, long amperage, long duration) {
        this.machineParams.set(new II_RecipeEnergyParams(usage, amperage, duration));
        return this;
    }


    public static final class BasicRecipeBuilder extends II_RecipeBuilder<II_BasicRecipe> {

        @Override
        public II_BasicRecipe construct() {
            return new II_BasicRecipe(inputs.get(), outputs.get(), fluidInputs.get(), fluidOutputs.get(), machineParams.get());
        }
    }

    public static II_RecipeBuilder<II_BasicRecipe> basicBuilder() {
        return new BasicRecipeBuilder();
    }
}
