package idealindustrial.util.worldgen;

public interface ICoordMovable {

    void move(Vector3 vector);

    default void move(int x, int y, int z) {
        move(new Vector3(x, y, z));
    }

}
