package idealindustrial.util.misc;

import java.util.function.Supplier;

public class II_Util {

    public static <T> Defaultable<T> makeDefault(T defaultValue) {
        return new DefaultableImpl<>(defaultValue);
    }

    public static <T>Supplier<T> singletonSupplier(T value) {
        return () -> value;
    }
}
