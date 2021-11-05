package idealindustrial.impl.loader;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import idealindustrial.util.config.IConfig;
import idealindustrial.util.config.II_MainConfig;
import net.minecraftforge.common.config.Configuration;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

public class ConfigsLoader implements Runnable {
    @SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
    static List<Class<? extends IConfig>> configList = Arrays.asList(II_MainConfig.class);

    FMLPreInitializationEvent event;

    public ConfigsLoader(FMLPreInitializationEvent event) {
        this.event = event;
    }


    @Override
    public void run() {
        for (Class<? extends  IConfig> cl : configList) {
            try {
                load(cl);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
                throw new IllegalStateException("failed loading configs, please check your config class signature",e);
            }
        }
    }

    private void load(Class<? extends IConfig> configClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
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
