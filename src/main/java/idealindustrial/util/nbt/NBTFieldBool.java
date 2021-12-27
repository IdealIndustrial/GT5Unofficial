package idealindustrial.util.nbt;

import idealindustrial.impl.item.stack.II_ItemStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class NBTFieldBool {

    String name;
    boolean defaultValue;

    public NBTFieldBool(String name, boolean defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
    }

    public NBTFieldBool(String name) {
        this.name = name;
        this.defaultValue = false;
    }

    public boolean get(ItemStack is) {
        NBTTagCompound nbt = is.getTagCompound();
        if (nbt == null || !nbt.hasKey(name)) {
            return defaultValue;
        }
        return nbt.getBoolean(name);
    }

    public void set(ItemStack is, boolean value) {
        NBTTagCompound nbt = is.getTagCompound();
        nbt = nbt == null ? new NBTTagCompound() : nbt;
        nbt.setBoolean(name, value);
        is.setTagCompound(nbt);
    }

    public boolean get(II_ItemStack is) {
        NBTTagCompound nbt = is.getTagCompound();
        if (nbt == null || !nbt.hasKey(name)) {
            return defaultValue;
        }
        return nbt.getBoolean(name);
    }

    public void set(II_ItemStack is, boolean value) {
        NBTTagCompound nbt = is.getTagCompound();
        nbt = nbt == null ? new NBTTagCompound() : (NBTTagCompound) nbt.copy();
        nbt.setBoolean(name, value);
        is.setTagCompound(nbt);
    }
}
