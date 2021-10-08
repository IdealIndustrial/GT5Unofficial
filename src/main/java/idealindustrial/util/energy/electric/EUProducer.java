package idealindustrial.util.energy.electric;

import idealindustrial.util.energy.electric.system.CableSystem;

public interface EUProducer {

    boolean hasEnergy();

    long voltage();

    long availableAmperes();

    void consume(long amperes);
    void reset();

    void setSystem(CableSystem system);

    void onConnectionAppended();

}
