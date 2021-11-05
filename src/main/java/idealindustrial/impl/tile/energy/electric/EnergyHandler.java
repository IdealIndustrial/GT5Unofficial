package idealindustrial.impl.tile.energy.electric;

import idealindustrial.api.tile.energy.electric.EUConsumer;
import idealindustrial.api.tile.energy.electric.EUProducer;
import idealindustrial.impl.recipe.MachineEnergyParams;
import idealindustrial.util.misc.II_NBTSerializable;
import net.minecraft.nbt.NBTTagCompound;

public abstract class EnergyHandler implements II_NBTSerializable {
    //ok todo remove class and make face

    protected long stored = 0L;

    public abstract EUProducer getProducer(int side);

    public abstract EUConsumer getConsumer(int side);

    public void onUpdate() {

    }

    public long fill(long energy, boolean doFill) {
        if (doFill) {
            stored += energy;
        }
        return energy;
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

    public MachineEnergyParams getParams() {
        return new MachineEnergyParams(0, 0, 0);
    }

    public long getStored() {
        return stored;
    }
}
