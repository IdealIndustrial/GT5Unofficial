package idealindustrial.util.misc;

import net.minecraft.nbt.NBTTagCompound;

public interface NBTSerializer<T> {

    T load(NBTTagCompound nbt);

    NBTTagCompound save(T t, NBTTagCompound nbt);
}
