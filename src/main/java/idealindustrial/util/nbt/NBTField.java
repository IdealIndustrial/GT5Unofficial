package idealindustrial.util.nbt;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class NBTField<T> {

    T defaultValue;
    Serializer<T> serializer;
    Deserializer<T> deserializer;
    String[] requiredFields;

    public NBTField(T defaultValue, Serializer<T> serializer, Deserializer<T> deserializer, String... requiredFields) {
        this.defaultValue = defaultValue;
        this.serializer = serializer;
        this.deserializer = deserializer;
        this.requiredFields = requiredFields;
    }

    public void set(ItemStack is, T value) {
        NBTTagCompound nbt = II_NBTUtil.getTag(is);
        if (value != null) {
            serializer.save(nbt, value);
        }
        else {
            for (String s : requiredFields) {
                nbt.removeTag(s);
            }
        }
        is.setTagCompound(nbt);
    }

    public T get(ItemStack is) {
        NBTTagCompound nbt = II_NBTUtil.getTag(is);
        for (String s : requiredFields) {
            if (!nbt.hasKey(s)) {
                return defaultValue;
            }
        }
        return deserializer.load(nbt);
    }





    public interface Serializer<E> {
        void save(NBTTagCompound nbt, E t);
    }

    public interface Deserializer<E> {
        E load(NBTTagCompound nbt);
    }

}
