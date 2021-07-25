package idealindustrial.util.misc;

import java.util.Map;
import java.util.function.Supplier;

public class II_Util {

    public static <T> Defaultable<T> makeDefault(T defaultValue) {
        return new DefaultableImpl<>(defaultValue);
    }

    public static <T>Supplier<T> singletonSupplier(T value) {
        return () -> value;
    }

    @SuppressWarnings("unchecked")
    public static <K, V> void rehash(Map<K, V> map) {
        K[] keys = (K[]) new Object[map.size()];
        V[] values = (V[]) new Object[map.size()];//not using entries to save some performance
        int i = 0;
        for (Map.Entry<K,V> entry : map.entrySet()) {
            keys[i] = entry.getKey();
            values[i] = entry.getValue();
            ++i;
        }
        map.clear();
        for(int j = 0; j < i; j++) {
            map.put(keys[j], values[j]);
        }
    }
}
