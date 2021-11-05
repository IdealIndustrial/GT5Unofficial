package idealindustrial.impl.tile.energy.electric;

import idealindustrial.api.tile.energy.electric.EUConsumer;
import idealindustrial.api.tile.energy.electric.EUProducer;
import net.minecraft.nbt.NBTTagCompound;

public class EmptyEnergyHandler extends EnergyHandler {

    public static final EmptyEnergyHandler INSTANCE = new EmptyEnergyHandler();
    private EmptyEnergyHandler() {

    }

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
