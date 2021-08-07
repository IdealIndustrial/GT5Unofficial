package idealindustrial.tile.meta;

import idealindustrial.tile.interfaces.base.II_BaseTile;
import idealindustrial.tile.interfaces.meta.II_MetaTile;

public interface II_MetaTileFactory {

    II_MetaTile construct(II_BaseTile baseTile);
}
