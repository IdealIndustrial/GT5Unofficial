package idealindustrial.api.tile.module;

public interface TileModule {

    void onPostTick(long timer, boolean serverSide);

    default void receiveEvent(int id, int value) {
    }
}
