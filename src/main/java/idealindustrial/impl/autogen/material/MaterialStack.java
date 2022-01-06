package idealindustrial.impl.autogen.material;

import idealindustrial.impl.item.stack.II_StackSignature;
import idealindustrial.impl.oredict.OreDict;

public class MaterialStack {
    public II_Material material;

    public int amount;

    public MaterialStack(II_Material material, int amount) {
        this.material = material;
        this.amount = amount;
    }

    public II_StackSignature forPrefix(Prefixes prefix) {
        return OreDict.get(prefix, material, amount);
    }
}
