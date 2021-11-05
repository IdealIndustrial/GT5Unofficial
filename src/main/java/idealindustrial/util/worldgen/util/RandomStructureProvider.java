package idealindustrial.util.worldgen.util;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraftforge.common.util.RotationHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//todo: support render mode
public abstract class RandomStructureProvider implements IRandomStructure<RandomStructureProvider.Parameters> {

    private final List<MatrixCoordConsumer<Parameters>> added = new ArrayList<>();

    protected abstract void pushStructure(Random random);


    @Override
    public ICoordManipulator<Parameters> provideManipulator(Random random) {
        added.clear();
        pushStructure(random);

        return null;
    }

    protected void setBlock(Vector3 position, int rotation, Block block, int meta) {
        added.add(new BlockEntry(position, rotation, block, meta));
    }

    protected void setBlock(Vector3 position, Block block, int meta) {
        setBlock(position, 0, block, meta);
    }

    protected void setBlock(Vector3 position, Block block) {
        setBlock(position, 0, block, 0);
    }

    protected void setBlock(int x ,int y, int z, Block block, int meta) {
        setBlock(new Vector3(x, y,z), block, meta);
    }

    protected void setBlock(int x ,int y, int z, Block block) {
        setBlock(new Vector3(x, y,z), block, 0);
    }


    protected void setFunc(Vector3 position, CoordFunc function) {
        added.add(new FuncEntry(position, function));
    }

    protected void setFunc(int x, int y, int z, CoordFunc function) {
        setFunc(new Vector3(x, y, z), function);
    }



    private static class FuncEntry implements MatrixCoordConsumer<Parameters> {
        CoordFunc coordFunc;
        Vector3 position;
        public FuncEntry(Vector3 position, CoordFunc coordFunc) {
            this.coordFunc = coordFunc;
            this.position = position;
        }

        @Override
        public void apply(Parameters mode, Vector3 position, int rotation) {
            coordFunc.apply(mode.world, position, rotation);
        }
    }

    private static class BlockEntry implements MatrixCoordConsumer<Parameters> {

        Vector3 position;
        int rotation;

        Block block;
        int meta;

        public BlockEntry(Vector3 position, int rotation, Block block, int meta) {
            this.block = block;
            this.meta = meta;
            this.rotation = rotation;
            this.position = position;
        }

        @Override
        public void apply(Parameters parameters, Vector3 position, int rotation) {
            parameters.world.setBlock(position.x, position.y, position.z, block, meta, 3);
            //todo imp rotation
        }
    }

    public enum Mode {
        BUILD, RENDER;
    }

    public static class Parameters {
        Mode mode;
        World world;
    }

    public interface CoordFunc {
        void apply(World world, Vector3 position, int rotation);
    }
}
