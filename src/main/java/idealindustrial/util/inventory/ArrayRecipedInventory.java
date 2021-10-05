package idealindustrial.util.inventory;

import idealindustrial.util.inventory.interfaces.RecipedInventory;
import idealindustrial.util.item.II_ItemStack;
import idealindustrial.util.item.II_StackSignature;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ArrayRecipedInventory implements RecipedInventory {

    protected II_ItemStack[] contents;
    protected int maxStackSize;

    public ArrayRecipedInventory(II_ItemStack[] contents) {
        this.contents = contents;
        this.maxStackSize = Integer.MAX_VALUE;
    }

    public ArrayRecipedInventory(int size, int stackSize) {
        this.contents = new II_ItemStack[size];
        this.maxStackSize = stackSize;
    }

    @Override
    public boolean hasMatch(II_StackSignature signature) {
        int have = 0;
        for (II_ItemStack stack : contents) {
            if (stack == null) {
                continue;
            }
            if (signature.isEqual(stack)) {
                have += stack.amount;
                if (have >= signature.amount) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void extract(II_StackSignature signature) {
        int toExtract = signature.amount;
        for (int i = 0; i < contents.length; i++) {
            II_ItemStack stack = contents[i];
            if (stack == null) {
                continue;
            }
            if (signature.isEqual(stack)) {
                int extract = Math.min(toExtract, stack.amount);
                stack.amount -= extract;
                if (stack.amount <= 0) {
                    contents[i] = null;
                }
                toExtract -= extract;
                if (toExtract <= 0) {
                    return;
                }
            }
        }
        assert toExtract <= 0;
    }

    @Override
    public List<II_ItemStack> getContents() {
        return Arrays.asList(contents);
    }

    @Override
    public int extract(II_ItemStack is, boolean doExtract) {
        int toExtract = is.amount;
        for (int i = 0; i < contents.length; i++) {
            II_ItemStack stack = contents[i];
            if (stack == null) {
                continue;
            }
            if (stack.equals(is)) {
                int extract = Math.min(toExtract, stack.amount);
                if (doExtract) {
                    stack.amount -= extract;
                    if (stack.amount <= 0) {
                        contents[i] = null;
                    }
                }
                toExtract -= extract;
                if (toExtract <= 0) {
                    return is.amount;
                }
            }
        }
        assert !doExtract || toExtract <= 0;
        return is.amount - toExtract;
    }

    @Override
    public int insert(II_ItemStack is, boolean doInsert) {
        int toInsert = is.amount;
        for (int i = 0; i < contents.length; i++) {
            II_ItemStack stack = contents[i];
            if (stack == null) {
                if (doInsert) {//todo properly handle maxStackSize here
                    contents[i] = is;
                }
                return 0;
            }
            if (stack.equals(is)) {
                int insert = Math.min(is.amount, maxStackSize - stack.amount);
                if (doInsert) {
                    stack.amount += insert;
                }
                toInsert -= insert;
                if (toInsert <= 0) {
                    return 0;
                }
            }
        }
        return is.amount - toInsert;
    }

    @Override
    public Iterator<II_ItemStack> iterator() {
        return new II_ArrayInventoryIterator();
    }

    @Override
    public boolean canStore(II_ItemStack[] contents) {
        int empty = emptySlots();
        if (empty >= contents.length) {
            return true;
        }
        int usedSpace = 0;
        a:
        for (II_ItemStack toStore : contents) {
            for (II_ItemStack stored : this.contents) {
                if (stored != null && stored.equals(toStore)) {
                    if (stored.amount + toStore.amount <= maxStackSize) {
                        continue a;
                    }
                    break;
                }
            }
            usedSpace++;
        }
        return empty >= usedSpace;
    }

    public int emptySlots() {
        int slots = 0;
        for (II_ItemStack content : contents) {
            if (content == null) {
                slots++;
            }
        }
        return slots;
    }

    protected class II_ArrayInventoryIterator implements Iterator<II_ItemStack> {

        int i;

        public II_ArrayInventoryIterator() {
            i = 0;
            while (i < contents.length && contents[i] == null) {
                i++;
            }
        }

        @Override
        public boolean hasNext() {
            return i < contents.length;
        }

        @Override
        public II_ItemStack next() {
            return contents[i++];
        }
    }

    @Override
    public ItemStack get(int i) {
        return contents[i] == null ? null : contents[i].toMCStack();
    }

    @Override
    public void set(int i, ItemStack stack) {
        contents[i] = stack == null ? null : new II_ItemStack(stack);
    }

    @Override
    public ItemStack reduce(int i, int amount) {
        ItemStack stack = contents[i].toMCStack();
        contents[i].amount -= amount;
        stack.stackSize = amount;
        if (contents[i].amount == 0) {
            contents[i] = null;
        }
        return stack;
    }

    @Override
    public int size() {
        return contents.length;
    }

    @Override
    public boolean allowInput(int i, ItemStack stack) {
        return true;
    }

    @Override
    public void nbtSave(NBTTagCompound tag, String prefix) {//todo replace with tag list
        NBTTagCompound inventoryTag = new NBTTagCompound();
        for (int i = 0; i < size(); i++) {
            if (contents[i] == null) {
                continue;
            }
            inventoryTag.setTag("" + i, contents[i].writeToNBT(new NBTTagCompound()));
        }
        tag.setTag(prefix + "Inv", inventoryTag);
    }

    @Override
    public void nbtLoad(NBTTagCompound tag, String prefix) {
        NBTTagCompound inventoryTag = tag.getCompoundTag(prefix + "Inv");
        if (inventoryTag == null) {
            return;
        }
        for (int i = 0; i < size(); i++) {
            NBTTagCompound itemTag = inventoryTag.getCompoundTag("" + i);
            if (itemTag == null) {
                continue;
            }
            contents[i] = II_ItemStack.loadFromNBT(itemTag);
        }
    }

    @Override
    public void validate() {
        for (int i = 0; i < contents.length; i++) {
            if (contents[i] != null && contents[i].amount <= 0) {
                contents[i] = null;
            }
        }
    }
}
