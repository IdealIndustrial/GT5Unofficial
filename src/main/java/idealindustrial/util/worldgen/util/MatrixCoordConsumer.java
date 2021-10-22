package idealindustrial.util.worldgen.util;

public interface MatrixCoordConsumer<M> {

    void apply(M mode, Vector3 position, int rotation);
}
