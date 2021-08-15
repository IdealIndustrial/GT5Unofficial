package idealindustrial.tile.covers;

import gregtech.api.enums.ItemList;
import idealindustrial.itemgen.material.II_Material;
import idealindustrial.itemgen.material.II_Materials;
import idealindustrial.itemgen.material.Prefixes;
import idealindustrial.itemgen.oredict.II_OreDict;
import idealindustrial.tile.covers.implementations.II_CoverChameleon;
import idealindustrial.tile.covers.implementations.II_CoverPlate;
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

    static Map<II_HashedStack, II_BaseCoverBehavior<?>> behaviorMap = II_ItemHelper.queryMap(new HashMap<>());

    public static void registerCover(II_HashedStack hashedStack, II_BaseCoverBehavior<?> behavior) {
        assert !behaviorMap.containsKey(hashedStack);
        behaviorMap.put(hashedStack, behavior);
    }

    public static II_BaseCoverBehavior<?> behaviorFromStack(II_HashedStack stack) {
        return behaviorMap.get(stack);
    }

    public static II_BaseCoverBehavior<?> behaviorFromID(int id) {
        return behaviorFromStack(new II_HashedStack(id));
    }

    public static int idFromStack(II_HashedStack stack) {
        if (behaviorMap.containsKey(stack)) {
            return stack.hashCode();
        }
        return 0;
    }

    public static boolean isCover(II_HashedStack stack) {
        return behaviorMap.containsKey(stack);
    }

    public static void init() {
        for (II_Material material : II_Materials.allMaterials) {
            if (material.isEnabled(Prefixes.plate)) {
                II_BaseCoverBehavior<?> coverBehavior = new II_CoverPlate(material);
                for (II_HashedStack hashedStack : II_OreDict.get(Prefixes.plate, II_Materials.iron).getSubItems()) {
                    registerCover(hashedStack, coverBehavior);
                }
            }
        }

        registerCover(new II_HashedStack(ItemList.FluidFilterItem.get(1)), new II_CoverChameleon());
        int a = 0;
    }


}
