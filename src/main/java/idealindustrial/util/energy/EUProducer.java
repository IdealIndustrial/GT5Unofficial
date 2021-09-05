package idealindustrial.util.energy;

import idealindustrial.util.energy.system.CableSystem;

public interface EUProducer {

    boolean hasEnergy();

    long voltage();

    long availableAmperes();

    void consume(long amperes);
    void reset();

    void setSystem(CableSystem system);

}
