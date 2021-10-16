package idealindustrial.util.world.underbedrock;

import net.minecraft.nbt.NBTTagCompound;

public interface UnderbedrockLayer<T> {
    
    //called when chunk at x, z loads
    void nbtLoad(int x, int z, NBTTagCompound nbt);

    //called when chunk at x, z saves
    void nbtSave(int x, int z, NBTTagCompound nbt);

    //called to obtain T situated at x, z
    T get(int x, int z);

    //called to flag T at x, z as modified and save it when chunk saves
    void flagAsModified(int x, int z);

    //called to set T at x, z, automatically sets it as modified
    void set(int x, int z, T t);
}
