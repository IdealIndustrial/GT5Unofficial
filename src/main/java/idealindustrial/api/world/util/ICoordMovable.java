package idealindustrial.api.world.util;

import idealindustrial.impl.world.util.Vector3;

public interface ICoordMovable {

    void move(Vector3 vector);

    default void move(int x, int y, int z) {
        move(new Vector3(x, y, z));
    }

}
