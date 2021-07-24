package idealindustrial.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class II_ReflectionHelper {

    public static Object call(Method method, Object on, Object... args) {
        try {
            return method.invoke(on, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }
}
