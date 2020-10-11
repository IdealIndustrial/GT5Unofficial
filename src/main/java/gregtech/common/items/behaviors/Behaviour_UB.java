package gregtech.common.items.behaviors;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.items.GT_MetaBase_Item;
import gregtech.api.util.GT_Utility;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;

import java.util.List;

public class Behaviour_UB
        extends Behaviour_None {

    public List<String> getAdditionalToolTips(GT_MetaBase_Item aItem, List<String> aList, ItemStack aStack) {
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            aList.add(getV(" SOON") + getV("SOON "));
        }
        return aList;
    }

    public static String getV(String from) {
        long time = System.currentTimeMillis()/1000;
        char value = from.charAt((int) (time % from.length()));
        time = System.currentTimeMillis()/1000;
        switch ((int) (time % 2)) {
            case 0 :
                return EnumChatFormatting.DARK_GRAY.toString() + value;
            default:
                return EnumChatFormatting.GRAY.toString() + value;
        }

    }
}
