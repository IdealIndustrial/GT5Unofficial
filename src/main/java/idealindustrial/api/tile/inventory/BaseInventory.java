package idealindustrial.api.tile.inventory;

import idealindustrial.impl.item.stack.II_ItemStack;
import net.minecraft.item.ItemStack;

public interface BaseInventory {

    default ItemStack get(int i) {
        return null;
    }

    default void set(int i, ItemStack stack) {

    }

    default II_ItemStack getII(int i) {
        return null;
    }

    default void set(int i, II_ItemStack stack) {

    }

    default ItemStack reduce(int i, int anount) {
        return null;
    }

    default void reduceII(int i, int amount) {

    }

    default int size() {
        return 0;
    }

    default boolean allowInput(int i, ItemStack stack) {
        return true;
    }

    default boolean isIndexed() {
        return false;
    }
}
