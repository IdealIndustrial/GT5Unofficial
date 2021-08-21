package idealindustrial.util.inventory;

import idealindustrial.util.item.II_ItemStack;
import idealindustrial.util.item.II_StackSignature;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class II_EmptyInventory implements II_RecipedInventory {

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
    public boolean canStore(II_ItemStack[] contents) {
        return false;
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

    @Override
    public boolean hasMatch(II_StackSignature signature) {
        return false;
    }

    @Override
    public void extract(II_StackSignature signature) {

    }

    @Override
    public List<II_ItemStack> getContents() {
        return new ArrayList<>();
    }
}
