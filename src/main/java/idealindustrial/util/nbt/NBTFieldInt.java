package idealindustrial.util.nbt;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class NBTFieldInt {

    String name;
    int defaultValue;

    public NBTFieldInt(String name, int defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
    }

    public NBTFieldInt(String name) {
        this.name = name;
        this.defaultValue = 0;
    }

    public int get(ItemStack is) {
        NBTTagCompound nbt = is.getTagCompound();
        if (nbt == null || !nbt.hasKey(name)) {
            return defaultValue;
        }
        return nbt.getInteger(name);
    }

    public void set(ItemStack is, int value) {
        NBTTagCompound nbt = is.getTagCompound();
        nbt = nbt == null ? new NBTTagCompound() : nbt;
        nbt.setInteger(name, value);
        is.setTagCompound(nbt);
    }
}
