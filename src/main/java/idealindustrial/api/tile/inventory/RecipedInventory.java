package idealindustrial.api.tile.inventory;

import idealindustrial.impl.item.stack.II_ItemStack;
import idealindustrial.impl.item.stack.II_StackSignature;

import java.util.List;

public interface RecipedInventory extends InternalInventory {

    boolean hasMatch(II_StackSignature signature);

    void extract(II_StackSignature signature);

    List<II_ItemStack> getContents();

}
