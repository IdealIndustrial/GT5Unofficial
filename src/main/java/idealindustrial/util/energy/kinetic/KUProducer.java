package idealindustrial.util.energy.kinetic;

import idealindustrial.util.energy.kinetic.system.KineticSystem;

public interface KUProducer {

    int getSpeed(int powerRequest);

    int getTotalPower();

    void onAddedToSystem(KineticSystem system);

    void onConnectionAppended();

    void setSystem(KineticSystem system);
}
