package idealindustrial.util.inventory;

import idealindustrial.util.item.II_ItemStack;
import idealindustrial.util.item.II_StackSignature;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class II_ArrayRecipedInventory implements II_RecipedInventory {

    II_ItemStack[] contents;
    int maxStackSize;

    public II_ArrayRecipedInventory(II_ItemStack[] contents) {
        this.contents = contents;
        this.maxStackSize = Integer.MAX_VALUE;
    }

    public II_ArrayRecipedInventory(int size, int stackSize) {
        this.contents = new II_ItemStack[size];
        this.maxStackSize = stackSize;
    }

    @Override
    public boolean hasMatch(II_StackSignature signature) {
        for (II_ItemStack stack : contents) {
            if (stack == null) {
                continue;
            }
            if (signature.isEqual(stack)) {
                return true;
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
        return new Iterator<II_ItemStack>() {
            int i = 0;

            @Override
            public boolean hasNext() {
                return i < contents.length;
            }

            @Override
            public II_ItemStack next() {
                return contents[i++];
            }
        };
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
            NBTTagCompound itemTag = inventoryTag.getCompoundTag(""+i);
            if (itemTag == null) {
                continue;
            }
            contents[i] = II_ItemStack.loadFromNBT(itemTag);
        }
    }
}
