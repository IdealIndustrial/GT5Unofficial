package idealindustrial.tile.covers;

import idealindustrial.util.item.II_HashedStack;
import idealindustrial.util.item.II_ItemHelper;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * each cover has an ID, Item with damage and II_CoverBehavior
 * ID is hash of II_HashedStack made from ItemWithDamage
 *
 */
public class II_CoverRegistry {

    static Map<II_HashedStack, II_CoverBehavior> behaviorMap = II_ItemHelper.queryMap(new HashMap<>());

    public static void registerCover(ItemStack stack, II_CoverBehavior behavior) {
        II_HashedStack hashedStack = new II_HashedStack(stack);
        assert !behaviorMap.containsKey(hashedStack);
        behaviorMap.put(hashedStack, behavior);
    }

    public static II_CoverBehavior behaviorFromStack(II_HashedStack stack) {
        return behaviorMap.get(stack);
    }

    public static II_CoverBehavior behaviorFromID(int id) {
        return behaviorFromStack(new II_HashedStack(id));
    }


}
