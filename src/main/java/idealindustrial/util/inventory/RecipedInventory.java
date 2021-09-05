package idealindustrial.util.inventory;

import idealindustrial.util.item.II_ItemStack;
import idealindustrial.util.item.II_StackSignature;

import java.util.List;

public interface RecipedInventory extends InternalInventory {

    boolean hasMatch(II_StackSignature signature);

    void extract(II_StackSignature signature);

    List<II_ItemStack> getContents();

}
