package idealindustrial.impl.tile.covers;

import idealindustrial.api.tile.BaseCoverBehavior;
import idealindustrial.impl.autogen.material.II_Material;
import idealindustrial.impl.autogen.material.II_Materials;
import idealindustrial.impl.autogen.material.Prefixes;
import idealindustrial.impl.oredict.OreDict;
import idealindustrial.impl.tile.covers.implementations.CoverPlate;
import idealindustrial.impl.item.stack.HashedStack;
import idealindustrial.util.misc.ItemHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * each cover has an ID, Item with damage and II_CoverBehavior
 * ID is hash of II_HashedStack made from ItemWithDamage
 *
 */
public class CoverRegistry {

    static Map<HashedStack, BaseCoverBehavior<?>> behaviorMap = ItemHelper.queryMap(new HashMap<>());

    public static void registerCover(HashedStack hashedStack, BaseCoverBehavior<?> behavior) {
        assert !behaviorMap.containsKey(hashedStack);
        behaviorMap.put(hashedStack, behavior);
    }

    public static BaseCoverBehavior<?> behaviorFromStack(HashedStack stack) {
        return behaviorMap.get(stack);
    }

    public static BaseCoverBehavior<?> behaviorFromID(int id) {
        return behaviorFromStack(new HashedStack(id));
    }

    public static int idFromStack(HashedStack stack) {
        if (behaviorMap.containsKey(stack)) {
            return stack.hashCode();
        }
        return 0;
    }

    public static boolean isCover(HashedStack stack) {
        return behaviorMap.containsKey(stack);
    }

    public static void init() {
        for (II_Material material : II_Materials.allMaterials) {
            if (material.isEnabled(Prefixes.plate)) {
                BaseCoverBehavior<?> coverBehavior = new CoverPlate(material);
                for (HashedStack hashedStack : OreDict.get(Prefixes.plate, material).getSubItems()) {
                    registerCover(hashedStack, coverBehavior);
                }
            }
        }

//        registerCover(new HashedStack(ItemList.FluidFilterItem.get(1)), new CoverChameleon());
        int a = 0;
    }


}
