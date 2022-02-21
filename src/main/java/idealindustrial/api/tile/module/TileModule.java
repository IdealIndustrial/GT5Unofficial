package idealindustrial.api.tile.module;

import net.minecraft.nbt.NBTTagCompound;

public interface TileModule {

    void onPostTick(long timer, boolean serverSide);

    void saveToNBT(String prefix, NBTTagCompound nbt);

    void loadFromNBT(String prefix, NBTTagCompound nbt);

    default void useEnergy(long voltage) {

    }

    default void useKineticEnergy(int speed, double satisfaction) {

    }

    default void receiveEvent(int id, int value) {
    }
}
