package idealindustrial.autogen.oredict;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import idealindustrial.autogen.material.II_Material;
import idealindustrial.autogen.material.Prefixes;
import idealindustrial.util.item.II_HashedStack;
import idealindustrial.util.item.II_ItemHelper;
import idealindustrial.util.item.II_ItemStack;
import idealindustrial.util.item.II_StackSignature;
import net.minecraft.item.ItemStack;

import java.io.PrintStream;
import java.util.*;

public class II_OreDict {
    //todo DONT FORGET ABOUT IDs FUCK UP EVENT
    static Map<String, II_OreInfo> name2info = new HashMap<>();
    static Map<II_HashedStack, II_OreInfo> stack2info = II_ItemHelper.queryMap(new HashMap<>()); // only for unification, so no list cause stack cannot be unified to different stacks =)
    static Multimap<II_HashedStack, II_OreInfo> anyStack2info = II_ItemHelper.queryMap(HashMultimap.create());

    protected static II_OreInfo emptyInfo = null;

    public static String register(Prefixes prefix, II_Material material, ItemStack stack) {
        II_OreInfo info = make(prefix, material);
        anyStack2info.put(new II_HashedStack(stack), info);
        info.addStack(stack);
        return info.name;
    }

    public static String registerMain(Prefixes prefix, II_Material material, ItemStack stack) {
        II_OreInfo info = make(prefix, material);
        info.setMain(stack);
        anyStack2info.put(new II_HashedStack(stack), info);
        return info.name;
    }

    public static void register(String ore, ItemStack stack) {
        II_OreInfo info = make(ore);
        anyStack2info.put(new II_HashedStack(stack), info);
        info.addStack(stack);
    }

    public static void add(II_OreInfo info) {
        assert !name2info.containsKey(info.name);
        name2info.put(info.name, info);
    }

    static II_OreInfo make(Prefixes prefix, II_Material material) {
        return name2info.computeIfAbsent(prefix.name() + material.name(), name -> new II_OreInfo(prefix, material));
    }

    static II_OreInfo make(String name) {
        return name2info.computeIfAbsent(name,II_OreInfo::new);
    }

    public static II_OreInfo get(Prefixes prefixes, II_Material material) {
        return get(prefixes.name() + material.name());
    }

    public static II_StackSignature get(Prefixes prefixes, II_Material material, int amount) {
        return new II_StackSignature(get(prefixes, material), amount);
    }

    public static II_ItemStack getMain(Prefixes prefixes, II_Material material, int amount) {
        II_HashedStack hashedStack = get(prefixes, material).getMain();
        return new II_ItemStack(hashedStack.getItem(), hashedStack.getDamage(), amount);
    }

    public static II_OreInfo get(String name) {
        II_OreInfo info = name2info.get(name);
        if (info == null) {
            return emptyInfo;
        }
        return name2info.get(name);
    }

    public static Collection<II_OreInfo> getInfo(II_HashedStack stack) {
        return anyStack2info.get(stack);
    }

    public static II_OreInfo getUnified(II_HashedStack stack) {
        return stack2info.get(stack);
    }


    static void addInfo(II_HashedStack stack, II_OreInfo info) {
        assert !stack2info.containsKey(stack) && info.prefix.isUnifiable();
        stack2info.put(stack, info);
    }

    public static void printAll(PrintStream stream) {
       name2info.forEach((key, value) -> {
           stream.println(key + " -> " + value);
       });
    }


}
