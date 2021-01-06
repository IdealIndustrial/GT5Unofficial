package gregtech;

import appeng.client.gui.implementations.GuiMEMonitorable;
import codechicken.nei.BookmarkPanel;
import extracells.gui.GuiFluidTerminal;
import extracells.tileentity.TileEntityFluidInterface;
import gloomyfolken.hooklib.asm.Hook;
import gloomyfolken.hooklib.asm.ReturnCondition;
import gregtech.api.items.GT_MetaGenerated_Item;
import gregtech.common.config.GT_DebugConfig;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import java.util.HashMap;
import java.util.Map;

public class HookContainer {

    //TODO: MOVE TO II_CORE AND SEPARATE CLASSES BY DEPENDENCIES

    //region:

    public static Map<Integer, Integer> subIDsMap = new HashMap<>();

    @Hook(injectOnExit = true, returnCondition = ReturnCondition.ALWAYS)
    public static ItemStack loadItemStackFromNBT(ItemStack defined, NBTTagCompound nbt, @Hook.ReturnValue ItemStack stack) {
        if (GT_DebugConfig.enableMapWrongMaterials && stack != null && stack.getItem() instanceof GT_MetaGenerated_Item && subIDsMap.containsKey(stack.getItemDamage() % 1000)) {
            fixMeta(stack, nbt);
        }
        return stack;
    }

    private static void fixMeta(ItemStack stack, NBTTagCompound nbt) {
        int meta = stack.getItemDamage();
        int rightSubID = subIDsMap.get(meta % 1000);
        meta -= meta % 1000;
        meta += rightSubID;
        stack.setItemDamage(meta);
        nbt.setShort("Damage", (short) meta);
    }

    @Hook(injectOnExit = true, returnCondition = ReturnCondition.ALWAYS)
    public static NBTTagCompound writeToNBT(ItemStack stack, NBTTagCompound p_77955_1_, @Hook.ReturnValue NBTTagCompound nbt) {
        if (GT_DebugConfig.enableMapWrongMaterials && stack != null && stack.getItem() instanceof GT_MetaGenerated_Item && subIDsMap.containsKey(stack.getItemDamage() % 1000)) {
           fixMeta(stack, nbt);
        }
        return nbt;
    }

    //region:FluidFaces

    @Hook(injectOnExit = true, isMandatory = true)
    public static void readFromNBT(TileEntityFluidInterface face, NBTTagCompound tag) {
        if (!GT_DebugConfig.fixEC2FluidInterfaces)
            return;
        for (int i = 0; i < face.tanks.length; i++) {
            if (!tag.hasKey("notShittyFluidID" + i))
                continue;
            Fluid fluid = FluidRegistry.getFluid(tag.getString("notShittyFluidID" + i));
            if (fluid == null)
                continue;
            int id = FluidRegistry.getFluidID(fluid);
            face.fluidFilter[i] = id;

        }
    }

    @Hook(injectOnExit = true, isMandatory = true)
    public static void writeToNBTWithoutExport(TileEntityFluidInterface face, NBTTagCompound tag) {
        if (!GT_DebugConfig.fixEC2FluidInterfaces)
            return;
        for (int i = 0; i < face.tanks.length; i++) {
            int id = face.fluidFilter[i];
            Fluid fluid = FluidRegistry.getFluid(id);
            if (fluid == null)
                continue;
            tag.setString("notShittyFluidID" + i, FluidRegistry.getFluidName(fluid));
        }
    }

    //endregion


    @Hook(injectOnExit = true, returnCondition = ReturnCondition.ALWAYS)
    public static int getNextX(BookmarkPanel panel, GuiContainer gui, @Hook.ReturnValue int ret){
        if (gui instanceof GuiMEMonitorable)
            ret-=16;
        if (gui instanceof GuiFluidTerminal)
            ret -= 27;
        return ret;
    }

    @Hook(injectOnExit = true, returnCondition = ReturnCondition.ALWAYS)
    public static int getWidth(BookmarkPanel panel, GuiContainer gui, @Hook.ReturnValue int ret){
        if (gui instanceof GuiMEMonitorable)
            ret-=16;
        if (gui instanceof GuiFluidTerminal)
            ret -= 27;
        return ret;
    }
}
