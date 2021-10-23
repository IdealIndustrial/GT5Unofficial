package idealindustrial.util.world;

import idealindustrial.util.world.underbedrock.ChunkStorageProvider;
import idealindustrial.util.world.underbedrock.UnderbedrockLayer;
import idealindustrial.util.world.underbedrock.impl.BasicUnderbedrockLayer;
import idealindustrial.util.world.underbedrock.impl.ChunkData;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class DimensionChunkData {

    private static Map<Integer, UnderbedrockLayer<ChunkData>> layerData = new HashMap<>();

    public static void chunkLoad(World world, int x, int z, NBTTagCompound nbt) {
        getLayer(world).nbtLoad(x, z, nbt);
    }

    public static void chunkSave(World world, int x, int z, NBTTagCompound nbt) {
        getLayer(world).nbtSave(x, z, nbt);
    }

    public static UnderbedrockLayer<ChunkData> getLayer(World world) {
        return layerData.computeIfAbsent(world.provider.dimensionId,
                i -> new BasicUnderbedrockLayer<>(3, new ChunkStorageProvider<>(new ChunkData.ChunkDataSupplier(world), 3),
                        "ii_layer", new ChunkData.Serializer()));
    }
}
