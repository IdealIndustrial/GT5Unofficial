package idealindustrial.util.energy.electric;

import idealindustrial.tile.IOType;
import idealindustrial.tile.interfaces.host.HostMachineTile;
import net.minecraft.nbt.NBTTagCompound;

public class InputEnergyHandler extends BasicEnergyHandler {

    protected EUConsumer universalConsumer;
    protected boolean[] allowedSides;

    public InputEnergyHandler(HostMachineTile baseTile, long minEnergyAmount, long maxCapacity, long voltageIn, long amperageIn) {
        super(baseTile, minEnergyAmount, maxCapacity, voltageIn, amperageIn, 0, 0);
        this.allowedSides = baseTile.getIO(IOType.ENERGY);
        this.universalConsumer = new II_DefaultEUConsumer(this, -1);
        baseTile.onIOConfigurationChanged(this::onConfigurationChanged);
    }

    @Override
    public EUProducer getProducer(int side) {
        return null;
    }

    @Override
    public EUConsumer getConsumer(int side) {
        if (allowedSides[side + 6]) {
            return universalConsumer;
        }
        return null;
    }

    @Override
    public void nbtSave(NBTTagCompound tag, String prefix) {
        super.nbtSave(tag, prefix);
    }

    @Override
    public void nbtLoad(NBTTagCompound tag, String prefix) {
        super.nbtLoad(tag, prefix);
    }

    public void onConfigurationChanged(IOType type) {
        if (type.is(IOType.ENERGY)) {
            allowedSides = baseTile.getIO(IOType.ENERGY);
        }
    }
}
