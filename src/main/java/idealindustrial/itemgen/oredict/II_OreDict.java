package idealindustrial.itemgen.oredict;

import idealindustrial.itemgen.material.II_Material;
import idealindustrial.itemgen.material.Prefixes;
import idealindustrial.util.item.II_HashedStack;
import idealindustrial.util.item.II_ItemStack;
import net.minecraft.item.ItemStack;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class II_OreDict {
    //todo DONT FORGET ABOUT IDs FUCK UP EVENT
    Map<String, II_OreInfo> name2info = new HashMap<>();
    Map<II_HashedStack, II_OreInfo> stack2info = new HashMap<>(); // only for unification, so no list cause stack cannot be unified to different stacks =)

    public String register(Prefixes prefix, II_Material material, ItemStack stack) {
        II_OreInfo info = make(prefix, material);
        info.addStack(stack);
        return info.name;
    }

    public String registerMain(Prefixes prefix, II_Material material, ItemStack stack) {
        II_OreInfo info = make(prefix, material);
        info.setMain(stack);
        return info.name;
    }

    public void register(String ore, ItemStack stack) {
        II_OreInfo info = make(ore);
        info.addStack(stack);
    }

    public void add(II_OreInfo info) {
        assert !name2info.containsKey(info.name);
        name2info.put(info.name, info);
    }

    II_OreInfo make(Prefixes prefix, II_Material material) {
        return name2info.computeIfAbsent(prefix.name() + material.name(), name -> new II_OreInfo(prefix, material));
    }

    II_OreInfo make(String name) {
        return name2info.computeIfAbsent(name,II_OreInfo::new);
    }

    public II_OreInfo get(Prefixes prefixes, II_Material material) {
        return get(prefixes.name() + material.name());
    }

    public II_OreInfo get(String name) {
        return name2info.get(name);
    }


    private static II_OreDict INSTANCE = null;
    public static II_OreDict getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new II_OreDict();
        }
        return INSTANCE;
    }

    void addInfo(II_HashedStack stack, II_OreInfo info) {
        assert !stack2info.containsKey(stack) && info.prefix.isUnifiable();//also assert if prefix can be unified
        stack2info.put(stack, info);
    }

    public void printAll(PrintStream stream) {
       name2info.forEach((key, value) -> {
           stream.println(key + " -> " + value);
       });
    }


}