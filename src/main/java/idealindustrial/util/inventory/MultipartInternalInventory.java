package idealindustrial.util.inventory;

import com.sun.istack.internal.NotNull;
import idealindustrial.util.inventory.interfaces.InternalInventory;
import idealindustrial.util.item.HashedStack;
import idealindustrial.util.item.II_ItemStack;
import idealindustrial.util.item.II_LinkedStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MultipartInternalInventory implements InternalInventory {

    Map<HashedStack, II_LinkedStack> map = new HashMap<>();

    @Override
    public int extract(II_ItemStack is, boolean doExtract) {
        II_LinkedStack stack = map.get(is.toHashedStack());
        if (stack == null) {
            return 0;
        }
        int toExtract = is.amount;
        for (Iterator<II_LinkedStack> iterator = stack.iterator(); iterator.hasNext(); ) {
            II_LinkedStack linkedStack = iterator.next();
            if (linkedStack.equals(is)) {
                int extract = Math.min(toExtract, linkedStack.amount);
                if (doExtract) {
                    linkedStack.amount -= extract;
                    if (linkedStack.amount <= 0) {
                        iterator.remove();
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
        II_LinkedStack stack = map.get(is.toHashedStack());
        if (stack == null) {
            map.put(is.toHashedStack(), new II_LinkedStack(is));
            return 0;
        }
        stack.append(is);
        return 0;
    }

    @Override
    public boolean canStore(II_ItemStack[] contents) {
       throw new IllegalStateException("not implemented yet");
    }

    @Override
    @NotNull
    public Iterator<II_ItemStack> iterator() {
        return new Iterator<II_ItemStack>() {
            final Iterator<II_LinkedStack> mapIterator = map.values().iterator();
            Iterator<II_LinkedStack> currentIterator;

            @Override
            public boolean hasNext() {
                return mapIterator.hasNext() || (currentIterator != null && currentIterator.hasNext());
            }

            @Override
            public II_ItemStack next() { //idk, may be recursion is slow so todo: optimize =)
                if (currentIterator == null) {
                    currentIterator = mapIterator.next().iterator();
                }
                if (currentIterator.hasNext()) {
                    return currentIterator.next();
                } else {
                    currentIterator = null;
                }
                return next();
            }
        };
    }

    @Override
    public void nbtSave(NBTTagCompound tag, String prefix) {

    }

    @Override
    public void nbtLoad(NBTTagCompound tag, String prefix) {

    }

    @Override
    public void validate() {

    }
}
