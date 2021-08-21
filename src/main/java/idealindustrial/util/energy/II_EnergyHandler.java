package idealindustrial.util.energy;

import idealindustrial.tile.interfaces.base.II_BaseMachineTile;
import idealindustrial.util.misc.II_NBTSerializable;
import net.minecraft.nbt.NBTTagCompound;

public abstract class II_EnergyHandler implements II_NBTSerializable {
    //class with public field... todo: check about inlining ( may be replace with interface)

    public long stored = 0L;

    public abstract EUProducer getProducer(int side);

    public abstract EUConsumer getConsumer(int side);

    public void onConfigurationChanged() {

    }

    public void onUpdate() {

    }

    public void onRemoval() {

    }

    public boolean drain(long energy, boolean doDrain) {
        if (stored >= energy) {
            if (doDrain) {
                stored -= energy;
            }
            return true;
        }
        return false;
    }

    public abstract boolean isAlmostFull();

    @Override
    public void nbtLoad(NBTTagCompound tag, String prefix) {
        stored = tag.getLong(prefix + "stored");
    }

    @Override
    public void nbtSave(NBTTagCompound tag, String prefix) {
        tag.setLong(prefix + "stored", stored);
    }
}
