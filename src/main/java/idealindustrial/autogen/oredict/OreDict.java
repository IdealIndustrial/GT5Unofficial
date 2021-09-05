package idealindustrial.autogen.oredict;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import idealindustrial.autogen.material.II_Material;
import idealindustrial.autogen.material.Prefixes;
import idealindustrial.util.item.HashedStack;
import idealindustrial.util.item.ItemHelper;
import idealindustrial.util.item.II_ItemStack;
import idealindustrial.util.item.II_StackSignature;
import net.minecraft.item.ItemStack;

import java.io.PrintStream;
import java.util.*;

public class OreDict {
    //todo DONT FORGET ABOUT IDs FUCK UP EVENT
    static Map<String, OreInfo> name2info = new HashMap<>();
    static Map<HashedStack, OreInfo> stack2info = ItemHelper.queryMap(new HashMap<>()); // only for unification, so no list cause stack cannot be unified to different stacks =)
    static Multimap<HashedStack, OreInfo> anyStack2info = ItemHelper.queryMap(HashMultimap.create());

    protected static OreInfo emptyInfo = null;

    public static String register(Prefixes prefix, II_Material material, ItemStack stack) {
        OreInfo info = make(prefix, material);
        anyStack2info.put(new HashedStack(stack), info);
        info.addStack(stack);
        return info.name;
    }

    public static String registerMain(Prefixes prefix, II_Material material, ItemStack stack) {
        OreInfo info = make(prefix, material);
        info.setMain(stack);
        anyStack2info.put(new HashedStack(stack), info);
        return info.name;
    }

    public static void register(String ore, ItemStack stack) {
        OreInfo info = make(ore);
        anyStack2info.put(new HashedStack(stack), info);
        info.addStack(stack);
    }

    public static void add(OreInfo info) {
        assert !name2info.containsKey(info.name);
        name2info.put(info.name, info);
    }

    static OreInfo make(Prefixes prefix, II_Material material) {
        return name2info.computeIfAbsent(prefix.name() + material.name(), name -> new OreInfo(prefix, material));
    }

    static OreInfo make(String name) {
        return name2info.computeIfAbsent(name, OreInfo::new);
    }

    public static OreInfo get(Prefixes prefixes, II_Material material) {
        return get(prefixes.name() + material.name());
    }

    public static II_StackSignature get(Prefixes prefixes, II_Material material, int amount) {
        return new II_StackSignature(get(prefixes, material), amount);
    }

    public static II_ItemStack getMain(Prefixes prefixes, II_Material material, int amount) {
        HashedStack hashedStack = get(prefixes, material).getMain();
        return new II_ItemStack(hashedStack.getItem(), hashedStack.getDamage(), amount);
    }

    public static OreInfo get(String name) {
        OreInfo info = name2info.get(name);
        if (info == null) {
            return emptyInfo;
        }
        return name2info.get(name);
    }

    public static Collection<OreInfo> getInfo(HashedStack stack) {
        return anyStack2info.get(stack);
    }

    public static OreInfo getUnified(HashedStack stack) {
        return stack2info.get(stack);
    }


    static void addInfo(HashedStack stack, OreInfo info) {
        assert !stack2info.containsKey(stack) && info.prefix.isUnifiable();
        stack2info.put(stack, info);
    }

    public static void printAll(PrintStream stream) {
       name2info.forEach((key, value) -> {
           stream.println(key + " -> " + value);
       });
    }


}
