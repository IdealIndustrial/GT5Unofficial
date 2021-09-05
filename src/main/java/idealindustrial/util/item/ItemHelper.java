package idealindustrial.util.item;

import com.google.common.collect.HashMultimap;
import idealindustrial.util.misc.II_Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemHelper {


    private static final List<HashedStack> hashedStacks = new ArrayList<>();
    private static final List<HashMap<? extends HashedStack, ?>> hashMaps = new ArrayList<>();
    private static final List<HashMultimap<? extends HashedStack, ?>> hashMultiMaps = new ArrayList<>();
    /**
     * Minecraft Item IDs can change during server start up
     * so we need to fix hash of stack if this happens
     */
    public static HashedStack queryStack(HashedStack stack) {
        hashedStacks.add(stack);
        return stack;
    }

    public static <K extends HashedStack, V> HashMap<K, V> queryMap(HashMap<K, V> map) {
        hashMaps.add(map);
        return map;
    }

    public static <K extends HashedStack, V> HashMultimap<K, V> queryMap(HashMultimap<K, V> map) {
        hashMultiMaps.add(map);
        return map;
    }

    public static void onIDsCharge() { //todo : link
        for (HashedStack stack : hashedStacks) {
            stack.fixHash();
        }
        for (HashMap<? extends HashedStack, ?> map : hashMaps) {//non hackery solution, idk if it's possible here
            for (Map.Entry<? extends HashedStack, ?> entry : map.entrySet()) {
                entry.getKey().fixHash();
            }
            II_Util.rehash(map);
        }

        for (HashMultimap<? extends HashedStack, ?> map : hashMultiMaps) {//non hackery solution, idk if it's possible here
            for (Object stack : map.keySet()) {
                ((HashedStack) stack).fixHash();
            }
            II_Util.rehash(map);
        }
    }

}
