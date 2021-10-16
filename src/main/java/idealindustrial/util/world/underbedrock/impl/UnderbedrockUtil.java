package idealindustrial.util.world.underbedrock.impl;

public class UnderbedrockUtil {

    interface LongLongFunc {
        long apply(long value);
    }

    interface CoordinateFunc{
        long apply(int x1, int z2, long amount);
    }

    @SuppressWarnings("unchecked")
    public static <T> T[][] convert(long[][] map, DifferenceSupplier<T> supplier) {
        T[][] ar = (T[][]) new Object[map.length][map[0].length];
        for (int x = 0; x < ar.length; x++) {
            for (int z = 0; z < ar[0].length; z++) {
                ar[x][z] = supplier.supply(map[x][z]);
            }
        }
        return ar;
    }

    public static long[][] apply(long[][] ar, LongLongFunc func) {
        for (int x = 0; x < ar.length; x++) {
            for (int z = 0; z < ar[0].length; z++) {
                ar[x][z] = func.apply(ar[x][z]);
            }
        }
        return ar;
    }

    public static long[][] apply(long[][] ar, CoordinateFunc func) {
        for (int x = 0; x < ar.length; x++) {
            for (int z = 0; z < ar[0].length; z++) {
                ar[x][z] = func.apply(x, z, ar[x][z]);
            }
        }
        return ar;
    }

    public static int blockToBlockCoordinateInChunk(int coord, int gridSize) {
        return ((coord % gridSize) + gridSize) % gridSize;
    }

    public static int blockToChunkCoordinate(int coord, int gridSize) {
        if (coord < 0) {
            return ((coord + 1)) / gridSize - 1;
        }
        return coord / gridSize;
    }
}
