package idealindustrial.util.energy.kinetic;

import idealindustrial.tile.impl.connected.ConnectedRotor;
import idealindustrial.tile.interfaces.host.HostTile;
import idealindustrial.tile.interfaces.meta.Tile;
import idealindustrial.util.energy.kinetic.system.KineticSystem;
import idealindustrial.util.misc.II_DirUtil;
import idealindustrial.util.misc.II_TileUtil;

import java.util.Set;

public interface KUPassThrough {

    void setSystem(KineticSystem system);

    default void spreadSetSpeed(Set<KUPassThrough> alreadyPassedSet, int speed, HostTile hostTile) {
        if (!alreadyPassedSet.add(this)) {
            return;
        }
        for (int i = 0; i < 6; i++) {
            Tile<?> tile = II_TileUtil.getMetaTileAtSide(hostTile, i);
            if (tile instanceof ConnectedRotor) {
                if (((ConnectedRotor) tile).isConnected(II_DirUtil.getOppositeSide(i))) {
                    ((ConnectedRotor) tile).spreadSetSpeed(alreadyPassedSet, speed, tile.getHost());
                }
            }
            if (tile instanceof KUSplitter) {
                ((KUSplitter) tile).spreadSetSpeed(alreadyPassedSet, speed, tile.getHost());
            }
        }
    }
}
