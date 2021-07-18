package idealindustrial.util.misc;

import net.minecraft.nbt.NBTTagCompound;

public interface II_NBTSerializable {

    void nbtSave(NBTTagCompound tag, String prefix);

    void nbtLoad(NBTTagCompound tag, String prefix);
}
