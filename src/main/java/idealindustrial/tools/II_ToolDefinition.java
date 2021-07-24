package idealindustrial.tools;

import gregtech.api.objects.GT_ItemStack;

import java.lang.reflect.Method;

//not interface cause we have one impl, idk if we need more
public interface II_ToolDefinition {

    boolean isTool(GT_ItemStack is);

    Method toInvoke();

    boolean provideFullArgs();

}
