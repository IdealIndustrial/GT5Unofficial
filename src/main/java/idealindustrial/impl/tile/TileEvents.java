package idealindustrial.impl.tile;

public class TileEvents {
    public static final int BASE_ACTIVE = 1;

    public static final int FACING_OUTPUT = 100, FACING_MAIN = 101;

    public static final int CONNECTIONS = 200;

    public static final int ROTATION_SPEED = 250, ROTATION_SPEED_DIRECT = 251;

    //all events with set 30 bit are reserved for tile modules
    private static final int MODULE_BIT = 1 << 30;
    public static boolean isTileModuleEvent(int a) {
        return (a & MODULE_BIT) != 0;
    }

    public static final int MODULE_START_PROCESSING = 1 | MODULE_BIT;


}
