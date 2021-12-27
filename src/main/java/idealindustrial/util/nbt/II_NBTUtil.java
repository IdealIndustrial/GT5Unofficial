package idealindustrial.util.nbt;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class II_NBTUtil {

    public static int getNBTField(ItemStack is, String field, int def) {
        NBTTagCompound nbt = is.getTagCompound();
        if (nbt == null || !nbt.hasKey(field)) {
            return def;
        }
        return nbt.getInteger(field);
    }

    public static void setNBTField(ItemStack is, String field, int value) {
        NBTTagCompound nbt = is.getTagCompound();
        nbt = nbt == null ? new NBTTagCompound() : nbt;
        nbt.setInteger(field, value);
        is.setTagCompound(nbt);
    }

    public static NBTTagCompound getTag(ItemStack is) {
        NBTTagCompound nbt = is.getTagCompound();
        return nbt == null ? new NBTTagCompound() : nbt;
    }

}
