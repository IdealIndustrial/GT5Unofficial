package idealindustrial.util.worldgen.util;

public interface ICoordManipulator<M> extends ICoordRotatable, ICoordMovable {

    void start(M mode);

    BoundingBox getBox();
}
