package idealindustrial.util.inventory;

import idealindustrial.tile.module.MultiMachineRecipedModule;
import idealindustrial.util.inventory.interfaces.RecipedInventory;
import idealindustrial.util.item.*;

import java.util.Iterator;
import java.util.List;

public class MultipartRecipedInternalInventory extends MultipartInternalInventory implements RecipedInventory {
    public static class SubInv extends ArrayRecipedInventory {

        MultipartInternalInventory parent;

        public SubInv(ArrayRecipedInventory old, MultipartInternalInventory parent) {
            super(old.contents);
            this.parent = parent;
        }

        public SubInv(int size, int stackSize) {
            super(size, stackSize);
        }

        @Override
        public int insert(II_ItemStack is, boolean doInsert) {
            if (doInsert) {
                parent.insert(is, true);
            }
            return super.insert(is, doInsert);
        }
    }

    MultiMachineRecipedModule<?> module;

    public MultipartRecipedInternalInventory(MultiMachineRecipedModule<?> module) {
        this.module = module;
    }

    @Override
    public int insert(II_ItemStack is, boolean doInsert) {
        if (doInsert) {
//            module.onInInventoryModified(0);
        }
        return super.insert(is, doInsert);
    }

    @Override
    public boolean hasMatch(II_StackSignature signature) {
        if (signature.getType() == CheckType.OREDICT) {
            int toExtract = signature.amount;
            for (HashedStack stack : signature.correspondingStacks()) {
                toExtract -= extractByOre(stack, toExtract, signature.getOre(), false);
                if (toExtract <= 0) {
                    return true;
                }
            }
            return false;
        }
        return extract(signature, false) >= signature.amount;
    }

    public int extractByOre(HashedStack is, int amount, String ore, boolean doExtract) {
        II_LinkedStack stack = map.get(is);
        if (stack == null) {
            return 0;
        }
        int toExtract = amount;
        for (Iterator<II_LinkedStack> iterator = stack.iterator(); iterator.hasNext(); ) {
            II_LinkedStack linkedStack = iterator.next();
            if (linkedStack.hasOre(ore)) {
                int extract = Math.min(toExtract, linkedStack.amount);
                if (doExtract) {
                    linkedStack.amount -= extract;
                    if (linkedStack.amount <= 0) {
                        iterator.remove();
                    }
                }
                toExtract -= extract;
                if (toExtract <= 0) {
                    return amount;
                }
            }
        }
        amount -= toExtract;
        assert !doExtract || toExtract <= 0;
        return amount;
    }

    @Override
    public void extract(II_StackSignature signature) {
        if (signature.getType() == CheckType.OREDICT) {
            int toExtract = signature.amount;
            for (HashedStack stack : signature.correspondingStacks()) {
                toExtract -= extractByOre(stack, toExtract, signature.getOre(), true);
                if (toExtract <= 0) {
                    return;
                }
            }
            return;
        }
        extract(signature, true);
    }

    @Override
    public List<II_ItemStack> getContents() {
        return null;
    }
}
