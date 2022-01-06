package idealindustrial.impl.recipe;

import idealindustrial.api.recipe.IMachineRecipe;
import idealindustrial.api.recipe.IRecipeGuiParams;
import idealindustrial.api.recipe.RecipeMap;
import idealindustrial.api.tile.fluid.FluidHandler;
import idealindustrial.api.tile.inventory.RecipedInventory;
import idealindustrial.api.tile.module.RecipeModule;
import idealindustrial.impl.item.stack.CheckType;
import idealindustrial.impl.item.stack.HashedStack;
import idealindustrial.impl.item.stack.II_ItemStack;
import idealindustrial.impl.item.stack.II_StackSignature;
import idealindustrial.util.misc.ItemHelper;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import static idealindustrial.impl.recipe.RecipeGuiParamsBuilder.SlotType.ItemsSpecial;

public abstract class AbstractRecipeMap<R extends IMachineRecipe> implements RecipeMap<R> {

    protected String name;
    protected Map<HashedStack, Set<R>> outputMap = ItemHelper.queryMap(new HashMap<>()), inputMap = ItemHelper.queryMap(new HashMap<>());
    protected IRecipeGuiParams params;
    protected RecipeMapStorage<R> storage;

    public AbstractRecipeMap(String name, IRecipeGuiParams params) {
        this.name = name;
        this.params = params;
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
        return loadRecipes(stackSignature, outputMap, IMachineRecipe::containsOutput);
    }

    @Override
    public Set<R> getUsageRecipes(II_StackSignature signature) {
        return loadRecipes(signature, inputMap, IMachineRecipe::containsInput);
    }

    @Override
    public RecipeMapStorage<R> getJsonReflection() {
        return storage;
    }

    protected Set<R> loadRecipes(II_StackSignature signature, Map<HashedStack, Set<R>> inputMap, BiPredicate<R, II_StackSignature> contains) {
        Set<R> out = new HashSet<>();
        for (HashedStack stack : signature.correspondingStacks()) {
            Set<R> toAdd = inputMap.get(stack);
            if (toAdd != null) {
                if (signature.getType().equals(CheckType.DIRECT)) {
                    toAdd.stream().filter(r -> contains.test(r, signature)).forEach(out::add);
                }
                else {
                    out.addAll(toAdd);
                }
            }
        }
        return out;
    }

    protected void addToLists(R recipe) {
        for (II_StackSignature signature : recipe.getInputs()) {
            if (signature == null) {
                continue;
            }
            for (HashedStack stack : signature.correspondingStacks()) {
                inputMap.computeIfAbsent(stack, s -> new HashSet<>()).add(recipe);
            }
        }
        for (II_ItemStack signature : recipe.getOutputs()) {
            HashedStack stack = signature.toHashedStack();
            outputMap.computeIfAbsent(stack, s -> new HashSet<>()).add(recipe);
        }
    }
}
