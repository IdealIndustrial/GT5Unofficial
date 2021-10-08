package idealindustrial.util.item;

import com.google.common.collect.HashMultimap;
import idealindustrial.util.misc.II_Util;

import java.util.*;

public class ItemHelper {


    private static final List<Rehashable> hashedStacks = new ArrayList<>();
    private static final List<HashMap<? extends Rehashable, ?>> hashMaps = new ArrayList<>();
    private static final List<HashSet<? extends Rehashable>> hashSets = new ArrayList<>();
    private static final List<HashMultimap<? extends Rehashable, ?>> hashMultiMaps = new ArrayList<>();
    /**
     * Minecraft Item IDs can change during server start up
     * so we need to fix hash of stack if this happens
     */
    public static Rehashable queryStack(Rehashable stack) {
        hashedStacks.add(stack);
        return stack;
    }

    public static <K extends Rehashable, V> HashMap<K, V> queryMap(HashMap<K, V> map) {
        hashMaps.add(map);
        return map;
    }

    public static <K extends Rehashable, V> HashMultimap<K, V> queryMap(HashMultimap<K, V> map) {
        hashMultiMaps.add(map);
        return map;
    }

    public static <K extends Rehashable> HashSet<K> querySet(HashSet<K> set) {
        hashSets.add(set);
        return set;
    }

    public static void onIDsCharge() { //todo : link
        for (Rehashable stack : hashedStacks) {
            stack.fixHash();
        }
        for (HashMap<? extends Rehashable, ?> map : hashMaps) {//non hackery solution, idk if it's possible here
            for (Map.Entry<? extends Rehashable, ?> entry : map.entrySet()) {
                entry.getKey().fixHash();
            }
            II_Util.rehash(map);
        }

        for (HashMultimap<? extends Rehashable, ?> map : hashMultiMaps) {//non hackery solution, idk if it's possible here
            for (Object stack : map.keySet()) {
                ((Rehashable) stack).fixHash();
            }
            II_Util.rehash(map);
        }

        for (HashSet<? extends Rehashable> set : hashSets) {
            for (Rehashable item : set) {
                item.fixHash();;
            }
            II_Util.rehash(set);
        }
    }

    public interface Rehashable {
        void fixHash();
    }

}
