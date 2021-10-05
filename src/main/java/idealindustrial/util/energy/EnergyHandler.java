package idealindustrial.util.energy;

import idealindustrial.util.misc.II_NBTSerializable;
import net.minecraft.nbt.NBTTagCompound;

public abstract class EnergyHandler implements II_NBTSerializable {
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

    public void fill(long energy) {
        stored += energy;
    }

    //ret: drained
    public long drain(long energy, boolean doDrain) {
        long drained = Math.min(energy, stored);
        if (doDrain) {
            stored -= drained;
        }
        return drained;
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
