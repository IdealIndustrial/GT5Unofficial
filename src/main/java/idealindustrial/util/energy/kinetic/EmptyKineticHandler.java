package idealindustrial.util.energy.kinetic;

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
