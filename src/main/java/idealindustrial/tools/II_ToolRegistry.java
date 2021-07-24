package idealindustrial.tools;

import gregtech.api.GregTech_API;
import gregtech.api.objects.GT_HashSet;
import gregtech.api.objects.GT_ItemStack;
import idealindustrial.reflection.II_ReflectionHelper;
import idealindustrial.tile.base.IToolClickableTile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class II_ToolRegistry {
    public static List<II_ToolDefinition> definitionList = new ArrayList<>();
    public static final GT_HashSet<GT_ItemStack> shiftClickSet = new GT_HashSet<>();

    public static void registerTool(GT_HashSet<GT_ItemStack> set, Method toInvoke, boolean fullArgs, boolean allowShiftClocking) {
        definitionList.add(new II_GT_ToolDef(set, toInvoke, fullArgs));
        if (allowShiftClocking) {
            shiftClickSet.addAll(set);
        }
    }

    public static boolean applyTool(IToolClickableTile tile, EntityPlayer player, ItemStack stack, int side, float hitX, float hitY, float hitZ) {
        if (stack == null) {
            return false;
        }
        GT_ItemStack gtItemStack = new GT_ItemStack(stack);
        for (II_ToolDefinition definition : definitionList) {
            if (definition.isTool(gtItemStack)) {
                Object[] args = definition.provideFullArgs() ? new Object[]{player, stack, side, hitX, hitY, hitZ} : new Object[]{player, stack, side};
                try {
                    return (boolean) definition.toInvoke().invoke(tile, args);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public static boolean allowShiftClickTile(ItemStack tool) {
        return shiftClickSet.contains(new GT_ItemStack(tool));
    }

    public static void initTools() throws NoSuchMethodException {
        Class<? extends IToolClickableTile> clazz = IToolClickableTile.class;
        registerTool(GregTech_API.sSoftHammerList, clazz.getDeclaredMethod("onSoftHammerClick", EntityPlayer.class, ItemStack.class, int.class), false, false);
    }
}

