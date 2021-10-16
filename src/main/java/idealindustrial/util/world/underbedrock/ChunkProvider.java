package idealindustrial.util.world.underbedrock;

import java.util.Map;

public interface ChunkProvider<T> {

    void addToCache(Map<Long, GridChunk<T>> chunkCache, int x, int z);
}
