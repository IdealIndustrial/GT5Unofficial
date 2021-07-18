package idealindustrial.itemgen.implementation;

import idealindustrial.itemgen.items.II_MetaGeneratedItem;
import idealindustrial.itemgen.material.II_Materials;
import idealindustrial.itemgen.material.Prefixes;

public class II_MetaGeneratedItem_1 extends II_MetaGeneratedItem {
    public static final Prefixes[] prefixes = new Prefixes[]{Prefixes.dust, Prefixes.dustSmall, Prefixes.dustTiny};
    public II_MetaGeneratedItem_1() {
        super("metagenerated.1", II_Materials.materialsK1, prefixes);
    }

}
