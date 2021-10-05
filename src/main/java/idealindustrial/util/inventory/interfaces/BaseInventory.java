package idealindustrial.util.inventory.interfaces;

import idealindustrial.util.item.II_ItemStack;
import net.minecraft.item.ItemStack;

public interface BaseInventory {

    default ItemStack get(int i) {
        return null;
    }

    default void set(int i, ItemStack stack) {

    }

    default ItemStack reduce(int i, int anount) {
        return null;
    }

    default int size() {
        return 0;
    }

    default boolean allowInput(int i, ItemStack stack) {
        return true;
    }
}
