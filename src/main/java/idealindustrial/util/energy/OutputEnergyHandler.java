package idealindustrial.util.energy;

import idealindustrial.tile.IOType;
import idealindustrial.tile.interfaces.base.BaseMachineTile;
import idealindustrial.util.world.EnergySystemHandler;

public class OutputEnergyHandler extends BasicEnergyHandler {

    boolean[] allowedSides;
    II_DefaultEUProducer universalProducer;
    int outSide = 0;

    public OutputEnergyHandler(BaseMachineTile baseTile, long minEnergyAmount, long maxCapacity, long voltageOut, long amperageOut, int outSide) {
        super(baseTile, minEnergyAmount, maxCapacity, 0, 0, voltageOut, amperageOut);
        this.universalProducer = new II_DefaultEUProducer(this);
        this.allowedSides = baseTile.getIO(IOType.ENERGY);
        this.outSide = outSide;
    }

    @Override
    public EUProducer getProducer(int side) {
        if (side == outSide && allowedSides[6 + side]) {
            return universalProducer;
        }
        return null;
    }

    @Override
    public EUConsumer getConsumer(int side) {
        return null;
    }

    @Override
    public void onConfigurationChanged() {
        this.allowedSides = baseTile.getIO(IOType.ENERGY);
        if (universalProducer.system != null) {
            universalProducer.system.invalidate();
        }
    }

    @Override
    public void onUpdate() {
        if (universalProducer.system == null) {
            EnergySystemHandler.initSystem(baseTile, outSide);
        }
    }

    @Override
    public void onRemoval() {
        if (universalProducer.system != null) {
            universalProducer.system.invalidate();
        }
    }
}
