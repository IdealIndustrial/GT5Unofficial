package idealindustrial.impl.oredict;

import idealindustrial.impl.autogen.material.II_Material;
import idealindustrial.impl.autogen.material.Prefixes;
import idealindustrial.impl.item.stack.HashedStack;
import idealindustrial.util.misc.ListUtil;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class OreInfo {
    String name;
    List<HashedStack> subItems;
    II_Material material;
    Prefixes prefix;

    public OreInfo(Prefixes prefix, II_Material material) {
        this.material = material;
        this.prefix = prefix;
        this.name = prefix.name() + material.name();
        this.subItems = new ArrayList<>();
    }

    public OreInfo(String name) {
        this.name = name;
        this.subItems = new ArrayList<>();
    }

    public boolean isSingleton() {
        return subItems.size() == 1; // or check special unification params
    }

    public HashedStack getUnified() {
        assert subItems.size() == 1; // or check special unification params
        return subItems.get(0);
    }

    public List<HashedStack> getSubItems() {
        return subItems;
    }

    public String getName() {
        return name;
    }


    void setMain(ItemStack stack) {
        HashedStack hashedStack = new HashedStack(stack);
        ListUtil.addOrMoveFirst(subItems, hashedStack);
    }

    OreInfo addStack(ItemStack stack) {
        HashedStack hashedStack = new HashedStack(stack);
        assert !subItems.contains(hashedStack);
        subItems.add(hashedStack);
        if (prefix != null && prefix.isUnifiable()) {
            OreDict.addInfo(hashedStack, this);
        }
        return this;
    }

    public HashedStack getMain() {
        return subItems.get(0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OreInfo info = (OreInfo) o;
        return name.equals(info.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public int size() {
        return subItems.size();
    }

    @Override
    public String toString() {
        return name + " "  + (material != null ? "Material: " + material.name() : "") + (prefix != null ? ", Prefix: " + prefix.name() : "");
    }
}
