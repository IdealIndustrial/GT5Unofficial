package idealindustrial.api.tile.energy.kinetic;

public interface KineticEnergyHandler {

    KUConsumer getConsumer(int side);

    KUProducer getProducer(int side);

}
