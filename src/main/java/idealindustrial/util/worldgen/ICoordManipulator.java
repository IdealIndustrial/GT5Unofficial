package idealindustrial.util.worldgen;

public interface ICoordManipulator<M> extends ICoordRotatable, ICoordMovable {

    void start(M mode);
}
