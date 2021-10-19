package idealindustrial.teststuff;

import cpw.mods.fml.common.registry.GameRegistry;
import gnu.trove.iterator.TLongIterator;
import gnu.trove.map.TLongLongMap;
import gnu.trove.map.hash.TLongLongHashMap;
import gnu.trove.set.TLongSet;
import gnu.trove.set.hash.TLongHashSet;
import idealindustrial.autogen.material.II_Materials;
import idealindustrial.autogen.material.submaterial.MatterState;
import idealindustrial.util.HPoint;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.FluidStack;

import java.util.Random;

import static idealindustrial.util.misc.II_Math.sign;

public class FluidTest extends BlockFluidBase {

    public FluidTest() {
        super(II_Materials.lightWater.getLiquidInfo().get(MatterState.Liquid).getFluid(), Material.water);
        GameRegistry.registerBlock(this, "testFluid");
        II_Materials.lightWater.getLiquidInfo().get(MatterState.Liquid).setBlock(this);
        quantaPerBlock = 15;
        quantaPerBlockFloat = 15f;
    }

    /**
     * Ticks the block if it's been scheduled
     */
    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        if (world.isRemote) {
            return;
        }
        int amount = amount(world, x, y, z);
        int neighbours = neighbours(world, x, y, z);
        boolean src = isSource(world, x, y, z);
        if (!src && largestNeighbour(world, x, y, z) <= amount) {
            set(world, x, y, z, amount - 1);
            return;
        }
        if (src) {
            neighbours++;
        }
        if (neighbours == 2) {
            return;//stable position
        }
        if (neighbours > 2) {
            world.setBlock(x, y, z, Blocks.air, 0, 3);

        }
        flow(world, x, y, z);

    }


    private static final int searchRadius = 5;

    public void flow(World world, int x, int y, int z) {
        HPoint pos = aStarLowerPosition(world, x, y, z);
        if (pos == null) {
            return;
        }
        pos.x -= x;
        pos.y -= y;
        pos.z -= z;
        System.out.println("Offset: " + pos);
        int amount = amount(world, x, y, z) - 1;
        if (pos.y < 0) {
            set(world, x, y - 1, z, quantaPerBlock - 1);
            return;
        }
        if (sign(pos.x) != 0) {
            set(world, x + sign(pos.x), y, z, amount);
            return;
        }
        if (sign(pos.z) != 0) {
            set(world, x, y, z + sign(pos.z), amount);
        }

    }

    protected void set(World world, int x, int y, int z, int amount) {
        if (amount < 0) {
            world.setBlock(x, y, z, Blocks.air, 0, 3);
            return;
        }
        System.out.println("Set to: "  + (8 - amount));
        world.setBlock(x, y, z, this, quantaPerBlock - amount, 3);
        world.scheduleBlockUpdate(x, y, z, this, 20);
    }

    private HPoint aStarLowerPosition(World world, int x, int y, int z) {
        HPoint pos = new HPoint();
        TLongSet currentPositions = new TLongHashSet(), passed = new TLongHashSet(), nextStep = new TLongHashSet();
        TLongLongMap map = new TLongLongHashMap();
        currentPositions.add(HPoint.toLong(x, y, z));
        passed.addAll(currentPositions);
        for (int i = 0; i < searchRadius; i++) {
            TLongIterator iter = currentPositions.iterator();
            while (iter.hasNext()) {
                long cur = iter.next();
                pos.fromLong(cur);
                if (step(world, cur, pos.toLongOff(0, -1,0 ), passed, nextStep, map)) {
                    int tmp = 0;
                    long last = pos.toLongOff(0, -1, 0);
                    long save = last;
                    while (tmp++ < 40 && map.containsKey(last)) {
                        save = last;
                        pos.fromLong(last);
                        System.out.println(pos);
                        last = map.get(last);
                    }
                    pos.fromLong(save);
                    return pos;
                }
                step(world,  cur, pos.toLongOff(1, 0, 0), passed, nextStep, map);
                step(world,  cur, pos.toLongOff(-1, 0, 0), passed, nextStep, map);
                step(world,  cur, pos.toLongOff(0, 0, 1), passed, nextStep, map);
                step(world,  cur, pos.toLongOff(0, 0, -1), passed, nextStep, map);
            }
            passed.addAll(currentPositions);
            currentPositions = nextStep;
            nextStep = new TLongHashSet();
        }
        if (canFlowIn(world, x + 1, y, z)) {
            return new HPoint(1 + x, y, z);
        }
        if (canFlowIn(world, x -1, y, z)) {
            return new HPoint(-1 + x, y, z);
        }
        if (canFlowIn(world, x , y, z + 1)) {
            return new HPoint(x, y, 1 + z);
        }
        if (canFlowIn(world, x, y, z - 1)) {
            return new HPoint(x, y, -1 + z);
        }
        return null;

    }

    private boolean step(World world,long prev, long pos, TLongSet passed, TLongSet nextStep, TLongLongMap map) {
        if (!passed.contains(pos) && canFlowIn(world, pos)) {
            nextStep.add(pos);
            map.put(pos, prev);
            return true;
        }
        return false;
    }

    static HPoint reader = new HPoint();

    private boolean canFlowIn(World world, long pos) {
        reader.fromLong(pos);
        return canFlowIn(world, reader.x, reader.y, reader.z);
    }

    private boolean canFlowIn(World world, int x, int y, int z) {
//        world.setBlock(x, y + 10, z, Blocks.iron_bars);
        Block block = world.getBlock(x, y, z);
        return block.isAir(world, x, y, z);
    }




    private int amount(IBlockAccess world, int x, int y, int z) {
        return quantaPerBlock - world.getBlockMetadata(x, y, z);
    }

    private boolean isSource(IBlockAccess world, int x, int y, int z) {
        return world.getBlockMetadata(x, y, z) == 0;
    }

    private int neighbours(IBlockAccess world, int x, int y, int z) {
        int out = 0;
        if (world.getBlock(x + 1, y, z) == this) {
            out++;
        }
        if (world.getBlock(x - 1, y, z) == this) {
            out++;
        }
        if (world.getBlock(x, y + 1, z) == this) {
            out++;
        }
        if (world.getBlock(x, y - 1, z) == this) {
            out++;
        }
        if (world.getBlock(x, y, z + 1) == this) {
            out++;
        }
        if (world.getBlock(x, y, z - 1) == this) {
            out++;
        }
        return out;
    }

    private int largestNeighbour(IBlockAccess world, int x, int y, int z) {
        int out = 0;
        out = Math.max(out, getQuantaValue(world, x, y + 1, z));
        out = Math.max(out, getQuantaValue(world, x + 1, y, z));
        out = Math.max(out, getQuantaValue(world, x - 1, y, z));
        out = Math.max(out, getQuantaValue(world, x, y, z + 1));
        out = Math.max(out, getQuantaValue(world, x, y, z - 1));
        return out;
    }


    @Override
    public boolean canCollideCheck(int meta, boolean fullHit) {
        return false;
    }

    @Override
    public int getMaxRenderHeightMeta() {
        return -1;
    }

    @Override
    public int getQuantaValue(IBlockAccess world, int x, int y, int z) {
        if (world.getBlock(x, y, z) == Blocks.air) {
            return 0;
        }

        if (world.getBlock(x, y, z) != this) {
            return -1;
        }

        return quantaPerBlock - world.getBlockMetadata(x, y, z);
    }


    @Override
    public int getRenderColor(int p_149741_1_) {
        return getBlockColor();
    }

    @Override
    public int getBlockColor() {
        return II_Materials.lightWater.getRenderInfo().getColorAsInt(MatterState.Liquid);
    }

    @Override
    public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
        return II_Materials.lightWater.getRenderInfo().getColorAsInt(MatterState.Liquid);
    }

    @Override
    public FluidStack drain(World world, int x, int y, int z, boolean doDrain) {
        return null;
    }

    @Override
    public boolean canDrain(World world, int x, int y, int z) {
        return false;
    }

    @Override
    public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
        return II_Materials.lightWater.getRenderInfo().getTextureSet().liquid.getIcon();
    }
}
