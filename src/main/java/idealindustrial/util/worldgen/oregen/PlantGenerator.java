package idealindustrial.util.worldgen.oregen;

import cpw.mods.fml.common.IWorldGenerator;
import idealindustrial.blocks.II_Blocks;
import idealindustrial.blocks.plants.BiomeStats;
import idealindustrial.blocks.plants.PlantDef;
import idealindustrial.blocks.plants.PlantStats;
import idealindustrial.blocks.plants.TilePlants;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;

public class PlantGenerator implements IWorldGenerator {
    protected int radius = 10, attempts = 30;
    protected Function<Random, PlantDef> defSupplier;
    protected BiFunction<Random, PlantDef, PlantStats> statSupplier =
            (r, d) -> new PlantStats(false, d.biome.copy(), 1 + r.nextInt(5), 1 + r.nextInt(5));

    public PlantGenerator(Function<Random, PlantDef> defSupplier) {
        this.defSupplier = defSupplier;
    }

    public PlantGenerator setRadius(int radius) {
        this.radius = radius;
        return this;
    }

    public PlantGenerator setAttempts(int attempts) {
        this.attempts = attempts;
        return this;
    }

    public PlantGenerator setStatSupplier(BiFunction<Random, PlantDef, PlantStats> statSupplier) {
        this.statSupplier = statSupplier;
        return this;
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        int x = (chunkX << 4) + 8;
        int z = (chunkZ << 4) + 8;
        for (int k = 0; k < attempts; k++) {
            int tx = x - radius + random.nextInt(radius * 2);
            int tz = z - radius + random.nextInt(radius * 2);
            int ty = world.getPrecipitationHeight(tx, tz);
            if (II_Blocks.INSTANCE.blockPlants.canPlaceBlockAt(world, tx, ty, tz)) {
                PlantDef def = defSupplier.apply(random);
                if (def.allowedSoil.apply(world.getBlock(tx, ty - 1, tz), world.getBlockMetadata(tx, ty - 1, tz))) {
                    PlantStats stats = statSupplier.apply(random, def);
                    TilePlants.plateTileRawGen(world, tx, ty, tz, def, stats);
                    world.scheduleBlockUpdate(tx, ty, tz, II_Blocks.INSTANCE.blockPlants, 10);
                }
            }
        }
    }
}
