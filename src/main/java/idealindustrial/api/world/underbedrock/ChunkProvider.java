package idealindustrial.api.world.underbedrock;

import idealindustrial.impl.world.underbedrock.GridChunk;

import java.util.Map;

public interface ChunkProvider<T> {

    void addToCache(Map<Long, GridChunk<T>> chunkCache, int x, int z);
}
