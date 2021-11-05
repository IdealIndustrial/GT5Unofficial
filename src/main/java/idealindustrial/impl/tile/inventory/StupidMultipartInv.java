package idealindustrial.impl.tile.inventory;

import idealindustrial.api.tile.inventory.InternalInventory;
import idealindustrial.impl.item.stack.II_ItemStack;

import java.util.ArrayList;
import java.util.List;

//really stupid impl cause I have no motivation to do it properly for now
public class StupidMultipartInv extends ArrayRecipedInventory {

    List<InternalInventory> inventories;
    public StupidMultipartInv(List<InternalInventory> inventories) {
        super(new II_ItemStack[0]);
        this.inventories = inventories;
    }

    //call before checking recipe
    public void combine() {
        List<II_ItemStack> stacks = new ArrayList<>();
        for (InternalInventory inv : inventories) {
            for (II_ItemStack stack : inv) {
                if (stack != null) {
                    stacks.add(stack);
                }
            }
        }
        contents = stacks.toArray(new II_ItemStack[0]);
    }

    @Override
    public int insert(II_ItemStack is, boolean doInsert) {
        for (InternalInventory inv : inventories) {
            is.amount -= inv.insert(is.copy(), doInsert);
            if (is.amount <= 0) {
                return 0;
            }
        }
        return is.amount;
    }

    public void checkSizes() {
        for (InternalInventory inv : inventories) {
            if (inv instanceof ArrayRecipedInventory) {
                for (int i = 0; i < inv.size(); i++) {
                    if (inv.get(i) != null && inv.get(i).stackSize == 0) {
                        inv.set(i, null);
                    }
                }
            }
        }
    }
}
