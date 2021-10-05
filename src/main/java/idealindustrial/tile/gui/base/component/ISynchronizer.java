package idealindustrial.tile.gui.base.component;

import idealindustrial.tile.interfaces.host.HostTile;

public interface ISynchronizer<T> {

    T getValue(HostTile tile);

    void setValue(HostTile tile, T value);

    int asInt(T value);

    T fromInt(int i);
}
