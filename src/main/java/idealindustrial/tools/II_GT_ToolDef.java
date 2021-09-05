package idealindustrial.tools;

import gregtech.api.objects.GT_HashSet;
import gregtech.api.objects.GT_ItemStack;

import java.lang.reflect.Method;

public class II_GT_ToolDef implements ToolDefinition {
    protected GT_HashSet<GT_ItemStack> set;
    protected Method toInvoke;
    protected boolean fullArgs;

    public II_GT_ToolDef(GT_HashSet<GT_ItemStack> set, Method toInvoke, boolean fullArgs) {
        this.set = set;
        this.toInvoke = toInvoke;
        this.fullArgs = fullArgs;
    }

    @Override
    public boolean isTool(GT_ItemStack is) {
        return set.contains(is);
    }

    @Override
    public Method toInvoke() {
        return toInvoke;
    }

    @Override
    public boolean provideFullArgs() {
        return fullArgs;
    }
}
