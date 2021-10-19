package idealindustrial.tools;

import idealindustrial.loader.ItemsLoader;
import idealindustrial.tile.interfaces.IToolClickableTile;
import idealindustrial.util.item.HashedStack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ToolRegistry {
    public static List<ToolDefinition> definitionList = new ArrayList<>();
    public static final Set<HashedStack> shiftClickSet = new HashSet<>();

    public static void registerTool(HashedStack stack, Method toInvoke, boolean fullArgs, boolean allowShiftClocking) {
        Set<HashedStack> set = new HashSet<>();
        set.add(stack);
        definitionList.add(new ToolDefImpl(toInvoke, set, fullArgs));
        if (allowShiftClocking) {
            shiftClickSet.addAll(set);
        }
    }

    public static boolean applyTool(IToolClickableTile tile, EntityPlayer player, ItemStack stack, int side, float hitX, float hitY, float hitZ) {
        if (stack == null) {
            return false;
        }
        HashedStack gtItemStack = new HashedStack(stack);
        for (ToolDefinition definition : definitionList) {
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
        return shiftClickSet.contains(new HashedStack(tool));
    }

    public static void initTools() throws NoSuchMethodException {
        Class<? extends IToolClickableTile> clazz = IToolClickableTile.class;
        registerTool(new HashedStack(ItemsLoader.mallet), clazz.getDeclaredMethod("onSoftHammerClick", EntityPlayer.class, ItemStack.class, int.class), false, false);
        registerTool(new HashedStack(ItemsLoader.wrench), clazz.getDeclaredMethod("onWrenchClick", EntityPlayer.class, ItemStack.class, int.class, float.class, float.class, float.class), true, true);
    }
}

