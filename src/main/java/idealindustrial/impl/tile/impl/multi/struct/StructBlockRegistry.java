package idealindustrial.impl.tile.impl.multi.struct;

import gnu.trove.iterator.TLongIterator;
import gnu.trove.set.TLongSet;
import gnu.trove.set.hash.TLongHashSet;
import idealindustrial.api.tile.meta.Tile;
import idealindustrial.util.HPoint;
import idealindustrial.util.misc.ItemHelper;
import idealindustrial.util.misc.II_TileUtil;
import net.minecraft.block.Block;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class StructBlockRegistry {
    static class BlockEntry implements ItemHelper.Rehashable {
        int meta;
        Block block;

        public BlockEntry(Block block, int meta) {
            this.meta = meta;
            this.block = block;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BlockEntry that = (BlockEntry) o;
            return meta == that.meta && block == that.block;
        }

        @Override
        public int hashCode() {
            return Objects.hash(meta, Block.getIdFromBlock(block));
        }

        @Override
        public void fixHash() {

        }
    }

    static Set<BlockEntry> casings = ItemHelper.querySet(new HashSet<>());

    public static void registerCasing(Block block, int meta) {
        casings.add(new BlockEntry(block, meta));
    }

    public static void spreadUpdate(World world, int x, int y, int z) {
        new Updater(world, x, y, z).run(); //no multithreading for now
    }


    private static class Updater implements Runnable {

        World world;
        HPoint helper = new HPoint();
        TLongSet curPositions = new TLongHashSet(), passed = new TLongHashSet(), nextPositions = new TLongHashSet();

        public Updater(World world, int x, int y, int z) {
            this.world = world;
            long[] pos = new long[]{HPoint.toLong(x, y, z),
                    HPoint.toLong(x, y, z - 1),
                    HPoint.toLong(x, y, z + 1),
                    HPoint.toLong(x, y - 1, z),
                    HPoint.toLong(x, y + 1, z),
                    HPoint.toLong(x + 1, y, z),
                    HPoint.toLong(x - 1, y, z)};
            curPositions.addAll(pos);
            passed.addAll(pos);
        }

        @Override
        public void run() {
            while (!curPositions.isEmpty()) {
                for (TLongIterator it = curPositions.iterator(); it.hasNext(); ) {
                    long l = it.next();
                    helper.fromLong(l);
                    process(helper.x, helper.y, helper.z);
                }
                passed.addAll(curPositions);
                curPositions = nextPositions;
                nextPositions = new TLongHashSet();
            }
        }

        private void process(int x, int y, int z) {
//            System.out.println(x + " " + y + " " + z);
//            world.setBlock(x, y + 10, z, Blocks.iron_ore, 0, 3);
            boolean transfer = false;
            Tile<?> tile = II_TileUtil.getMetaTile(world, x, y, z);
            if (tile != null) {
                transfer = tile.transferStructureUpdate();
                tile.receiveStructureUpdate();
            }
            if (!transfer) {
                Block b = world.getBlock(x, y, z);
                int meta = world.getBlockMetadata(x, y, z);
                transfer = casings.contains(new BlockEntry(b, meta));
            }
            if (transfer) {
                addNextPos(x + 1, y, z);
                addNextPos(x - 1, y, z);
                addNextPos(x, y + 1, z);
                addNextPos(x, y - 1, z);
                addNextPos(x, y, z + 1);
                addNextPos(x, y, z - 1);
            }
        }

        private void addNextPos(int x, int y, int z) {
            long l = HPoint.toLong(x, y, z);
            if (!passed.contains(l) && !nextPositions.contains(l)) {
                nextPositions.add(l);
            }
        }
    }
}
