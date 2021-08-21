package idealindustrial.util.energy;

import net.minecraft.nbt.NBTTagCompound;

public class II_EmptyEnergyHandler extends II_EnergyHandler {
    @Override
    public EUProducer getProducer(int side) {
        return null;
    }

    @Override
    public EUConsumer getConsumer(int side) {
        return null;
    }

    @Override
    public boolean isAlmostFull() {
        return false;
    }

    @Override
    public void nbtSave(NBTTagCompound tag, String prefix) {

    }

    @Override
    public void nbtLoad(NBTTagCompound tag, String prefix) {

    }
}
