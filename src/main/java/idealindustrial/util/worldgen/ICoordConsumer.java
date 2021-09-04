package idealindustrial.util.worldgen;

public interface ICoordConsumer<M> {

    void apply(M mode, Vector3 position, int rotation);
}
