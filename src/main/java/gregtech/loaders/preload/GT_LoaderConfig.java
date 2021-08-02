package gregtech.loaders.preload;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import gregtech.api.interfaces.internal.IGT_Config;
import gregtech.common.config.GT_DebugConfig;
import net.minecraftforge.common.config.Configuration;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

public class GT_LoaderConfig implements Runnable {
    static List<Class<? extends IGT_Config>> configList = Arrays.asList(GT_DebugConfig.class);

    FMLPreInitializationEvent event;

    public GT_LoaderConfig(FMLPreInitializationEvent event) {
        this.event = event;
    }


    @Override
    public void run() {
        for (Class<? extends  IGT_Config> cl : configList) {
            try {
                load(cl);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
                throw new IllegalStateException("failed loading configs, please check your config class signature",e);
            }
        }
    }

    private void load(Class<? extends IGT_Config> configClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Object configObject = configClass.getConstructor().newInstance();
        String configName = (String)configClass.getMethod("getName").invoke(configObject);
        Configuration configuration = loadConfiguration(configName);
        configClass.getMethod("load", Configuration.class).invoke(configObject, configuration);
        configuration.save();
    }

    private Configuration loadConfiguration(String fileName) {
        File file = new File(new File(event.getModConfigurationDirectory(), "GregTech"), fileName + ".cfg");
        Configuration config = new Configuration(file);
        config.load();
        return config;
    }
}
