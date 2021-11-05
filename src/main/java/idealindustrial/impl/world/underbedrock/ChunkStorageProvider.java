package idealindustrial.impl.world.underbedrock;

import idealindustrial.api.world.underbedrock.ChunkProvider;
import idealindustrial.util.misc.II_Util;

import java.util.Map;

public class ChunkStorageProvider<T> implements ChunkProvider<T> {
    CoordSupplier<T> supplier;
    int gridSize;

    public ChunkStorageProvider(CoordSupplier<T> supplier, int gridSize) {
        this.supplier = supplier;
        this.gridSize = gridSize;
    }

    @Override
    public void addToCache(Map<Long, GridChunk<T>> chunkCache, int gridX, int gridZ) {
        long address = II_Util.intsToLong(gridX, gridZ);
        if (chunkCache.containsKey(address)) {
            return;
        }
        GridChunk<T> chunk = new GridChunk<>(gridSize);
        for (int x = 0; x < gridSize; x++) {
            for (int z = 0; z < gridSize; z++) {
                chunk.set(x, z, supplier.provide(gridX * gridSize + x, gridZ * gridSize + z));
            }
        }
        chunkCache.put(address, chunk);
    }

    public interface CoordSupplier<E> {
        E provide(int x, int z);
    }


}
