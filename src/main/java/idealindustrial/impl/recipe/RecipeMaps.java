package idealindustrial.impl.recipe;

import idealindustrial.api.recipe.IMachineRecipe;
import idealindustrial.api.recipe.RecipeMap;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

public class RecipeMaps {
    public static List<RecipeMap<?>> allRecipeMaps = new ArrayList<>();
    @Deprecated //todo: remove recipe map from here, move it to bender
    public static RecipeMap<BasicMachineRecipe> benderRecipes = new BasicRecipeMap<>("Bender Recipes", true, false,
            new RecipeGuiParamsBuilder(1, 1, 0, 1, 1).construct(), BasicMachineRecipe.class);

    public static void optimize() {
        for (RecipeMap<?> map : allRecipeMaps) {
            map.optimize();
        }
    }

    public static final Map<Integer, RecipeMap<?>> id2map = new HashMap<>();
    public static final Map<String, Integer> name2id = new HashMap<>();
    private static int freeID = 1;

    static {
        Field[] declaredFields = RecipeMaps.class.getDeclaredFields();//todo: replace with loop over allRecipes
        for (Field field : declaredFields) {
            if (RecipeMap.class.isAssignableFrom(field.getType())) {
                try {
                    RecipeMap<?> map = (RecipeMap<?>) field.get(null);
                    id2map.put(id2map.size() + 1, map);
                    name2id.put(field.getName(), id2map.size());
                } catch (IllegalAccessException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static  <R extends IMachineRecipe> RecipeMap<R> getMap(String name) {
        name = name.toLowerCase().replace(' ', '_').trim();
        return (RecipeMap<R>) id2map.get(name2id.get(name));
    }

    @SuppressWarnings("unchecked")
    public static <R extends IMachineRecipe> RecipeMap<R> getMap(String name, Function<String, RecipeMap<R>> supplier) {
        String engName = name;
        name = name.toLowerCase().replace(' ', '_').trim();
        if (name2id.containsKey(name)) {
            return (RecipeMap<R>) id2map.get(name2id.get(name));
        }
        RecipeMap<R> map = supplier.apply(engName);
        name2id.put(name, freeID);
        id2map.put(freeID++, map);
        return map;
    }
}
