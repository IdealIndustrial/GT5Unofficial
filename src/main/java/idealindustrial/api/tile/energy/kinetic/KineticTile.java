package idealindustrial.api.tile.energy.kinetic;

import gnu.trove.set.TLongSet;
import idealindustrial.util.HPoint;

public interface KineticTile {

    HPoint localPoint = new HPoint();

    long powerUsage(TLongSet passed, int side, int speed);

    void usePower(TLongSet passed, int side, int speed, double satisfaction);
}
