package idealindustrial.util.item;

import com.google.common.collect.HashMultimap;
import idealindustrial.util.misc.II_Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class II_ItemHelper {


    private static final List<II_HashedStack> hashedStacks = new ArrayList<>();
    private static final List<HashMap<? extends II_HashedStack, ?>> hashMaps = new ArrayList<>();
    private static final List<HashMultimap<? extends II_HashedStack, ?>> hashMultiMaps = new ArrayList<>();
    /**
     * Minecraft Item IDs can change during server start up
     * so we need to fix hash of stack if this happens
     */
    public static II_HashedStack queryStack(II_HashedStack stack) {
        hashedStacks.add(stack);
        return stack;
    }

    public static <K extends II_HashedStack, V> HashMap<K, V> queryMap(HashMap<K, V> map) {
        hashMaps.add(map);
        return map;
    }

    public static <K extends II_HashedStack, V> HashMultimap<K, V> queryMap(HashMultimap<K, V> map) {
        hashMultiMaps.add(map);
        return map;
    }

    public static void onIDsCharge() { //todo : link
        for (II_HashedStack stack : hashedStacks) {
            stack.fixHash();
        }
        for (HashMap<? extends II_HashedStack, ?> map : hashMaps) {//non hackery solution, idk if it's possible here
            for (Map.Entry<? extends II_HashedStack, ?> entry : map.entrySet()) {
                entry.getKey().fixHash();
            }
            II_Util.rehash(map);
        }

        for (HashMultimap<? extends II_HashedStack, ?> map : hashMultiMaps) {//non hackery solution, idk if it's possible here
            for (Object stack : map.keySet()) {
                ((II_HashedStack) stack).fixHash();
            }
            II_Util.rehash(map);
        }
    }

}
