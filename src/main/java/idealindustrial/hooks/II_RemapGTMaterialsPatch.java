package idealindustrial.hooks;

import gloomyfolken.hooklib.asm.Hook;
import gloomyfolken.hooklib.asm.ReturnCondition;
import gregtech.api.items.GT_MetaGenerated_Item;
import gregtech.common.config.GT_DebugConfig;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.HashMap;
import java.util.Map;

public class II_RemapGTMaterialsPatch {
    public static Map<Integer, Integer> subIDsMap = new HashMap<>();

    @Hook(injectOnExit = true, returnCondition = ReturnCondition.ALWAYS)
    public static ItemStack loadItemStackFromNBT(ItemStack defined, NBTTagCompound nbt, @Hook.ReturnValue ItemStack stack) {
        if (GT_DebugConfig.enableMapWrongMaterials && stack != null && stack.getItem() instanceof GT_MetaGenerated_Item && subIDsMap.containsKey(stack.getItemDamage() % 1000)) {
            fixMeta(stack, nbt);
        }
        return stack;
    }

    private static void fixMeta(ItemStack stack, NBTTagCompound nbt) {
        int meta = stack.getItemDamage();
        int rightSubID = subIDsMap.get(meta % 1000);
        meta -= meta % 1000;
        meta += rightSubID;
        stack.setItemDamage(meta);
        nbt.setShort("Damage", (short) meta);
    }

    @Hook(injectOnExit = true, returnCondition = ReturnCondition.ALWAYS)
    public static NBTTagCompound writeToNBT(ItemStack stack, NBTTagCompound p_77955_1_, @Hook.ReturnValue NBTTagCompound nbt) {
        if (GT_DebugConfig.enableMapWrongMaterials && stack != null && stack.getItem() instanceof GT_MetaGenerated_Item && subIDsMap.containsKey(stack.getItemDamage() % 1000)) {
            fixMeta(stack, nbt);
        }
        return nbt;
    }
}
