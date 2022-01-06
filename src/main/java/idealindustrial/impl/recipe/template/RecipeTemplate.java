package idealindustrial.impl.recipe.template;

import idealindustrial.api.recipe.IMachineRecipe;
import idealindustrial.impl.item.stack.II_ItemStack;
import idealindustrial.impl.item.stack.II_StackSignature;
import idealindustrial.impl.oredict.OreInfo;
import idealindustrial.impl.recipe.RecipeBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

//todo: fluid handling
public class RecipeTemplate<R extends IMachineRecipe> {

    DefinedStack[] inputs, outputs;

    public RecipeTemplate(DefinedStack[] inputs, DefinedStack[] outputs) {
        this.inputs = inputs;
        this.outputs = outputs;
    }

    public RecipeTemplate<R> copy() {
        return new RecipeTemplate<>(
                Arrays.stream(inputs).map(DefinedStack::copy).toArray(DefinedStack[]::new),
                Arrays.stream(outputs).map(DefinedStack::copy).toArray(DefinedStack[]::new));
    }

    public void multiply(int amount) {
        Stream.of(inputs, outputs).flatMap(Arrays::stream).forEach(i -> i.multiply(amount));
    }


    public class InstantiationPipe {
        boolean inItemsDone, outItemsDone;
        RecipeTemplate<R> local = RecipeTemplate.this.copy();

        public InstantiationPipe forInputs(OreInfo... entries) {
            assert !inItemsDone;

            int globalID = 0;
            for (OreInfo info : entries) {
                assert info.hasMaterialPrefixDefinition() : "no handling for non material + prefix oreDicts yet";
                DefinedStack stack = local.inputs[globalID];
                while (!stack.shouldBeGenerated()) {
                    globalID++;
                    stack = local.inputs[globalID];
                }
                int mul = stack.generate(info.getMaterial(), info.getPrefix());
                stack.lock();
                local.multiply(mul);
                stack.unlock();
            }

            inItemsDone = true;
            return this;
        }

        public InstantiationPipe forOutputs(OreInfo... entries) {
            assert !outItemsDone;

            int globalID = 0;
            for (OreInfo info : entries) {
                assert info.hasMaterialPrefixDefinition() : "no handling for non material + prefix oreDicts yet";
                DefinedStack stack = local.outputs[globalID];
                while (!stack.shouldBeGenerated()) {
                    globalID++;
                    stack = local.outputs[globalID];
                }
                int mul = stack.generate(info.getMaterial(), info.getPrefix());
                stack.lock();
                local.multiply(mul);
                stack.unlock();
            }

            outItemsDone = true;
            return this;
        }

        @SuppressWarnings("unchecked")
        R init() {
            return (R) RecipeBuilder.basicBuilder()
                    .addInputs(Arrays.stream(inputs).map(DefinedStack::instantiate).toArray(II_StackSignature[]::new))
                    .addOutputs(Arrays.stream(outputs).map(DefinedStack::instantiate).toArray(II_ItemStack[]::new))
                    .construct();
        }
    }


    public static RecipeTemplateBuilder builder() {
        return new RecipeTemplateBuilder();
    }

    public static class RecipeTemplateBuilder {
        List<InstantiableStack> itemsIn = new ArrayList<>(), itemsOut = new ArrayList<>();

        public RecipeTemplateBuilder addInput(DefinedItem item) {
            itemsIn.add(new InstantiableStack(item, 1));
            return this;
        }

        public RecipeTemplateBuilder addInputs(DefinedItem... stacks) {
            Arrays.stream(stacks).forEach(this::addInput);
            return this;
        }

        public RecipeTemplateBuilder addOutput(DefinedItem item, int amount) {
            itemsOut.add(new InstantiableStack(item, amount));
            return this;
        }

        public <E extends IMachineRecipe> RecipeTemplate<E> construct() {
            return new RecipeTemplate<>(itemsIn.toArray(new InstantiableStack[0]), itemsOut.toArray(new InstantiableStack[0]));
        }
    }

}
