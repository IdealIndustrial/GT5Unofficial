package idealindustrial.recipe;

import idealindustrial.util.fluid.II_FluidHandler;
import idealindustrial.util.fluid.II_MultiFluidHandler;
import idealindustrial.util.inventory.II_ArrayRecipedInventory;
import idealindustrial.util.inventory.II_RecipedInventory;
import idealindustrial.util.item.II_HashedStack;
import idealindustrial.util.item.II_ItemHelper;
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
    protected II_RecipeGuiParams params;
    protected Map<II_HashedStack, Set<R>> outputMap = II_ItemHelper.queryMap(new HashMap<>()), inputMap = II_ItemHelper.queryMap(new HashMap<>());
    protected II_RecipeMapStorage<R> storage;

    public II_BasicRecipeMap(String name, boolean checkConflicts, boolean allowNulls, II_RecipeGuiParams guiParams, Class<R> recipeType) {
        this.name = name;
        this.checkConflicts = checkConflicts;
        this.allowNulls = allowNulls;
        this.params = guiParams;
        II_RecipeMaps.allRecipeMaps.add(this);
        this.storage = new II_RecipeMapStorage<>(name.replace(' ', '.').toLowerCase() + ".json", recipeType);
    }

    @Override
    public R findRecipe(II_RecipedInventory inventory, II_FluidHandler fluidHandler, II_MachineEnergyParams params) {
        for (II_ItemStack stack : inventory) {
            if (map.containsKey(stack.toHashedStack())) {
                for (R recipe : map.get(stack.toHashedStack())) {
                    if (recipe.isInputEqualStacks(inventory, fluidHandler, false) && recipe.recipeParams().areValid(params)) {
                        return recipe;
                    }
                }
            }
        }
        return null;
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
        addToLists(recipe);
        return true;

    }

    protected void addToLists(R recipe) {
        for (II_HashedStack stack : recipe.getAllPossibleInputs()) {
            map.computeIfAbsent(stack, s -> new ArrayList<>()).add(recipe);
        }
        allRecipes.add(recipe);
        for (II_StackSignature signature : recipe.getInputs()) {
            for (II_HashedStack stack : signature.correspondingStacks()) {
                inputMap.computeIfAbsent(stack, s -> new HashSet<>()).add(recipe);
            }
        }
        for (II_ItemStack signature : recipe.getOutputs()) {
            II_HashedStack stack = signature.toHashedStack();
            outputMap.computeIfAbsent(stack, s -> new HashSet<>()).add(recipe);
        }
    }

    @Override
    public List<R> getAllRecipes() {
        return allRecipes;
    }

    @Override
    public void optimize() {
        for (II_Recipe recipe : allRecipes) {
            recipe.optimize();
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public II_RecipeGuiParams getGuiParams() {
        return params;
    }

    @Override
    public Set<R> getCraftingRecipes(II_StackSignature stackSignature) {
        return loadRecipes(stackSignature, outputMap);
    }

    @Override
    public Set<R> getUsageRecipes(II_StackSignature signature) {
        return loadRecipes(signature, inputMap);
    }

    @Override
    public II_RecipeMapStorage<R> getJsonReflection() {
        return storage;
    }

    protected Set<R> loadRecipes(II_StackSignature signature, Map<II_HashedStack, Set<R>> inputMap) {
        Set<R> out = new HashSet<>();
        for (II_HashedStack stack : signature.correspondingStacks()) {
            Set<R> toAdd = inputMap.get(stack);
            if (toAdd != null) {
                out.addAll(toAdd);
            }
        }
        return out;
    }
}
