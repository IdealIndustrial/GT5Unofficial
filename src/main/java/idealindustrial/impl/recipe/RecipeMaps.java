package idealindustrial.impl.recipe;

import idealindustrial.api.recipe.RecipeMap;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipeMaps {
    public static List<RecipeMap<?>> allRecipeMaps = new ArrayList<>();
    public static RecipeMap<BasicMachineRecipe> benderRecipes = new BasicRecipeMap<>("Bender Recipes", true, false,
            new RecipeGuiParamsBuilder(1, 1, 0, 1, 1).construct(), BasicMachineRecipe.class);

    public static void optimize() {
        for (RecipeMap<?> map : allRecipeMaps) {
            map.optimize();
        }
    }

    public static final Map<Integer, RecipeMap<?>> id2map = new HashMap<>();
    public static final Map<String, Integer> name2id = new HashMap<>();

    static {
        Field[] declaredFields = RecipeMaps.class.getDeclaredFields();
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

    public static RecipeMap<?> getMap(String name) {
        try {
            Field field = RecipeMaps.class.getDeclaredField(name);
            if (RecipeMap.class.isAssignableFrom(field.getType())) {
                return (RecipeMap<?>) field.get(null);
            }
        } catch (NoSuchFieldException | IllegalAccessException exception) {
            exception.printStackTrace();
        }
        return null;
    }
}
