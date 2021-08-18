package idealindustrial.recipe;

import idealindustrial.util.fluid.II_FluidHandler;
import idealindustrial.util.fluid.II_MultiFluidHandler;
import idealindustrial.util.inventory.II_ArrayRecipedInventory;
import idealindustrial.util.inventory.II_RecipedInventory;
import idealindustrial.util.item.II_HashedStack;
import idealindustrial.util.item.II_ItemStack;
import idealindustrial.util.item.II_StackSignature;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

import java.util.*;
import java.util.stream.Stream;

public class II_BasicRecipeMap<R extends II_Recipe> implements II_RecipeMap<R> {

    private static final boolean EXCEPTION_ON_CONFLICTS = true;

    protected String name;
    protected boolean checkConflicts, allowNulls;
    protected Map<II_HashedStack, List<R>> map = new HashMap<>();
    protected List<R> allRecipes = new ArrayList<>();

    public II_BasicRecipeMap(String name, boolean checkConflicts, boolean allowNulls) {
        this.name = name;
        this.checkConflicts = checkConflicts;
        this.allowNulls = allowNulls;
    }

    @Override
    public R findRecipe(II_RecipedInventory inventory, II_FluidHandler fluidHandler, II_MachineEnergyParams params) {
        for (II_ItemStack stack : inventory) {
            if (map.containsKey(stack.toHashedStack())) {
                for (R recipe : map.get(stack.toHashedStack())) {
                    if (isInputEqualStacks(recipe, inventory, fluidHandler, false) && recipe.recipeParams().areValid(params)) {
                        return recipe;
                    }
                }
            }
        }
        return null;
    }

    protected boolean isInputEqualStacks(II_Recipe recipe, II_RecipedInventory inventory, II_FluidHandler fluidInputs, boolean doConsume) {
        for (II_StackSignature signature : recipe.getInputs()) {
            if (inventory.hasMatch(signature)) {
                if (doConsume) {
                    inventory.extract(signature);
                }
            } else {
                return false;
            }
        }
        for (FluidStack fluid : recipe.getFluidInputs()) {
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


    protected R findRecipe(R recipe) {
        return findRecipe(new II_ArrayRecipedInventory(recipe.getAllPossibleInputs().stream().map(II_HashedStack::toIIStack).toArray(II_ItemStack[]::new)),
                new II_MultiFluidHandler(recipe.getFluidInputs()), recipe.recipeParams());
    }

    @Override
    public boolean addRecipe(R recipe) {
        if (checkConflicts) {
            R match = findRecipe(recipe);
            if (match != null) {
                if (EXCEPTION_ON_CONFLICTS) {
                    throw new IllegalStateException("map " + name + " already contains this recipe ( or there is a conflict)");
                }
                return false;
            }
        }
        if (!allowNulls && !Stream.of(recipe.getInputs(), recipe.getOutputs(), recipe.getFluidInputs(), recipe.getFluidOutputs())
                .allMatch(arr -> Arrays.stream(arr).noneMatch(Objects::isNull))) {
            throw new IllegalStateException("recipe contains null fluid or item");
        }
        for (II_HashedStack stack : recipe.getAllPossibleInputs()) {
            map.computeIfAbsent(stack, s -> new ArrayList<>()).add(recipe);
        }
        allRecipes.add(recipe);
        return true;

    }

    @Override
    public List<R> getAllRecipes() {
        return allRecipes;
    }


}
