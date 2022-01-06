package idealindustrial.impl.recipe;

import idealindustrial.api.recipe.IMachineRecipe;
import idealindustrial.api.recipe.RecipeMap;
import idealindustrial.impl.autogen.material.II_Material;
import idealindustrial.impl.autogen.material.Prefixes;
import idealindustrial.impl.item.stack.II_ItemStack;
import idealindustrial.impl.item.stack.II_StackSignature;
import idealindustrial.impl.oredict.OreDict;
import idealindustrial.impl.oredict.OreInfo;
import idealindustrial.util.misc.Defaultable;
import idealindustrial.util.misc.II_Util;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public abstract class RecipeBuilder<R extends IMachineRecipe> {

    protected List<II_StackSignature> inputs = new ArrayList<>();
    protected List<II_ItemStack> outputs = new ArrayList<>();
    protected List<FluidStack> fluidInputs = new ArrayList<>(), fluidOutputs = new ArrayList<>();
    protected Defaultable<RecipeEnergyParams> machineParams = II_Util.makeDefault(new RecipeEnergyParams(0, 0, 20));

    public abstract R construct();

    public void addTo(RecipeMap<R> map) {
        map.addRecipe(construct());
    }

    public RecipeBuilder<R> addInputs(II_StackSignature... inputs) {
        this.inputs.addAll(Arrays.asList(inputs));
        return this;
    }

    public RecipeBuilder<R> addInputs(Collection<II_StackSignature> inputs) {
        this.inputs.addAll(inputs);
        return this;
    }

    public RecipeBuilder<R> addInput(Prefixes prefix, II_Material material, int amount) {
        inputs.add(OreDict.get(prefix, material, amount));
        return this;
    }

    public RecipeBuilder<R> addInput(OreInfo info, int amount) {
        inputs.add(new II_StackSignature(info, amount));
        return this;
    }

    public RecipeBuilder<R> addOutputs(II_ItemStack... outputs) {
        Arrays.stream(outputs)
                .map(is -> is instanceof II_StackSignature ? ((II_StackSignature) is).getAsStack() : is)
                .forEach(is -> this.outputs.add(is));
        return this;
    }

    public RecipeBuilder<R> addOutput(Prefixes prefix, II_Material material, int amount) {
        return addOutputs(OreDict.get(prefix, material, amount));
    }


    public RecipeBuilder<R> addFluidInputs(FluidStack... fluids) {
        this.fluidInputs.addAll(Arrays.asList(fluids));
        return this;
    }

    public RecipeBuilder<R> addFluidOutputs(FluidStack... fluids) {
        this.fluidOutputs.addAll(Arrays.asList(fluids));
        return this;
    }

    public RecipeBuilder<R> addEnergyValues(long usage, long amperage, long duration) {
        this.machineParams.set(new RecipeEnergyParams(usage, amperage, duration));
        return this;
    }

    public RecipeBuilder<R> multiply(int amount) {
        assert machineParams.isSet();
        Stream.of(inputs, outputs).flatMap(List::stream).forEach(is -> is.amount *= amount);
        Stream.of(fluidInputs, fluidOutputs).flatMap(List::stream).forEach(fs -> fs.amount *= amount);
        RecipeEnergyParams params = machineParams.get();
        machineParams.set(new RecipeEnergyParams(params.voltage, params.amperage, params.duration * amount));
        return this;
    }


    public static final class BasicRecipeBuilder extends RecipeBuilder<BasicMachineRecipe> {

        @Override
        public BasicMachineRecipe construct() {
            return new BasicMachineRecipe(
                    inputs.toArray(new II_StackSignature[0]),
                    outputs.toArray(new II_ItemStack[0]),
                    fluidInputs.toArray(new FluidStack[0]),
                    fluidOutputs.toArray(new FluidStack[0]),
                    machineParams.get()
            );
        }
    }

    public static final class ShapedRecipeBuilder extends RecipeBuilder<ShapedMachineRecipe> {

        @Override
        public ShapedMachineRecipe construct() {
            return new ShapedMachineRecipe(
                    inputs.toArray(new II_StackSignature[0]),
                    outputs.toArray(new II_ItemStack[0]),
                    fluidInputs.toArray(new FluidStack[0]),
                    fluidOutputs.toArray(new FluidStack[0]),
                    machineParams.get()
            );
        }
    }

    public static RecipeBuilder<BasicMachineRecipe> basicBuilder() {
        return new BasicRecipeBuilder();
    }

    public static RecipeBuilder<ShapedMachineRecipe> shapedBuilder() {
        return new ShapedRecipeBuilder();
    }
}
