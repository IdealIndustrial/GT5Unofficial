package idealindustrial.api.tile.energy.kinetic;

public interface KUConsumer {

    int getPowerUsage();

    void supply(int power, int speed);

    default int getNextDirection() {
        return -1;
    }
}
