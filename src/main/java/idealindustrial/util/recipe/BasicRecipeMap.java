package idealindustrial.util.recipe;

import idealindustrial.util.inventory.II_ArrayRecipedInventory;
import idealindustrial.util.inventory.II_RecipedInventory;
import idealindustrial.util.item.II_HashedStack;
import idealindustrial.util.item.II_ItemStack;
import idealindustrial.util.item.II_StackSignature;
import net.minecraftforge.fluids.FluidStack;

import java.util.*;
import java.util.stream.Stream;

public class BasicRecipeMap implements RecipeMap {

    private static final boolean EXCEPTION_ON_CONFLICTS = true;

    boolean checkConflicts = true, allowNulls = false;
    Map<II_HashedStack, List<Recipe>> map;
    String name;


    @Override
    public Recipe findRecipe(II_RecipedInventory inventory, FluidStack[] fluidInputs, MachineParams params) {
        for (II_ItemStack stack : inventory) {
            for (Recipe recipe : map.get(stack.toHashedStack())) {
                if (isInputEqualStacks(recipe, inventory, fluidInputs, false)) {
                    return recipe;
                }
            }
        }
        return null;
    }

    protected boolean isInputEqualStacks(Recipe recipe, II_RecipedInventory inventory, FluidStack[] fluidInputs, boolean doConsume) {
        if (recipe.getInputs().length != 0) {
            for (II_StackSignature signature : recipe.getInputs()) {
                if (inventory.hasMatch(signature)) {
                    if (doConsume) {
                        inventory.extract(signature);
                    }
                } else {
                    return false;
                }
            }
        }
        if (recipe.getFluidInputs().length == 0) {
            return true;
        }
        o:
        for (FluidStack signature : recipe.getFluidInputs()) {
            int i = 0, needed = signature.amount;
            while (i < fluidInputs.length) {
                if (signature.isFluidEqual(fluidInputs[i])) {
                    int consume = Math.min(needed, fluidInputs[i].amount);
                    if (doConsume) {
                        fluidInputs[i].amount -= consume;
                    }
                    needed -= consume;
                    i++;
                    if (needed <= 0) {
                        continue o;
                    }
                }
            }
            return false;
        }
        return true;
    }


    protected Recipe findRecipe(Recipe recipe) {
        return findRecipe(new II_ArrayRecipedInventory(recipe.getAllPossibleInputs().stream().map(II_HashedStack::toIIStack).toArray(II_ItemStack[]::new)),
                recipe.getFluidInputs(), recipe.recipeParams());
    }

    @Override
    public boolean addRecipe(Recipe recipe) {
        if (checkConflicts) {
            Recipe match = findRecipe(recipe);
            if (match != null) {
                if (EXCEPTION_ON_CONFLICTS) {
                    throw new IllegalStateException("map " + name + " already contains this recipe ( or there is a conflict)");
                }
                return false;
            }
        }
        if (!allowNulls && !Stream.of(recipe.getInputs(), recipe.getOutputs(), recipe.getFluidInputs(), recipe.getFluidOutputs()) //todo may be move lambdas to static fields for optimization
                .allMatch(arr -> Arrays.stream(arr).noneMatch(Objects::isNull))) {
            throw new IllegalStateException("recipe contains null fluid or item");
        }
        for (II_HashedStack stack : recipe.getAllPossibleInputs()) {
            map.computeIfAbsent(stack, s -> new ArrayList<>()).add(recipe);
        }
        return true;

    }


}
