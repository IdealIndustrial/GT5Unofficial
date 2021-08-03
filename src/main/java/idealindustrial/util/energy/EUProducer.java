package idealindustrial.util.energy;

public interface EUProducer {

    boolean hasEnergy();

    long voltage();

    long availableAmperes();

    void consume(long amperes);

    void setHasSystem(boolean hasSystem);

}
