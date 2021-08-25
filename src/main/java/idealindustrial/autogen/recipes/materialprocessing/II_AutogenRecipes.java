package idealindustrial.autogen.recipes.materialprocessing;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import idealindustrial.autogen.material.II_Material;
import idealindustrial.autogen.material.II_Materials;
import idealindustrial.autogen.recipes.RecipeAction;
import idealindustrial.util.json.JsonMaterialSerializer;

import java.io.Reader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class II_AutogenRecipes {

    public static Gson gson = new GsonBuilder().registerTypeAdapter(II_Material.class, new JsonMaterialSerializer()).create();
    public static II_AutogenRecipes INSTANCE;
    public II_BenderAutogenRecipes benderAutogenRecipes = new II_BenderAutogenRecipes();

    public transient List<II_AutogenRecipeAdder> adders = new ArrayList<>();

    public II_AutogenRecipes() {

    }

    protected void addAdders() {
        Class<? extends II_AutogenRecipes> clazz = getClass();
        for (Field field : clazz.getDeclaredFields()) {
            if (II_AutogenRecipeAdder.class.isAssignableFrom(field.getType())) {
                try {
                    adders.add((II_AutogenRecipeAdder) field.get(this));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void init() {
        for (II_Material material : II_Materials.allMaterials) {
            for (II_AutogenRecipeAdder adder : adders) {
                adder.addRecipes(material);
            }
        }
        benderAutogenRecipes.blackList.add(new AutogenRecipeDefinition(II_Materials.iron, RecipeAction.plateBending));
        System.out.println(gson.toJson(this));
    }

    public static void loadAndInit(Reader reader) {
        if (reader != null) {
            try {
                INSTANCE = gson.fromJson(reader, II_AutogenRecipes.class);
            } catch (JsonParseException e) {
                e.printStackTrace();
                INSTANCE = new II_AutogenRecipes();
            }
        } else {
            INSTANCE = new II_AutogenRecipes();
        }
        INSTANCE.addAdders();
        INSTANCE.init();

    }
}
