package idealindustrial.api.tile.energy.kinetic;

import idealindustrial.impl.tile.impl.connected.ConnectedRotor;
import idealindustrial.api.tile.host.HostTile;
import idealindustrial.api.tile.meta.Tile;
import idealindustrial.impl.tile.energy.kinetic.system.KineticSystem;
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
