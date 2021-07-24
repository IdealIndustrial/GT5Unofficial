package idealindustrial.tile.gui.base.component;

import idealindustrial.tile.base.II_BaseTile;

public interface ISynchronizer<T> {

    T getValue(II_BaseTile tile);

    void setValue(II_BaseTile tile, T value);

    int asInt(T value);

    T fromInt(int i);
}
