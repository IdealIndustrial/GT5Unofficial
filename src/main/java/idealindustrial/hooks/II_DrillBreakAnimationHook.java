package idealindustrial.hooks;

import gloomyfolken.hooklib.asm.Hook;
import idealindustrial.items.tools.ToolDrill;
import net.minecraft.server.management.ItemInWorldManager;
import net.minecraft.world.World;

public class II_DrillBreakAnimationHook {

    static ItemInWorldManager updatingManager = null;
    static boolean isInInvocation = false;

    @Hook(injectOnExit = false)
    public static void updateBlockRemoving(ItemInWorldManager mn) {
        if (!isInInvocation) {
            updatingManager = mn;
        }
    }

    @Hook(injectOnExit = true, targetMethod = "updateBlockRemoving")
    public static void updateBlockRemovingExit(ItemInWorldManager mn) {
        if (!isInInvocation) {
            updatingManager = null;
        }

    }

    @Hook
    public static void cancelDestroyingBlock(ItemInWorldManager mn, int p_73073_1_, int p_73073_2_, int p_73073_3_) {
        ToolDrill.cancelBlockBreaking(mn, mn.thisPlayerMP.getEntityId(), p_73073_1_, p_73073_2_, p_73073_3_);
    }

    @Hook(injectOnExit = true)
    public static void destroyBlockInWorldPartially(World world, int p_147443_1_, int p_147443_2_, int p_147443_3_, int p_147443_4_, int p_147443_5_) {
        if (isInInvocation || updatingManager == null) {
            return;
        }
        isInInvocation = true;
        ToolDrill.updateBlockBreaking(updatingManager, p_147443_1_, p_147443_2_, p_147443_3_, p_147443_4_, p_147443_5_);
        isInInvocation = false;

    }
}
