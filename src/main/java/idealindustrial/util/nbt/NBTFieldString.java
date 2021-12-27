package idealindustrial.util.nbt;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class NBTFieldString {

    String name;
    String defaultValue;

    public NBTFieldString(String name, String defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
    }

    public NBTFieldString(String name) {
        this.name = name;
        this.defaultValue = "";
    }

    public String get(ItemStack is) {
        NBTTagCompound nbt = is.getTagCompound();
        if (nbt == null || !nbt.hasKey(name)) {
            return defaultValue;
        }
        return nbt.getString(name);
    }

    public void set(ItemStack is, String value) {
        NBTTagCompound nbt = is.getTagCompound();
        nbt = nbt == null ? new NBTTagCompound() : nbt;
        nbt.setString(name, value);
        is.setTagCompound(nbt);
    }
}
