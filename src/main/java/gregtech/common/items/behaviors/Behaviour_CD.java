package gregtech.common.items.behaviors;

import gregtech.api.items.GT_MetaBase_Item;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;

import java.util.List;

public class Behaviour_CD
        extends Behaviour_None {
    public List<String> getAdditionalToolTips(GT_MetaBase_Item aItem, List<String> aList, ItemStack aStack) {
        String tString = GT_Utility.ItemNBT.getBookTitle(aStack);
        if (GT_Utility.isStringValid(tString)) {
            aList.add(tString);
        }
        tString = GT_Utility.ItemNBT.getBookAuthor(aStack);
        if (GT_Utility.isStringValid(tString)) {
            aList.add("by " + tString);
        }
        return aList;
    }
}
