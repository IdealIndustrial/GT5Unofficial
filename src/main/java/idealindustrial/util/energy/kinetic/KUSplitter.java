package idealindustrial.util.energy.kinetic;

import idealindustrial.tile.impl.connected.ConnectedRotor;
import idealindustrial.tile.interfaces.meta.Tile;
import idealindustrial.util.misc.II_DirUtil;
import idealindustrial.util.misc.II_TileUtil;

import java.util.Set;

public interface KUSplitter extends KUPassThrough {

    int getInputSide();
}
