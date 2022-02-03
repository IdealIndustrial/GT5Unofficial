package idealindustrial.api.world.underbedrock;

import idealindustrial.impl.world.util.Vector2;

public interface Vein<T> {

    boolean isFull(int x, int z);

    int size();

    Vector2 position();

    T[][] get();
}
