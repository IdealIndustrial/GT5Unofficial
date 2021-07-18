package idealindustrial.util.energy;

public interface EUConsumer {

    boolean needsEnergy();

    long requestAmperes();

    void acceptEnergy(long voltage, long amperage);
}
