package idealindustrial.api.world.util;

import idealindustrial.impl.world.util.Vector3;

public interface MatrixCoordConsumer<M> {

    void apply(M mode, Vector3 position, int rotation);
}
