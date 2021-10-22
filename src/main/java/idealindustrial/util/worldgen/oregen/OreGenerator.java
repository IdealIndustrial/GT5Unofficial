package idealindustrial.util.worldgen.oregen;

import cpw.mods.fml.common.IWorldGenerator;
import idealindustrial.autogen.material.II_Materials;
import idealindustrial.autogen.material.Prefixes;
import idealindustrial.blocks.ores.TileOres;
import idealindustrial.util.item.HashedBlock;
import idealindustrial.util.misc.RandomCollection;
import idealindustrial.util.world.underbedrock.WeightedRandom;
import idealindustrial.util.worldgen.impl.ClampedEllipsoid;
import idealindustrial.util.worldgen.impl.LinearRange;
import idealindustrial.util.worldgen.util.Vector3;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.Random;

public class OreGenerator implements IWorldGenerator {
    static WeightedRandom<OreEntry> ores = new RandomCollection<>(1d);
    static {
        ores.add(5, new OreEntry(II_Materials.iron, Prefixes.ore, new ClampedEllipsoid(4, 10), new LinearRange(10, 50)));
        ores.add(3, new OreEntry(II_Materials.copper, Prefixes.ore, new ClampedEllipsoid(4, 15), new LinearRange(10, 50)));
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        if (chunkX % 3 != 0 || chunkZ % 3 != 0) {
            return;
        }
        OreEntry entry = ores.next(random);
        Vector3 position = new Vector3(chunkX * 16 + 8, entry.heightRange.get(random), chunkZ * 16 + 8);
        entry.shape.forEach(position, vec -> {
            Block b = world.getBlock(vec.x, vec.y, vec.z);
            int meta = world.getBlockMetadata(vec.x, vec.y, vec.z);
            if (!entry.baseBlocks.contains(new HashedBlock(b, meta))) {
                return;
            }
            if (Math.abs(random.nextDouble()) * 100 < entry.fillRatio) {
                TileOres.replaceBlock(world, vec.x, vec.y, vec.z, TileOres.getMeta(entry.material, entry.prefix));
            }
        });
    }


}
