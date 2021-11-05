package idealindustrial.api.world.util;

import idealindustrial.impl.world.util.BoundingBox;

public interface ICoordManipulator<M> extends ICoordRotatable, ICoordMovable {

    void start(M mode);

    BoundingBox getBox();
}
