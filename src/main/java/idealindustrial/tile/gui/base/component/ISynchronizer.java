package idealindustrial.tile.gui.base.component;

import idealindustrial.tile.interfaces.base.BaseTile;

public interface ISynchronizer<T> {

    T getValue(BaseTile tile);

    void setValue(BaseTile tile, T value);

    int asInt(T value);

    T fromInt(int i);
}
