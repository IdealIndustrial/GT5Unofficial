package idealindustrial.hooks;

import appeng.core.features.registries.MovableTileRegistry;
import gloomyfolken.hooklib.asm.Hook;
import gloomyfolken.hooklib.asm.ReturnCondition;
import net.minecraft.tileentity.TileEntity;

public class II_AE2SpatialPatch {

    @Hook(returnCondition = ReturnCondition.ALWAYS, injectOnLine = 83)
    public static boolean askToMove(MovableTileRegistry registry, final TileEntity te) {
        te.invalidate();
        return true;
    }
}
