package idealindustrial.impl.tile.energy.kinetic;

import idealindustrial.api.tile.energy.kinetic.KUConsumer;
import idealindustrial.api.tile.energy.kinetic.KUProducer;
import idealindustrial.api.tile.energy.kinetic.KineticEnergyHandler;

public class EmptyKineticHandler implements KineticEnergyHandler {
    public static final EmptyKineticHandler INSTANCE = new EmptyKineticHandler();

    private EmptyKineticHandler() {

    }

    @Override
    public KUConsumer getConsumer(int side) {
        return null;
    }

    @Override
    public KUProducer getProducer(int side) {
        return null;
    }
}
