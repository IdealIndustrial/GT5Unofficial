package idealindustrial.impl.autogen.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import groovy.lang.GroovyClassLoader;
import idealindustrial.II_Core;
import idealindustrial.II_Values;
import idealindustrial.api.recipe.IMachineRecipe;
import idealindustrial.api.recipe.RecipeMap;
import idealindustrial.api.reflection.Config;
import idealindustrial.impl.autogen.material.II_Material;
import idealindustrial.impl.autogen.material.II_Materials;
import idealindustrial.impl.autogen.recipes.generators.ToolRecipeGenerator;
import idealindustrial.impl.autogen.recipes.materialprocessing.AutogenRecipes;
import idealindustrial.impl.recipe.BasicMachineRecipe;
import idealindustrial.impl.recipe.RecipeMapStorage;
import idealindustrial.impl.recipe.RecipeMaps;
import idealindustrial.impl.reflection.config.ReflectionConfig;
import idealindustrial.util.json.ArrayObject;
import net.minecraftforge.common.config.Configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

public class LoadManager {

    static GroovyClassLoader loader = new GroovyClassLoader();

    public static void loadMaterialAutogenInfo() {
        try {
            String csv = Util.readAsTxt(II_Materials.class.getResourceAsStream("MaterialAutogen.csv"));
            String[] lines = csv.split("[\r\n]+");
            String[] header = lines[0].split(",");
            Map<String, Integer> mapHeader = new HashMap<>();
            for (int i = 0; i < header.length; i++) {
                mapHeader.put(header[i].trim(), i);
            }
            for (int i = 1; i < lines.length; i++) {
                List<String> data = splitEscaped(lines[i]);
                String materialName = data.get(mapHeader.get("Material"));
                II_Material material = II_Materials.materialForName(materialName);
                material.getAutogenInfo().parseFrom(mapHeader, data);
            }


        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

    }

    private static List<String> splitEscaped(String str) {
        List<String> out = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        boolean quoted = false;
        for (char ch : str.toCharArray()) {
            if (ch == '"') {
                quoted = !quoted;
                continue;
            }
            if (ch == ',' && !quoted) {
                out.add(builder.toString().trim());
                builder = new StringBuilder();
                continue;
            }
            builder.append(ch);

        }
        out.add(builder.toString());
        return out;
    }

    public static void loadRecipes() {
//        initAutogenFromCore();
        new ToolRecipeGenerator().run();
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
            if (recipeMap.getJsonReflection() == null) {
                continue;
            }
            initRecipeMap(recipeMap);
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static <T extends IMachineRecipe> void initRecipeMap(RecipeMap<T> map) {
        RecipeMapStorage<T> storage = map.getJsonReflection();
        InputStream stream = BasicMachineRecipe.class.getResourceAsStream(storage.getRecipesFileName());
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
        InputStream stream2 = LoadManager.class.getResourceAsStream(storage.getAutogenFileName());
        if (stream2 != null) {
            try {
                String classSrc = Util.readAsTxt(stream2);
                Class<?> clazz = loader.parseClass(classSrc);
                if (RecipeGenerator.class.isAssignableFrom(clazz)) {
                    RecipeGenerator<T> generator = (RecipeGenerator<T>) clazz.newInstance();
                    generator.setMap(map);
                    generator.run();
                }
            } catch (IOException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }
    }


    private static void initAutogenFromCore() {//todo : remove
        InputStream stream = II_Core.class.getResourceAsStream("autogen.json");
        AutogenRecipes.loadAndInit(stream == null ? null : new InputStreamReader(stream));
    }


    public static class Util {
        public static String readAsTxt(InputStream stream) throws IOException {
            if (stream == null) {
                throw new IOException("no stream provided");
            }
            return readAll(new InputStreamReader(stream));
        }

        private static String readAll(InputStreamReader reader) throws IOException {
            StringBuilder builder = new StringBuilder();
            char[] ar = new char[1000];
            int read = 0;
            while ((read = reader.read(ar)) != -1) {
                builder.append(ar, 0, read);
            }
            return builder.toString();
        }
    }
}

