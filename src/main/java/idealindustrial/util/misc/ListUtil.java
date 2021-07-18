package idealindustrial.util.misc;

import java.util.Arrays;
import java.util.List;

public class ListUtil {

    public static  <T> void swap(List<T> list, int i, int j) {
        T a = list.get(i), b = list.get(j);
        list.set(i, b);
        list.set(j, a);
    }

    public static  <T> void addOrMoveFirst(List<T> list, T object) {
        int index = list.indexOf(object);
        if (index == -1) {
            list.add(object);
            swap(list, 0, list.size() - 1);
        }
        else {
            swap(list, 0, index);
        }
    }

    @SafeVarargs
    public static <T> List<T> of(T... contents) {
        return Arrays.asList(contents);
    }
}
