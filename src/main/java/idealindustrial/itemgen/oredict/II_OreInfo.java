package idealindustrial.itemgen.oredict;

import idealindustrial.itemgen.material.II_Material;
import idealindustrial.itemgen.material.Prefixes;
import idealindustrial.util.item.II_HashedStack;
import idealindustrial.util.misc.ListUtil;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class II_OreInfo {
    String name;
    List<II_HashedStack> subItems;
    II_Material material;
    Prefixes prefix;

    public II_OreInfo(Prefixes prefix, II_Material material) {
        this.material = material;
        this.prefix = prefix;
        this.name = prefix.name() + material.name();
        this.subItems = new ArrayList<>();
    }

    public II_OreInfo(String name) {
        this.name = name;
        this.subItems = new ArrayList<>();
    }

    public boolean isSingleton() {
        return subItems.size() == 1; // or check special unification params (WIP)
    }

    public II_HashedStack getUnified() {
        assert subItems.size() == 1; // or check special unification params (WIP)
        return subItems.get(0);
    }

    public List<II_HashedStack> getSubItems() {
        return subItems;
    }

    public String getName() {
        return name;
    }


    void setMain(ItemStack stack) {
        II_HashedStack hashedStack = new II_HashedStack(stack);
        ListUtil.addOrMoveFirst(subItems, hashedStack);
    }

    void addStack(ItemStack stack) {
        II_HashedStack hashedStack = new II_HashedStack(stack);
        assert !subItems.contains(hashedStack);
        subItems.add(hashedStack);
        if (prefix != null && prefix.isUnifiable()) {
            II_OreDict.addInfo(hashedStack, this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        II_OreInfo info = (II_OreInfo) o;
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
