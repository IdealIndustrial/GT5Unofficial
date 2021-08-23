package gregtech.common.config;

import gregtech.api.interfaces.internal.GT_Config;
import gregtech.api.interfaces.internal.IGT_Config;
import idealindustrial.hooks.II_RemapGTMaterialsPatch;
import net.minecraftforge.common.config.Configuration;

import java.lang.reflect.InvocationTargetException;

public class GT_DebugConfig implements IGT_Config {
    @GT_Config(category = "recipes", configComment = "enables GT_Recipes.log, where all conflicts are logged xD")
    public static boolean recipeConflicts = true;
    @GT_Config(category = "recipes", configComment = "sets filters for GT_Recipes.log by recipeMap unlocalized name, all filters are applied with OR with String.contains(filter)")
    public static String[] recipeMapsFilter = new String[]{"."};
    @GT_Config(category = "recipes", configComment = "sets filters for GT_Recipes.log by class-recipeLoader name, all filters are applied with OR with String.contains(filter)")
    public static String[] recipeSourcesFilter = new String[]{"GT"};


    @GT_Config(category = "extracells2", configComment = "enables fluid spam in GT ( adds 2000-3000 useless fluids), used for EC2 fluid interface testing")
    public static boolean addTrashFluids = false;

    @GT_Config(category = "materials", configComment = "Remaps all meta generated gt items by materials (for eg. \"33->35\" maps all world cobalt items to copper ones)")
    protected static String[] mapWrongMaterialIDs = new String[]{"33->35"};
    @GT_Config(category = "materials", configComment = "Enables gt remap materials IDs feature")
    public static boolean mapIDsEnabled = false;

    @Override
    public void load(Configuration configuration) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        IGT_Config.super.load(configuration);

        for (String s : mapWrongMaterialIDs) {
            String[] kv = s.split("->");
            II_RemapGTMaterialsPatch.subIDsMap.put(Integer.valueOf(kv[0]), Integer.valueOf(kv[1]));
        }
    }

    @Override
    public String getName() {
        return "Debug";
    }

}
