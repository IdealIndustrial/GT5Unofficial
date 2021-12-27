package idealindustrial.impl.world.oregen;

import cpw.mods.fml.common.IWorldGenerator;
import idealindustrial.impl.autogen.material.II_Materials;
import idealindustrial.impl.autogen.material.Prefixes;
import idealindustrial.impl.blocks.ores.TileOres;
import idealindustrial.impl.blocks.plants.Plants;
import idealindustrial.impl.item.stack.HashedBlock;
import idealindustrial.impl.world.gen.Torus;
import idealindustrial.util.misc.RandomCollection;
import idealindustrial.api.world.underbedrock.WeightedRandom;
import idealindustrial.impl.world.gen.ClampedEllipsoid;
import idealindustrial.impl.world.gen.LinearRange;
import idealindustrial.impl.world.util.Vector3;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.Random;

public class OreGenerator implements IWorldGenerator {
    static WeightedRandom<OreEntry> ores = new RandomCollection<>(1d);
    static {
        ores.add(3, new OreEntry(II_Materials.arsenicBronze, Prefixes.oreSmall, new ClampedEllipsoid(4, 10), new LinearRange(50, 70))
        .addGenerator(new PlantGenerator(r -> Plants.copperPlant).setRadius(10)));
        ores.add(5, new OreEntry(II_Materials.copper, Prefixes.ore, new ClampedEllipsoid(5, 13), new LinearRange(40, 50))
                .addGenerator(new PlantGenerator(r -> Plants.copperPlant)));
        ores.add(4, new OreEntry(II_Materials.tin, Prefixes.ore, new Torus(5, 8, 5), new LinearRange(40, 50))
                .addGenerator(new PlantGenerator(r -> Plants.tinPlant)));
    }

    private static int successCounter = 0;
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        if (chunkX % 3 != 0 || chunkZ % 3 != 0) {
            return;
        }
        OreEntry entry = ores.next(random);
        Vector3 position = new Vector3(chunkX * 16 + 8, entry.heightRange.get(random), chunkZ * 16 + 8);
        successCounter = 0;
        entry.shape.forEach(position, vec -> {
            Block b = world.getBlock(vec.x, vec.y, vec.z);
            int meta = world.getBlockMetadata(vec.x, vec.y, vec.z);
            if (!entry.baseBlocks.contains(new HashedBlock(b, meta))) {
                return;
            }
            if (Math.abs(random.nextDouble()) * 100 < entry.fillRatio) {
                TileOres.replaceBlock(world, vec.x, vec.y, vec.z, TileOres.getMeta(entry.material, entry.prefix));
                successCounter++;
            }
        });
        if (entry.onSuccess != null && successCounter > entry.shape.getBoundingBox().volume() / 10) {
            entry.onSuccess.generate(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
        }
    }


}
