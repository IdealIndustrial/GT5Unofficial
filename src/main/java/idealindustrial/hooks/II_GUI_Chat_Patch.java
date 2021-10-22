package idealindustrial.hooks;

import gloomyfolken.hooklib.asm.Hook;
import gloomyfolken.hooklib.asm.ReturnCondition;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;

public class II_GUI_Chat_Patch {

    public static boolean interrupt = false;

    @Hook(injectOnExit = false, returnCondition = ReturnCondition.ON_TRUE)
    public static boolean displayGuiScreen(Minecraft mc, GuiScreen p_147108_1_) {
        if (interrupt) {
            interrupt = false;
            return true;
        }
        return false;
    }
}
