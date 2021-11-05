package idealindustrial.api.tile.energy.kinetic;

import idealindustrial.impl.tile.energy.kinetic.system.KineticSystem;

public interface KUProducer {

    int getSpeed(int powerRequest);

    int getTotalPower();

    void onAddedToSystem(KineticSystem system);

    void onConnectionAppended();

    void setSystem(KineticSystem system);
}
