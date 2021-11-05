package idealindustrial.api.items;

import idealindustrial.impl.item.stack.HashedStack;

import java.lang.reflect.Method;

//not interface cause we have one impl, idk if we need more
public interface ToolDefinition {

    boolean isTool(HashedStack is);

    Method toInvoke();

    boolean provideFullArgs();

}
