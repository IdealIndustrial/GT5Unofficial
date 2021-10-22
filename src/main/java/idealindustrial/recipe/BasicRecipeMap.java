package idealindustrial.recipe;

import idealindustrial.util.fluid.FluidHandler;
import idealindustrial.util.fluid.MultiFluidHandler;
import idealindustrial.util.inventory.ArrayRecipedInventory;
import idealindustrial.util.inventory.interfaces.RecipedInventory;
import idealindustrial.util.item.HashedStack;
import idealindustrial.util.item.ItemHelper;
import idealindustrial.util.item.II_ItemStack;
import idealindustrial.util.item.II_StackSignature;

import java.util.*;
import java.util.stream.Stream;

public class BasicRecipeMap<R extends IMachineRecipe> implements RecipeMap<R> {

    private static final boolean EXCEPTION_ON_CONFLICTS = true;

    protected String name;
    protected boolean checkConflicts, allowNulls;
    protected Map<HashedStack, List<R>> map = ItemHelper.queryMap(new HashMap<>());
    protected List<R> allRecipes = new ArrayList<>();
    protected IRecipeGuiParams params;
    protected Map<HashedStack, Set<R>> outputMap = ItemHelper.queryMap(new HashMap<>()), inputMap = ItemHelper.queryMap(new HashMap<>());
    protected RecipeMapStorage<R> storage;
    protected Class<R> recipeType;

    public BasicRecipeMap(String name, boolean checkConflicts, boolean allowNulls, IRecipeGuiParams guiParams, Class<R> recipeType) {
        this.name = name;
        this.checkConflicts = checkConflicts;
        this.allowNulls = allowNulls;
        this.params = guiParams;
        RecipeMaps.allRecipeMaps.add(this);
        this.storage = new RecipeMapStorage<>(name.replace(' ', '.').toLowerCase() + ".json", recipeType, this);
        this.recipeType = recipeType;
    }

    @Override
    public R findRecipe(RecipedInventory inventory, FluidHandler fluidHandler, MachineEnergyParams params) {
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
        return findRecipe(new ArrayRecipedInventory(recipe.getAllPossibleInputs().stream().map(HashedStack::toIIStack).toArray(II_ItemStack[]::new)),
                new MultiFluidHandler(recipe.getFluidInputs()), recipe.recipeParams());
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
        for (HashedStack stack : recipe.getAllPossibleInputs()) {
            map.computeIfAbsent(stack, s -> new ArrayList<>()).add(recipe);
        }
        allRecipes.add(recipe);
        for (II_StackSignature signature : recipe.getInputs()) {
            for (HashedStack stack : signature.correspondingStacks()) {
                inputMap.computeIfAbsent(stack, s -> new HashSet<>()).add(recipe);
            }
        }
        for (II_ItemStack signature : recipe.getOutputs()) {
            HashedStack stack = signature.toHashedStack();
            outputMap.computeIfAbsent(stack, s -> new HashSet<>()).add(recipe);
        }
    }

    @Override
    public List<R> getAllRecipes() {
        return allRecipes;
    }

    @Override
    public void optimize() {
        for (IMachineRecipe recipe : allRecipes) {
            recipe.optimize();
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public IRecipeGuiParams getGuiParams() {
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
    public RecipeMapStorage<R> getJsonReflection() {
        return storage;
    }

    @Override
    public RecipeMap<R> newEmpty() {
        return new BasicRecipeMap<R>(name, checkConflicts, allowNulls, params, recipeType);
    }

    protected Set<R> loadRecipes(II_StackSignature signature, Map<HashedStack, Set<R>> inputMap) {
        Set<R> out = new HashSet<>();
        for (HashedStack stack : signature.correspondingStacks()) {
            Set<R> toAdd = inputMap.get(stack);
            if (toAdd != null) {
                out.addAll(toAdd);
            }
        }
        return out;
    }
}
