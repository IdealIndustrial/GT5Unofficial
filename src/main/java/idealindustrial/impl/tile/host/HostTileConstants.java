package idealindustrial.impl.tile.host;

public class HostTileConstants {
    public static final int EVENT_ACTIVE = 1;

    public static boolean intToBool(int i) {
        return i == 1;
    }

    public static int boolToInt(boolean i) {
        return i ? 1 : 0;
    }
}
