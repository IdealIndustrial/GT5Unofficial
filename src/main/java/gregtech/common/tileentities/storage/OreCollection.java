package gregtech.common.tileentities.storage;

import gregtech.api.util.GT_LanguageManager;
import gregtech.common.blocks.GT_Item_Ores;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumChatFormatting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class OreCollection implements Comparator<OreCollection> {
    public OreCollection(){};
    public OreCollection(String oreName, int oreCount, String oreFlow){
        this.oreName = oreName;
        this.oreCount = oreCount;
        this.oreFlow = oreFlow;
    }
    String oreName;
    String oreFlow;
    int oreCount;
    public int compare(OreCollection a, OreCollection b)
    {
        return b.oreCount - a.oreCount;
    }

    public static String getText(String unlocText){
        StringBuffer sb = new StringBuffer();
        // sb.append();
        if("underBrOresMainTitle" == unlocText) {
            sb.append(EnumChatFormatting.WHITE);
            sb.append("     ");
            sb.append(GT_LanguageManager.getTranslation("underBrOresMainTitle1"));
            sb.append("\n     ");
            sb.append(GT_LanguageManager.getTranslation("underBrOresMainTitle2"));
            sb.append(EnumChatFormatting.RESET);
            return sb.toString();
        }
        if("warningLabel" == unlocText) {
            sb.append(EnumChatFormatting.RED);
            sb.append("     ");
            sb.append(GT_LanguageManager.getTranslation("warningLabel"));
            sb.append("! ");
            sb.append(EnumChatFormatting.RESET);
            return sb.toString();
        }
        if("underBrWarningLabel" == unlocText){
            return GT_LanguageManager.getTranslation("DataOrb.Miner.Warning");
        }
        if("minedOresCountLabel" == unlocText){
            return GT_LanguageManager.getTranslation("minedOresCount");
        }
        if("underBrOresFlowLabel2" == unlocText){
            sb.append(EnumChatFormatting.DARK_GRAY);
            sb.append("   ");
            sb.append(GT_LanguageManager.getTranslation("DataOrb.Miner.ubFlow"));
            sb.append("\n   ");
            sb.append(GT_LanguageManager.getTranslation("DataOrb.Miner.onMv"));
            sb.append("\n   ");
            sb.append(GT_LanguageManager.getTranslation("DataOrb.Miner.oresEachHour"));
            sb.append(':');
            sb.append(EnumChatFormatting.RESET);
            return sb.toString();
        }
        if("minedOreTypesCountLabel" == unlocText){
            return GT_LanguageManager.getTranslation("minedOreTypesCount");
        }
        return unlocText;
    }
    public static NBTTagCompound getRawOresData(ArrayList<OreCollection> aList){
        NBTTagCompound oreTag = new NBTTagCompound();
        for (int i = 0; i < aList.size(); i++){
            oreTag.setString("oreName"+i, aList.get(i).oreName);
            oreTag.setInteger("oreCount"+i, aList.get(i).oreCount);
            oreTag.setString("oreFlow"+i, aList.get(i).oreFlow);
        }
        return oreTag;
    }
    public static ArrayList<OreCollection> getCollectionFromNbt(NBTTagCompound nbt){
        ArrayList<OreCollection> aList = new ArrayList<>();
        int oreTypesCount = nbt.getInteger("minerOreTypesCount");
        NBTTagCompound rawOresData = nbt.getCompoundTag("rawOresData");
        for (int i = 0; i < oreTypesCount; i++){
            OreCollection oreCol = new OreCollection(
                    rawOresData.getString("oreName"+i),
                    rawOresData.getInteger("oreCount"+i),
                    rawOresData.getString("oreFlow"+i)
            );
            aList.add(oreCol);
        }
        return aList;
    }
    public static String getTranslatedOreName(String unlockOreName){
        if(unlockOreName.indexOf("gt.blockores.") == 0) {
            String gtOreId = unlockOreName.replaceAll(".+\\.","");
            return new ItemStack(GT_Item_Ores.getItemById(/*576*/1095), 1, Integer.valueOf(gtOreId)).getDisplayName();
        } else {
            return GT_LanguageManager.getTranslation(unlockOreName);
        }
    }
    public static NBTTagList getTooltipLines(ArrayList<OreCollection> aList, int totalBlocksCount, int oreTypesCount){
        StringBuffer sBuf = new StringBuffer();
        sBuf.append(getText("underBrOresMainTitle"));
        sBuf.append("\n\n");

        sBuf.append(getText("warningLabel"));
        sBuf.append(getText("underBrWarningLabel"));
        sBuf.append("\n\n");
        sBuf.append("     ");
        sBuf.append(EnumChatFormatting.DARK_GREEN + getText("minedOresCountLabel"));
        sBuf.append(" ");
        sBuf.append(EnumChatFormatting.AQUA);
        sBuf.append(totalBlocksCount);
        sBuf.append("\n\n     ");
        sBuf.append(EnumChatFormatting.DARK_GREEN);
        sBuf.append(getText("minedOreTypesCountLabel"));
        sBuf.append(" ");
        sBuf.append(EnumChatFormatting.AQUA);
        sBuf.append(oreTypesCount);
        sBuf.append(EnumChatFormatting.RESET);

        ArrayList<String> pages = new ArrayList<>();
        pages.add(sBuf.toString());
        NBTTagList tTagList = new NBTTagList();
        Collections.sort(aList, new OreCollection());
        StringBuffer lineSb = new StringBuffer();
        for (int i = 0; i < aList.size(); i++){
            String tempLine = EnumChatFormatting.GRAY + (getTranslatedOreName(aList.get(i).oreName)/*GT_LanguageManager.getTranslation(aList.get(i).oreName)*/) + " " + EnumChatFormatting.AQUA + aList.get(i).oreFlow;
            if(lineSb.length() == 0) {
                lineSb.append(getText("underBrOresFlowLabel2"));
                lineSb.append("\n\n");
                lineSb.append("  ");
                lineSb.append(tempLine);
            }
            else {
                if(i % 7 == 6) {
                    lineSb.append("\n\n  ");
                    lineSb.append(tempLine);
                    pages.add(lineSb.toString());
                    lineSb = new StringBuffer();
                } else {
                    lineSb.append("\n\n  ");
                    lineSb.append(tempLine);
                    if(i == aList.size()-1) {
                        pages.add(lineSb.toString());
                    }
                }
            }
        }
        int idx = 0;
        for (String str : pages){
            sBuf = new StringBuffer();
            sBuf.append(EnumChatFormatting.GRAY);
            sBuf.append("                         ");
            sBuf.append((idx+1));
            sBuf.append(" / ");
            sBuf.append(pages.size());
            sBuf.append(EnumChatFormatting.RESET);
            sBuf.append("\n\n");
            sBuf.append(str);
            tTagList.appendTag(new NBTTagString(sBuf.toString()));
            idx++;
        }
        return tTagList;
    }

}
