package idealindustrial.util.misc;

import org.omg.CORBA.Object;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Supplier;

public class II_Util {

    public static <T> Defaultable<T> makeDefault(T defaultValue) {
        return new DefaultableImpl<>(defaultValue);
    }

    public static <T> Supplier<T> singletonSupplier(T value) {
        return () -> value;
    }

    @SuppressWarnings("unchecked")
    public static <K, V> void rehash(Map<K, V> map) {
        K[] keys = (K[]) new Object[map.size()];
        V[] values = (V[]) new Object[map.size()];//not using entries to save some performance
        int i = 0;
        for (Map.Entry<K, V> entry : map.entrySet()) {
            keys[i] = entry.getKey();
            values[i] = entry.getValue();
            ++i;
        }
        map.clear();
        for (int j = 0; j < i; j++) {
            map.put(keys[j], values[j]);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] nonNull(T[] ar) {
        return ar == null ? (T[]) new Object[0] : ar;
    }

    public static boolean[] trueAr(int size) {
        boolean[] ar = new boolean[size];
        Arrays.fill(ar, true);
        return ar;
    }

    public static long intsToLong(int a, int b) {
        return ((long) a) << 32 | ((long) b);
    }

    public static int intAFromLong(long l) {
        return (int) (l >> 32);
    }

    public static int intBFromLong(long l)  {
        return (int) l;
    }
}
