package gregtech.api.interfaces.internal;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public interface IGT_Config {


    //todo: move all impl stuff to abstract class
    /**
     * loads everything, gets invoked via reflection from {@link gregtech.loaders.preload.GT_LoaderConfig}
     * @param configuration is cfg instance that is created from file with name {@link #getName()}
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
     * simple method to load all static fields from config file that are annotated with {@link gregtech.api.interfaces.internal.GT_Config}
     * initial values are used as default value ( or may be overridden)
     * use protected fields to load from config and then process in {@link #load(Configuration)} for non common objects for eg. Map<>
     */
    default void loadAll(Class<? extends IGT_Config> loadAll, Configuration configuration) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        for (Field field : loadAll.getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers()))
                continue;
            if (!field.isAnnotationPresent(GT_Config.class))
                continue;
            field.setAccessible(true);
            GT_Config config = field.getAnnotation(GT_Config.class);
            String category = config.category();
            String name = config.configName().equals("") ? field.getName() : config.configName();
            String comment = config.configComment();
            field.set(null, getWithType(field.getType(), getProperty(category, name, comment, field, configuration)));
        }
    }
    /*gregtechproxy.allowDisableToolTips = tMainConfig.get("general", "AllowDisablingLargeTooltips", true).getBoolean(true);*/

    static Property getProperty(String category, String name, String comment, Field field, Configuration configuration) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        /*if (String.class.equals(field.getType())) {
            return configuration.get("main", field.getName(), (String) field.getType().cast(field.get(null)));
        }*/
        if (comment.equals("")) {
            return (Property) Configuration.class.getMethod("get", String.class, String.class, field.getType())
                    .invoke(configuration, category, name, field.get(null));
        }
        else {
            return (Property) Configuration.class.getMethod("get", String.class, String.class, field.getType(), String.class)
                    .invoke(configuration, category, name, field.get(null), comment);

        }
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
