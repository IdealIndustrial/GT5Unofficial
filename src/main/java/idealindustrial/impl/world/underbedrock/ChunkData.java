package idealindustrial.impl.world.underbedrock;

import idealindustrial.impl.blocks.plants.Plants;
import idealindustrial.util.misc.NBTSerializer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class ChunkData {
    private int minerals;
    private int pollution;


    public int getMinerals() {
        return minerals;
    }

    public int getPollution() {
        return pollution;
    }

    public void setMinerals(int minerals) {
        this.minerals = minerals;
    }

    public void setPollution(int pollution) {
        this.pollution = pollution;
    }

    public static class ChunkDataSupplier implements ChunkStorageProvider.CoordSupplier<ChunkData> {

        World world;

        public ChunkDataSupplier(World world) {
            this.world = world;
        }

        @Override
        public ChunkData provide(int x, int z) {
            ChunkData out = new ChunkData();
            BiomeGenBase biome = world.getBiomeGenForCoords(x, z);
            out.minerals = Plants.mineralsPerBiome.getOrDefault(biome, 10_000);
            return out;
        }
    }

    public static class Serializer implements NBTSerializer<ChunkData> {

        @Override
        public ChunkData load(NBTTagCompound nbt) {
            ChunkData out = new ChunkData();
            out.pollution = nbt.getInteger("pollution");
            out.minerals = nbt.getInteger("minerals");
            return out;
        }

        @Override
        public NBTTagCompound save(ChunkData chunkData, NBTTagCompound nbt) {
            nbt.setInteger("pollution", chunkData.pollution);
            nbt.setInteger("minerals", chunkData.minerals);
            return nbt;
        }
    }
}
