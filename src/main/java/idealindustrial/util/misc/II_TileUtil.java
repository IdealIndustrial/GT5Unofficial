package idealindustrial.util.misc;

import idealindustrial.II_Values;
import idealindustrial.tile.base.II_BaseTile;
import idealindustrial.tile.base.II_BaseTileImpl;
import idealindustrial.tile.meta.II_MetaTile;

public class II_TileUtil {

    public static void registerMetaTile(int id, II_MetaTile metaTile) {
        II_Values.metaTiles[id] = metaTile;
        metaTile.getBase().setMetaTileID(id);
    }

    public static II_BaseTile makeBaseTile() {
        return new II_BaseTileImpl();
    }
}
