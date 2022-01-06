package idealindustrial.impl.recipe.template;

import idealindustrial.impl.autogen.material.II_Material;
import idealindustrial.impl.autogen.material.Prefixes;
import idealindustrial.impl.item.stack.II_StackSignature;
import opisapache.math3.util.Pair;

public interface DefinedItem {

    Pair<II_StackSignature, Integer> instantiate(II_Material material, Prefixes prefixes);


}
