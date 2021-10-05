package idealindustrial.util.inventory;

import idealindustrial.util.inventory.interfaces.InternalInventory;
import idealindustrial.util.inventory.interfaces.RecipedInventory;
import idealindustrial.util.item.HashedStack;
import idealindustrial.util.item.II_ItemStack;
import idealindustrial.util.item.II_StackSignature;
import net.minecraft.nbt.NBTTagCompound;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SegmentedRecipedInventory implements RecipedInventory {
    public static class Segment implements InternalInventory {

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
        public void validate() {

        }

        @Override
        public void nbtSave(NBTTagCompound tag, String prefix) {

        }

        @Override
        public void nbtLoad(NBTTagCompound tag, String prefix) {

        }

        @Override
        public Iterator<II_ItemStack> iterator() {
            return null;
        }
    }
    List<InternalInventory> segments;
    Map<HashedStack, InternalInventory> stackMap = new HashMap<>();

    @Override
    public boolean hasMatch(II_StackSignature signature) {
        return false;
    }

    @Override
    public void extract(II_StackSignature signature) {

    }

    @Override
    public List<II_ItemStack> getContents() {
        return null;
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
    public void validate() {

    }

    @Override
    public void nbtSave(NBTTagCompound tag, String prefix) {

    }

    @Override
    public void nbtLoad(NBTTagCompound tag, String prefix) {

    }

    @Override
    public Iterator<II_ItemStack> iterator() {
        return null;
    }
}
