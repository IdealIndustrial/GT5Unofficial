package idealindustrial.impl.recipe;

import idealindustrial.api.recipe.IMachineRecipe;
import idealindustrial.api.recipe.IRecipeGuiParams;
import idealindustrial.api.recipe.RecipeMap;
import idealindustrial.api.tile.fluid.FluidHandler;
import idealindustrial.api.tile.inventory.InternalInventory;
import idealindustrial.impl.tile.fluid.MultiFluidHandler;
import idealindustrial.impl.tile.inventory.ArrayRecipedInventory;
import idealindustrial.api.tile.inventory.RecipedInventory;
import idealindustrial.impl.item.stack.HashedStack;
import idealindustrial.impl.tile.inventory.EmptyInventory;
import idealindustrial.util.misc.ItemHelper;
import idealindustrial.impl.item.stack.II_ItemStack;

import java.util.*;
import java.util.stream.Stream;

public class BasicRecipeMap<R extends IMachineRecipe> extends AbstractRecipeMap<R> {

    private static final boolean EXCEPTION_ON_CONFLICTS = true;


    protected boolean checkConflicts, allowNulls;
    protected Map<HashedStack, List<R>> map = ItemHelper.queryMap(new HashMap<>());
    protected List<R> allRecipes = new ArrayList<>();
    protected Class<R> recipeType;

    public BasicRecipeMap(String name, boolean checkConflicts, boolean allowNulls, IRecipeGuiParams guiParams, Class<R> recipeType, boolean isSpecial) {
        super(name, guiParams);
        this.checkConflicts = checkConflicts;
        this.allowNulls = allowNulls;
        this.storage = new RecipeMapStorage<>(
                name.replace(' ', '.').toLowerCase() + ".json",
                Arrays.stream(name.split(" "))
                        .map(String::toLowerCase)
                        .map(s -> s.substring(0, 1).toUpperCase().concat(s.substring(1)))
                        .reduce("", String::concat)
                .concat(".groovy"),
                recipeType, this);
        this.recipeType = recipeType;
        if (!isSpecial) {
            RecipeMaps.allRecipeMaps.add(this);
        }
    }

    public BasicRecipeMap(String name, boolean checkConflicts, boolean allowNulls, IRecipeGuiParams guiParams, Class<R> recipeType) {
        this(name, checkConflicts, allowNulls, guiParams, recipeType, false);
    }


    @Override
    public R findRecipe(RecipedInventory inventory, InternalInventory special, FluidHandler fluidHandler, MachineEnergyParams params) {
        for (II_ItemStack stack : inventory) {
            if (stack != null && map.containsKey(stack.toHashedStack())) {
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
        return findRecipe(new ArrayRecipedInventory(recipe.getAllPossibleInputs().stream().map(HashedStack::toIIStack).toArray(II_ItemStack[]::new)), EmptyInventory.INSTANCE,
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

    @Override
    protected void addToLists(R recipe) {
        for (HashedStack stack : recipe.getAllPossibleInputs()) {
            map.computeIfAbsent(stack, s -> new ArrayList<>()).add(recipe);
        }
        allRecipes.add(recipe);
        super.addToLists(recipe);
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
    public RecipeMap<R> newEmpty() {
        return new BasicRecipeMap<>(name, checkConflicts, allowNulls, params, recipeType);
    }


}
