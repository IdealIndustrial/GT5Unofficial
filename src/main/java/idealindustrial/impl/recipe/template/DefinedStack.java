package idealindustrial.impl.recipe.template;

import idealindustrial.impl.autogen.material.II_Material;
import idealindustrial.impl.autogen.material.Prefixes;
import idealindustrial.impl.item.stack.II_StackSignature;

public interface DefinedStack {

    void lock();

    void unlock();

    void multiply(int times);

    int generate(II_Material material, Prefixes prefixes);

    II_StackSignature instantiate();

    InstantiableStack copy();

    boolean shouldBeGenerated();
}
