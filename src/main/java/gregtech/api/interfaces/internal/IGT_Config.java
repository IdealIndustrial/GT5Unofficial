package gregtech.api.interfaces.internal;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public interface IGT_Config {

    /**
     * loads everything, gets invoked via reflection from {@link gregtech.loaders.preload.GT_LoaderConfig}
     * @param configuration is cfg instance created from file with name {@link #getName()}
     */
    default void load(Configuration configuration) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        loadAll(getClass(), configuration);
    }

    /**
     * @return name of configuration file
     */
    String getName();

    /**
     * should not be overridden!
     * simple method to load all public static fields from config file according to their names
     * initial values is used as default value
     */
    default void loadAll(Class<? extends IGT_Config> loadAll, Configuration configuration) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        for (Field field : loadAll.getFields()) {
            if (!Modifier.isStatic(field.getModifiers()))
                continue;
            field.set(null, getWithType(field.getType(), getProperty(field, configuration)));
        }
    }
    /*gregtechproxy.allowDisableToolTips = tMainConfig.get("general", "AllowDisablingLargeTooltips", true).getBoolean(true);*/

    static Property getProperty(Field field, Configuration configuration) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (String.class.equals(field.getType())) {
            return configuration.get("main", field.getName(), (String) field.getType().cast(field.get(null)));
        }
        Method get = Configuration.class.getMethod("get", String.class, String.class, field.getType());
        return (Property) get.invoke(configuration, "main", field.getName(), field.get(null));
       // throw new IllegalStateException("type of "+field.getName() + ": " + field.getType().getName()+ " handling is not implemented yet");
    }

    @SuppressWarnings("rawtypes")
    static Object getWithType(Class type, Property property) {
        if (String.class.equals(type)) {
            return property.getString();
        }
        if (int.class.equals(type)) {
            return property.getInt();
        }
        if (double.class.equals(type)) {
            return property.getDouble();
        }
        if (String[].class.equals(type)) {
            return property.getStringList();
        }
        if (boolean.class.equals(type)) {
            return property.getBoolean();
        }
        throw new IllegalStateException("type: "+type.getName() + " handling is not implemented yet");
    }


}
