package idealindustrial.util.inventory;

import idealindustrial.util.item.II_ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Iterator;

public class II_EmptyInventory implements II_InternalInventory {

    public static final II_EmptyInventory INSTANCE = new II_EmptyInventory();
    private II_EmptyInventory() {
        
    }
    @Override
    public int extract(II_ItemStack is, boolean doExtract) {
        return 0;
    }

    @Override
    public int insert(II_ItemStack is, boolean doInsert) {
        return 0;
    }

    @Override
    public void nbtSave(NBTTagCompound tag, String prefix) {

    }

    @Override
    public void nbtLoad(NBTTagCompound tag, String prefix) {

    }

    @Override
    public Iterator<II_ItemStack> iterator() {
        return new Iterator<II_ItemStack>() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public II_ItemStack next() {
                return null;
            }
        };
    }
}
