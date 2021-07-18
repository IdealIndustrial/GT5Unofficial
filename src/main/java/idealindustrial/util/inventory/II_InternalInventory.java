package idealindustrial.util.inventory;

import idealindustrial.util.item.II_ItemStack;
import idealindustrial.util.misc.II_NBTSerializable;

import java.util.Iterator;

public interface II_InternalInventory extends Iterable<II_ItemStack>, II_BaseInventory, II_NBTSerializable {

    int extract(II_ItemStack is, boolean doExtract);

    int insert(II_ItemStack is, boolean doInsert);

}
