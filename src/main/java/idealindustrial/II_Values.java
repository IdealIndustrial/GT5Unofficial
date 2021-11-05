package idealindustrial;

import idealindustrial.api.tile.meta.Tile;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public class II_Values {
    @SuppressWarnings("rawtypes")
    public static final Tile[] TILES = new Tile[Short.MAX_VALUE];
    public static Stream<Tile<?>> tileStream() {
        return Arrays.stream(TILES).filter(Objects::nonNull).map(t -> (Tile<?>)t);
    }

    public static final String MOD_ID = "iicore";
    public static final int maxTier = 1;
}
