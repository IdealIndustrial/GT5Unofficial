package idealindustrial.util.energy.kinetic.system;

import idealindustrial.tile.impl.connected.ConnectedRotor;
import idealindustrial.util.energy.kinetic.KUConsumer;
import idealindustrial.util.energy.kinetic.KUProducer;

import java.util.ArrayList;
import java.util.List;

public class KineticConnection {
    List<ConnectedRotor> cables;
    int maxSpeed, maxPower;

    public KineticConnection() {
        cables = new ArrayList<>();
    }
}
