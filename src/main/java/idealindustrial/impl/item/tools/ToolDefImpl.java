package idealindustrial.impl.item.tools;

import idealindustrial.api.items.ToolDefinition;
import idealindustrial.impl.item.stack.HashedStack;

import java.lang.reflect.Method;
import java.util.Set;

public class ToolDefImpl implements ToolDefinition {

    Method toInvoke;
    Set<HashedStack> set;
    boolean fullArgs;

    public ToolDefImpl(Method toInvoke, Set<HashedStack> set, boolean fullArgs) {
        this.toInvoke = toInvoke;
        this.set = set;
        this.fullArgs = fullArgs;
    }

    @Override
    public boolean isTool(HashedStack is) {
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
