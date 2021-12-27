package idealindustrial.impl.recipe;

import idealindustrial.api.recipe.IMachineRecipe;
import idealindustrial.api.recipe.IRecipeGuiParams;
import idealindustrial.api.recipe.RecipeMap;
import idealindustrial.api.tile.fluid.FluidHandler;
import idealindustrial.api.tile.inventory.InternalInventory;
import idealindustrial.api.tile.inventory.RecipedInventory;
import idealindustrial.impl.item.stack.CheckType;
import idealindustrial.impl.item.stack.II_ItemStack;
import idealindustrial.impl.item.stack.II_StackSignature;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class CustomRecipeMap<R extends IMachineRecipe> extends AbstractRecipeMap<R> {

    public CustomRecipeMap(String name, IRecipeGuiParams params) {
        super(name, params);
    }

    @Override
    public boolean addRecipe(R recipe) {
        addToLists(recipe);
        return true;
    }

    @Override
    public List<R> getAllRecipes() {
        return new ArrayList<>();
    }

    @Override
    public RecipeMap<R> newEmpty() {
        return null;
    }

    public interface RecipeFunction<R extends IMachineRecipe> {
        R get(RecipedInventory inventory, InternalInventory special, FluidHandler fluidInputs, MachineEnergyParams params);
    }

    public interface RequirementFunction {
        boolean get(RecipedInventory inventory, InternalInventory special, FluidHandler fluidInputs, MachineEnergyParams params);
    }

    public static class CustomFunctionRecipeMap<R extends IMachineRecipe> extends CustomRecipeMap<R> {

        RecipeFunction<R> function;

        protected CustomFunctionRecipeMap(String name, IRecipeGuiParams params, RecipeFunction<R> function) {
            super(name, params);
            this.function = function;
        }

        @Override
        public R findRecipe(RecipedInventory inventory, InternalInventory special, FluidHandler fluidInputs, MachineEnergyParams params) {
            return function.get(inventory, special, fluidInputs, params);
        }
    }

    public static <T extends IMachineRecipe> CustomRecipeMap<T> forFunction(String name, IRecipeGuiParams guiParams, RecipeFunction<T> function) {
        return new CustomFunctionRecipeMap<>(name, guiParams, function);
    }


    public static class CustomMultipleFuctionRecipeMap<R extends IMachineRecipe> extends CustomRecipeMap<R> {
        List<RecipeFunction<R>> list = new ArrayList<>();

        protected CustomMultipleFuctionRecipeMap(String name, IRecipeGuiParams params) {
            super(name, params);
        }

        @Override
        public R findRecipe(RecipedInventory inventory, InternalInventory special, FluidHandler fluidInputs, MachineEnergyParams params) {
            R recipe = null;
            for (RecipeFunction<R> function : list) {
                recipe = function.get(inventory, special, fluidInputs, params);
                if (recipe != null) {
                    break;
                }
            }
            return recipe;
        }

        public CustomMultipleFuctionRecipeMap<R> add(RecipeFunction<R> function) {
            list.add(function);
            return this;
        }
    }

    public static <T extends IMachineRecipe> CustomMultipleFuctionRecipeMap<T> forMultiFunction(String name, IRecipeGuiParams params) {
        return new CustomMultipleFuctionRecipeMap<>(name, params);
    }

    public static <R extends IMachineRecipe> RecipeFunction<BasicMachineRecipe> forStackFunction(Function<II_ItemStack, II_ItemStack> func, RequirementFunction requirement, RecipeEnergyParams params) {
        return (inventory, special, fluidInputs, params1) -> {
            if (!requirement.get(inventory, special, fluidInputs, params1)) {
                return null;
            }
            for (II_ItemStack is : inventory) {
                if (is == null) {
                    continue;
                }
                II_ItemStack got = func.apply(is);
                if (got == null) {
                    continue;
                }
                return new BasicMachineRecipe(new II_StackSignature[]{
                        new II_StackSignature(is, CheckType.DIRECT)},
                        new II_ItemStack[]{got},new FluidStack[0], new FluidStack[0], params);
            }
            return null;
        };
    }
}
