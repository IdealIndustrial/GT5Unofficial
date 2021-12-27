package idealindustrial.api.tile.inventory;

import idealindustrial.impl.item.stack.II_ItemStack;
import idealindustrial.util.misc.II_NBTSerializable;

public interface InternalInventory extends Iterable<II_ItemStack>, BaseInventory, II_NBTSerializable {

    int extract(II_ItemStack is, boolean doExtract);

    int insert(II_ItemStack is, boolean doInsert);

    boolean canStore(II_ItemStack[] contents);

    void validate();

    boolean isEmpty();

}
