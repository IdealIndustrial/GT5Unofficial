package idealindustrial.util.energy;

import idealindustrial.util.energy.system.II_CableSystem;

public interface EUProducer {

    boolean hasEnergy();

    long voltage();

    long availableAmperes();

    void consume(long amperes);
    void reset();

    void setSystem(II_CableSystem system);

}
