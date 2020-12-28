package gregtech.common.config;

import gregtech.api.interfaces.internal.IGT_Config;
import net.minecraftforge.common.config.Configuration;
import java.lang.reflect.InvocationTargetException;

public class GT_DebugConfig implements IGT_Config {
    public static boolean recipeConflicts = true;
    public static String[] recipeMapsFilter = new String[]{"."};
    public static String[] recipeSourcesFilter = new String[]{"GT"};

    @Override
    public void load(Configuration configuration) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        IGT_Config.super.load(configuration);
    }

    @Override
    public String getName() {
        return "Debug";
    }

    //todo: add map for comments
}
