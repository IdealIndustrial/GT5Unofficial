package gregtech.common.items.behaviors;

import cpw.mods.fml.common.FMLCommonHandler;
import gregtech.api.items.GT_MetaBase_Item;
import gregtech.api.util.GT_LanguageManager;
import gregtech.api.util.GT_Utility;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;
import java.util.Objects;

public class Behaviour_Punched_Card
        extends Behaviour_None {
    public List<String> getAdditionalToolTips(GT_MetaBase_Item aItem, List<String> aList, ItemStack aStack) {
        NBTTagCompound nbt = GT_Utility.ItemNBT.getNBT(aStack);
        String tString = "";
        boolean isEn = Objects.equals(FMLCommonHandler.instance().getCurrentLanguage(), "en_US");
        if(nbt.getBoolean("oresNotFound")) {
            tString = GT_LanguageManager.getTranslation("gt.label.no_ores_found");
        } else if(GT_Utility.isStringValid(nbt.getString("oreName"))){
            String oreName = nbt.getString("oreName");
            String oreFoundLabel = GT_LanguageManager.getTranslation("gt.label.found_ore");
            tString = oreFoundLabel + ": " + (isEn ? nbt.getString("enOreName") : GT_LanguageManager.getTranslation(oreName));
        }
        if(!Objects.equals(tString, "")){
            aList.add(tString);
        }
        return aList;
    }
}
