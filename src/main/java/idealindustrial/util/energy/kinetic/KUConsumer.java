package idealindustrial.util.energy.kinetic;

public interface KUConsumer {

    int getPowerUsage();

    int getOptimalSpeed();

    void supply(int power, int speed);
}
