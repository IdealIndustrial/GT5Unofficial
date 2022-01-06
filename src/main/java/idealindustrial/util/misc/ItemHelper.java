package idealindustrial.util.misc;

import com.google.common.collect.HashMultimap;

import java.util.*;

public class ItemHelper {


    private static final List<Rehashable> hashedStacks = new ArrayList<>();
    private static final List<Map<? extends Rehashable, ?>> hashMaps = new ArrayList<>();
    private static final List<Set<? extends Rehashable>> hashSets = new ArrayList<>();
    private static final List<HashMultimap<? extends Rehashable, ?>> hashMultiMaps = new ArrayList<>();
    private static final List<Map<?, ?>> nonRehashableMas = new ArrayList<>();

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

    public static <K, V> HashMap<K, V> queryNonRehashable(HashMap<K, V> map) {
        nonRehashableMas.add(map);
        return map;
    }

    public static <K extends Rehashable> Set<K> querySet(Set<K> set) {
        hashSets.add(set);
        return set;
    }

    @SafeVarargs
    public static <K extends Rehashable> Set<K> set(K... elements) {
        return querySet(new HashSet<>(Arrays.asList(elements)));
    }

    @SafeVarargs
    public static <K extends Rehashable> Set<K> set(Set<K>... sets) {
        Set<K> out = querySet(new HashSet<>());
        for (Set<K> set : sets) {
            out.addAll(set);
        }
        return out;
    }

    public static void onIDsCharge() {
        for (Rehashable stack : hashedStacks) {
            stack.fixHash();
        }
        for (Map<? extends Rehashable, ?> map : hashMaps) {//non hackery solution, idk if it's possible here
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

        for (Set<? extends Rehashable> set : hashSets) {
            for (Rehashable item : set) {
                item.fixHash();
            }
            II_Util.rehash(set);
        }
        for (Map<?, ?> map : nonRehashableMas) {
            II_Util.rehash(map);
        }
    }

    public interface Rehashable {
        void fixHash();
    }


}
