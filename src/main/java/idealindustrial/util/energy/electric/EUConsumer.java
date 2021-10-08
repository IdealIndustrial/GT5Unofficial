package idealindustrial.util.energy.electric;

public interface EUConsumer {

    boolean needsEnergy();

    long requestAmperes();

    void acceptEnergy(long voltage, long amperage);
}
