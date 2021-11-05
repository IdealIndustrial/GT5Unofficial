package idealindustrial.impl.tile.energy.kinetic.system;

import idealindustrial.impl.tile.impl.connected.ConnectedRotor;

import java.util.ArrayList;
import java.util.List;

public class KineticConnection {
    List<ConnectedRotor> cables;
    int maxSpeed, maxPower;

    public KineticConnection() {
        cables = new ArrayList<>();
    }
}
