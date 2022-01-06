package idealindustrial.impl.recipe.template;

import idealindustrial.impl.autogen.material.II_Material;
import idealindustrial.impl.autogen.material.Prefixes;
import idealindustrial.impl.item.stack.II_StackSignature;
import opisapache.math3.util.Pair;

public class InstantiableStack implements DefinedStack {

    public DefinedItem item;
    public int amount = 1;

    private II_StackSignature cache;
    private boolean multiplyLock = false;

    public InstantiableStack(DefinedItem item, int amount) {
        this.item = item;
        this.amount = amount;
    }

    @Override
    public void lock() {
        multiplyLock = true;
    }

    @Override
    public void unlock() {
        multiplyLock = false;
    }

    @Override
    public void multiply(int times) {
        if (multiplyLock) {
            return;
        }
        amount *= times;
    }

    @Override
    public int generate(II_Material material, Prefixes prefixes) {
        Pair<II_StackSignature, Integer> value = item.instantiate(material, prefixes);
        cache = value.getFirst();
        return value.getSecond();
    }

    @Override
    public II_StackSignature instantiate() {
        assert cache != null;
        cache.amount *= amount;
        return cache;
    }


    @Override
    public InstantiableStack copy() {
        return new InstantiableStack(item, amount);
    }

    @Override
    public boolean shouldBeGenerated() {
        return true;
    }
}
