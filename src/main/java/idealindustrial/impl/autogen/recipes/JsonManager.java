package idealindustrial.impl.autogen.recipes;

import com.google.gson.*;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import idealindustrial.II_Core;
import idealindustrial.II_Values;
import idealindustrial.api.recipe.IMachineRecipe;
import idealindustrial.api.recipe.RecipeMap;
import idealindustrial.impl.autogen.recipes.materialprocessing.AutogenRecipes;
import idealindustrial.impl.recipe.*;
import idealindustrial.api.reflection.Config;
import idealindustrial.impl.reflection.config.ReflectionConfig;
import idealindustrial.util.json.ArrayObject;
import net.minecraftforge.common.config.Configuration;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class JsonManager {

    public static void loadRecipes() {
        initAutogenFromCore();
        loadOtherRecipes();
        RecipeMaps.optimize();

    }

    private static void setAsJson(Class<?> clazz, JsonElement element) {
        Map<String, Field> fields = Arrays.stream(clazz.getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Config.class))
                .filter(f -> Modifier.isStatic(f.getModifiers()))
                .peek(f -> f.setAccessible(true))
                .collect(Collectors.toMap(Field::getName, f -> f));
        JsonObject object = element.getAsJsonObject();
        for (Map.Entry<String, JsonElement> value : object.entrySet()) {
            Field field = fields.get(value.getKey());
            if (field != null) {
                try {
                    field.set(null, value.getValue());
                } catch (IllegalAccessException exception) {
                    exception.printStackTrace();
                    throw new IllegalStateException(exception);
                }
            }
        }
    }

    public static void loadMachineConfigs(FMLPreInitializationEvent aEvent) {
        II_Values.tileStream().forEach(tile -> {
            InputStream stream = II_Core.class.getResourceAsStream("machines/" + tile.getName() + ".json");
            if (stream != null) {
                JsonElement element = new JsonParser().parse(new InputStreamReader(stream));
                if (element != null) {
                    setAsJson(tile.getClass(), element);
                }
            }
        });


        File tFile = new File(new File(aEvent.getModConfigurationDirectory(), "StalinTech"), "Machines.cfg");
        Configuration config = new Configuration(tFile);
        config.load();
        II_Values.tileStream().forEach(tile -> ReflectionConfig.loadAll(tile.getClass(), config));
        config.save();
    }

    private static void loadOtherRecipes() {
        for (RecipeMap<?> recipeMap : RecipeMaps.allRecipeMaps) {
            initRecipeMap(recipeMap);
        }
    }

    private static <T extends IMachineRecipe> void initRecipeMap(RecipeMap<T> map) {
        RecipeMapStorage<T> storage = map.getJsonReflection();
        InputStream stream = BasicMachineRecipe.class.getResourceAsStream(storage.getFileName());
        if (stream != null) {
            try {
                ArrayObject<T> recipes = storage.getGson().fromJson(new InputStreamReader(stream), ArrayObject.getType(storage.getRecipeType()));
                for (T recipe : recipes.contents) {
                    map.addRecipe(recipe);
                }
            } catch (JsonParseException e) {
                e.printStackTrace();
            }
        }
    }


    private static void initAutogenFromCore() {
        InputStream stream = II_Core.class.getResourceAsStream("autogen.json");
        AutogenRecipes.loadAndInit(stream == null ? null : new InputStreamReader(stream));
    }
}

