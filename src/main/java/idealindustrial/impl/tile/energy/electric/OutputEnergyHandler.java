package idealindustrial.impl.tile.energy.electric;

import idealindustrial.api.tile.energy.electric.EUConsumer;
import idealindustrial.api.tile.energy.electric.EUProducer;
import idealindustrial.impl.tile.IOType;
import idealindustrial.impl.tile.host.WorldAction;
import idealindustrial.api.tile.host.HostMachineTile;
import idealindustrial.impl.tile.energy.electric.system.EnergySystemHandler;

import java.util.stream.IntStream;

public class OutputEnergyHandler extends BasicEnergyHandler {

    boolean[] allowedSides;
    II_DefaultEUProducer universalProducer;
    int outSide;

    public OutputEnergyHandler(HostMachineTile baseTile, long minEnergyAmount, long maxCapacity, long voltageOut, long amperageOut, int outSide) {
        super(baseTile, minEnergyAmount, maxCapacity, 0, 0, voltageOut, amperageOut);
        this.universalProducer = new II_DefaultEUProducer(this);
        this.allowedSides = baseTile.getIO(IOType.ENERGY);
        baseTile.onIOConfigurationChanged(this::onConfigurationChanged);
        baseTile.onWorldStateUpdated(this::onRemoval);
    }

    @Override
    public EUProducer getProducer(int side) {
        if (allowedSides[side]) {
            return universalProducer;
        }
        return null;
    }

    @Override
    public EUConsumer getConsumer(int side) {
        return null;
    }

    public void onConfigurationChanged(IOType type) {
        if (!type.is(IOType.ENERGY)) {
            return;
        }
        this.allowedSides = baseTile.getIO(IOType.ENERGY);
        assert IntStream.iterate(0, i -> i + 1).limit(6).map(i -> allowedSides[i] ? 1 : 0).sum() == 1; //only 1 output side allowed
        for (int i = 0; i < 6; i++) {
            if (allowedSides[i]) {
                outSide = i;
            }
        }
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

    public void onRemoval(WorldAction action) {
        if (action.equals(WorldAction.OnRemoval) && universalProducer.system != null) {
            universalProducer.system.invalidate();
        }
    }
}
