package idealindustrial.util.world.underbedrock.impl;

import idealindustrial.util.misc.II_Util;
import idealindustrial.util.world.underbedrock.*;

import java.util.Map;
import java.util.Random;

import static idealindustrial.util.world.underbedrock.impl.UnderbedrockUtil.blockToChunkCoordinate;

public class UnderbedrockChunkProvider<T> implements ChunkProvider<T> {

    private final GridGenerationRules<T> rules;
    private final int clusterSize, gridSize;

    public UnderbedrockChunkProvider(GridGenerationRules<T> rules) {
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
        if (chunk.isFree(ore.getCollider())) {
            chunk.insert(ore);
        }
        return chunk;
    }
}
