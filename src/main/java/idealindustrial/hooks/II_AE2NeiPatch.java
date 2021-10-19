package idealindustrial.hooks;

import appeng.client.gui.implementations.GuiMEMonitorable;
import appeng.core.features.registries.MovableTileRegistry;
import codechicken.nei.BookmarkPanel;
import com.google.common.collect.HashBasedTable;
import extracells.gui.GuiFluidTerminal;
import gloomyfolken.hooklib.asm.Hook;
import gloomyfolken.hooklib.asm.ReturnCondition;
import net.minecraft.client.gui.inventory.GuiContainer;

public class II_AE2NeiPatch {

    @Hook(injectOnExit = true, returnCondition = ReturnCondition.ALWAYS)
    public static int getNextX(BookmarkPanel panel, GuiContainer gui, @Hook.ReturnValue int ret) {
        if (gui instanceof GuiMEMonitorable)
            ret -= 16;
        if (gui instanceof GuiFluidTerminal)
            ret -= 27;
        return ret;
    }

    @Hook(injectOnExit = true, returnCondition = ReturnCondition.ALWAYS)
    public static int getWidth(BookmarkPanel panel, GuiContainer gui, @Hook.ReturnValue int ret) {
        if (gui instanceof GuiMEMonitorable)
            ret -= 16;
        if (gui instanceof GuiFluidTerminal)
            ret -= 27;
        return ret;
    }

}
