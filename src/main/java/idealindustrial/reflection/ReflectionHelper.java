package idealindustrial.reflection;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ReflectionHelper {

    public static Object call(Method method, Object on, Object... args) {
        try {
            return method.invoke(on, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static final Map<Pair<Class<?>, String[]>, Field[]> namesToFieldsCache = new HashMap<>();

    private static Field[] getFields(Class<?> clazz, String[] names) {
        Pair<Class<?>, String[]> pair = new ImmutablePair<>(clazz, names);
        if (!namesToFieldsCache.containsKey(pair)) {
            Field[] fields = new Field[names.length];
            for (int i = 0; i < names.length; i++) {
                try {
                    fields[i] = clazz.getDeclaredField(names[i]);
                    fields[i].setAccessible(true);
                } catch (NoSuchFieldException exception) {
                    exception.printStackTrace();
                    throw new IllegalStateException(exception);
                }
            }
            namesToFieldsCache.put(pair, fields);
        }
        return namesToFieldsCache.get(pair);
    }

    public static <T> void moveFields(T from, T to, Class<T> clazz, String... fields) {
        for (Field field : getFields(clazz, fields)) {
            try {
                field.set(to, field.get(from));
            } catch (IllegalAccessException exception) {
                throw new IllegalStateException(exception);//this should never happen
            }
        }
    }
}
