package idealindustrial.hooks;

import appeng.client.gui.implementations.GuiMEMonitorable;
import appeng.core.features.registries.MovableTileRegistry;
import codechicken.nei.BookmarkPanel;
import com.google.common.collect.HashBasedTable;
import extracells.gui.GuiFluidTerminal;
import gloomyfolken.hooklib.asm.Hook;
import gloomyfolken.hooklib.asm.ReturnCondition;
import gregtech.GT_Mod;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.common.blocks.GT_Block_Machines;
import jdk.nashorn.internal.runtime.regexp.joni.Config;
import mcp.mobius.mobiuscore.profiler.ProfilerSection;
import mcp.mobius.opis.data.holders.basetypes.CoordinatesBlock;
import mcp.mobius.opis.data.holders.newtypes.DataBlockTileEntityPerClass;
import mcp.mobius.opis.data.managers.TileEntityManager;
import mcp.mobius.opis.data.profilers.ProfilerTileEntityUpdate;
import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.common.DimensionManager;
import org.lwjgl.opengl.Display;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;

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
