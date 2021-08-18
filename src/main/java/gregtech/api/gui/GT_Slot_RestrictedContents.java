package gregtech.api.gui;

import gregtech.api.enums.ItemList;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class GT_Slot_RestrictedContents extends Slot {
    public IValidator mValidator;
    public GT_Slot_RestrictedContents(IInventory par1iInventory, int par2, int par3, int par4, IValidator aValidator) {
        super(par1iInventory, par2, par3, par4);
        mValidator = aValidator;
    }

    @Override
    public boolean isItemValid(ItemStack aStack) {
        return mValidator.isStackValid(aStack);
    }

    public interface IValidator {
        boolean isStackValid(ItemStack aStack);
    }
}