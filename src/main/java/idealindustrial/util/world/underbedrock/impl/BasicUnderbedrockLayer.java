package idealindustrial.util.world.underbedrock.impl;

import idealindustrial.util.misc.II_Util;
import idealindustrial.util.misc.NBTSerializer;
import idealindustrial.util.world.underbedrock.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.chunk.Chunk;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static idealindustrial.util.world.underbedrock.impl.UnderbedrockUtil.blockToBlockCoordinateInChunk;
import static idealindustrial.util.world.underbedrock.impl.UnderbedrockUtil.blockToChunkCoordinate;

public class BasicUnderbedrockLayer<T> implements UnderbedrockLayer<T> {

    private final Map<Long, GridChunk<T>> chunkCache = new HashMap<>();
    private final int gridSize;
    private final ChunkProvider<T> provider;
    private final String nbtName;
    private final NBTSerializer<T> serializationWrapper;

    public BasicUnderbedrockLayer(GridGenerationRules<T> rules) {
        this(rules, new UnderbedrockChunkProvider<>(rules), "noNBT", null);
    }

    public BasicUnderbedrockLayer(GridGenerationRules<T> rules, ChunkProvider<T> provider, String nbtName, NBTSerializer<T> serializationWrapper) {
        this.provider = provider;
        this.gridSize = rules.getGridSize();
        this.nbtName = nbtName;
        this.serializationWrapper = serializationWrapper;
    }

    @Override
    public void nbtLoad(int x, int z, NBTTagCompound nbt) {
        NBTTagCompound stored = nbt.getCompoundTag(nbtName);
        if (stored != null) {
            T loaded = serializationWrapper.load(stored);
            set(x, z, loaded);
        }
    }

    @Override
    public void nbtSave(int x, int z, NBTTagCompound nbt) {
        GridChunk<T> chunk = getChunkFromCache(x, z);
        if (chunk != null && chunk.wasModified()) {
            T t = chunk.get(blockToBlockCoordinateInChunk(x, gridSize), blockToBlockCoordinateInChunk(z, gridSize));
            nbt.setTag(nbtName, serializationWrapper.save(t, new NBTTagCompound()));
        }
    }

    @Override
    public T get(int x, int z) {
        int chunkX = blockToChunkCoordinate(x, gridSize), chunkZ = blockToChunkCoordinate(z, gridSize);
        long address = II_Util.intsToLong(chunkX, chunkZ);
        if (!chunkCache.containsKey(address)) {
            provider.addToCache(chunkCache, chunkX, chunkZ);
        }
        return chunkCache.get(address).get(blockToBlockCoordinateInChunk(x, gridSize), blockToBlockCoordinateInChunk(z, gridSize));
    }


    private GridChunk<T> getChunkFromCache(int x, int z) {
        int chunkX = blockToChunkCoordinate(x, gridSize), chunkZ = blockToChunkCoordinate(z, gridSize);
        long address = II_Util.intsToLong(chunkX, chunkZ);
        if (!chunkCache.containsKey(address)) {
            return null;
        }
        return chunkCache.get(address);
    }

    @Override
    public void flagAsModified(int x, int z) {
        int chunkX = blockToChunkCoordinate(x, gridSize), chunkZ = blockToChunkCoordinate(z, gridSize);
        long address = II_Util.intsToLong(chunkX, chunkZ);
        chunkCache.get(address).setModified(true);
    }

    @Override
    public void set(int x, int z, T vein) {
        int chunkX = blockToChunkCoordinate(x, gridSize), chunkZ = blockToChunkCoordinate(z, gridSize);
        long address = II_Util.intsToLong(chunkX, chunkZ);
        GridChunk<T> chunk = chunkCache.get(address);
        if (chunk == null) {
            chunk = new GridChunk<>(gridSize);
        }
        chunk.setModified(true);
        chunk.set(blockToBlockCoordinateInChunk(x, gridSize), blockToBlockCoordinateInChunk(z, gridSize), vein);
    }

}
