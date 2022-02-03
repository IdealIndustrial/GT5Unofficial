package idealindustrial.impl.world.underbedrock;

import idealindustrial.api.world.underbedrock.*;
import idealindustrial.util.misc.II_Util;

import java.util.Map;
import java.util.Random;

import static idealindustrial.impl.world.underbedrock.UnderbedrockUtil.blockToChunkCoordinate;

public class UnderbedrockOreProvider<T> implements ChunkProvider<T> {

    private final GridGenerationRules<T> rules;
    private final int clusterSize, gridSize;

    public UnderbedrockOreProvider(GridGenerationRules<T> rules) {
        this.rules = rules;
        this.clusterSize = rules.getClusterSize();
        this.gridSize = rules.getGridSize();
    }

    @Override
    public void addToCache(Map<Long, GridChunk<T>> chunkCache, int x, int z) {
        x = blockToChunkCoordinate(x, clusterSize);
        z = blockToChunkCoordinate(z, clusterSize);
        Random random = new Random(Long.hashCode(x * 31249843213L + z * 31));
        for (int i = 0; i < rules.getPassCount(); i++) {
            WeightedRandom<VeinProvider<T>> provider = rules.getProviderForPass(i);
            OrderGenerator xs = rules.getOrder(clusterSize, random);
            while (xs.hasNext()) {
                int xi = xs.next();
                OrderGenerator zs = rules.getOrder(clusterSize, random);
                while (zs.hasNext()) {
                    int zi = zs.next();
                    int chunkX = x * clusterSize + xi;
                    int chunkZ = z * clusterSize + zi;
                    long address = II_Util.intsToLong(chunkX, chunkZ);
                    GridChunk<T> chunk = chunkCache.get(address);
                    chunk = process(chunk, provider, random);
                    chunkCache.put(address, chunk);
                }
            }
        }
    }

    private GridChunk<T> process(GridChunk<T> chunk, WeightedRandom<VeinProvider<T>> provider, Random random) {
        if (chunk == null) {
            chunk = new GridChunk<>(gridSize);
        }
        VeinProvider<T> vein = provider.next(random);
        Vein<T> ore = vein.provide(random, rules);
        chunk.insert(ore);
        return chunk;
    }
}
