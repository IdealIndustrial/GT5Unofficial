package idealindustrial.impl.reflection.config;

import idealindustrial.api.reflection.Config;
import idealindustrial.util.config.IConfig;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

public class ReflectionConfig implements IConfig {
    @Override
    public void load(Configuration configuration){
        loadAll(getClass(), configuration);
    }

    @Override
    public String getName() {
        return null;
    }

    public static void loadAll(Class<?> clazz, Configuration configuration) {
        for (Field field : clazz.getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers()))
                continue;
            if (!field.isAnnotationPresent(Config.class))
                continue;
            field.setAccessible(true);
            Config config = field.getAnnotation(Config.class);
            String category = config.category();
            if (category.equals("*class*")) {
                category = clazz.getSimpleName();
            }
            String name = config.configName().equals("") ? field.getName() : config.configName();
            String comment = config.configComment();
            try {
                field.set(null, getWithType(field.getType(), getProperty(category, name, comment, field, configuration)));
            } catch (NullPointerException | IllegalAccessException | NoSuchMethodException | InvocationTargetException exception) {
                exception.printStackTrace();
            }
        }
    }

    static Property getProperty(String category, String name, String comment, Field field, Configuration configuration) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (comment.equals("")) {
            return (Property) Configuration.class.getMethod("get", String.class, String.class, field.getType())
                    .invoke(configuration, category, name, field.get(null));
        }
        else {
            return (Property) Configuration.class.getMethod("get", String.class, String.class, field.getType(), String.class)
                    .invoke(configuration, category, name, field.get(null), comment);

        }
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
