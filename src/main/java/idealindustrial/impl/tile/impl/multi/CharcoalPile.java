package idealindustrial.impl.tile.impl.multi;

import idealindustrial.api.textures.ITexture;
import idealindustrial.api.tile.host.HostMachineTile;
import idealindustrial.api.tile.meta.Tile;
import idealindustrial.impl.blocks.II_Blocks;
import idealindustrial.impl.item.stack.HashedBlock;
import idealindustrial.impl.textures.TextureUtil;
import idealindustrial.impl.textures.Textures;
import idealindustrial.impl.tile.impl.multi.struct.MachineShapeBuilder;
import idealindustrial.impl.tile.impl.multi.struct.MultiMachineShape;
import idealindustrial.util.misc.II_StreamUtil;
import idealindustrial.util.misc.ItemHelper;
import net.minecraft.init.Blocks;

import java.util.Set;
import java.util.stream.IntStream;

import static idealindustrial.impl.blocks.II_Blocks.SpecialBlocks.CoalChunks;

public class CharcoalPile extends MultiMachineBase<HostMachineTile> {

    private static final int maxSize = 9;
    private static final int maxProgress = 60 * 10 * 20;
    private static Set<HashedBlock> baseBlocks = ItemHelper.set(new HashedBlock(Blocks.brick_block, 0));
    private static Set<HashedBlock> dirtBlocks = ItemHelper.set(new HashedBlock(Blocks.dirt, 0), new HashedBlock(Blocks.grass, 0));
    private static Set<HashedBlock> woodBlocks = ItemHelper.set(HashedBlock.anyMeta(Blocks.log));
    static String[][][] shapes = IntStream.iterate(5, i -> i + 2).limit(3).mapToObj(CharcoalPile::makeShape).toArray(String[][][]::new);

    int progressTime = 0;


    private static final String engName = "Charcoal Pile Igniter";
    public CharcoalPile(HostMachineTile baseTile) {
        super(baseTile, engName,  II_StreamUtil.arrayOf(Textures.baseTiredTextures[1], new ITexture[10]), TextureUtil.loadMultiMachineTextures(engName));
    }

    public CharcoalPile(HostMachineTile baseTile, MultiMachineBase<?> copyFrom) {
        super(baseTile, copyFrom);
    }

    @Override
    public void onPostTick(long timer, boolean serverSide) {
        if (!serverSide) {
            return;
        }
        if ((timer & 32) == 32) {
            update();
        }
    }

    private void update() {
        boolean machine = checkMachine();
        if (!machine) {
            progressTime = 0;
            hostTile.setActive(false);
            return;
        }
        progressTime += 32;
        if (progressTime >= maxProgress) {
            MultiMachineShape shape = constructShape();
            assert shape != null;
            shape.execute(this, (ch, pos) -> {
                if (ch != 'W') {
                    return;
                }
                hostTile.setBlock(pos, CoalChunks.getBlock());
            });
            progressTime = 0;
        }
        hostTile.setActive(progressTime > 0);
    }

    @Override
    public Tile<HostMachineTile> newMetaTile(HostMachineTile baseTile) {
        return new CharcoalPile(baseTile, this);
    }

    @Override
    protected boolean checkMachine() {
        MultiMachineShape shape = constructShape();
        if (shape == null) {
            return false;
        }
        return shape.checkMachine(this);
    }

    private MultiMachineShape constructShape() {
        int size = getSize() + 1;
        if (size < 5 || size > 9 || size % 2 == 0) {
            return null;
        }
        String[][] shapeStr = shapes[(size - 5) / 2];
        return MachineShapeBuilder.start().addShape(shapeStr)
                .define('R', blocksPredicate(dirtBlocks))
                .define('B', blocksPredicate(baseBlocks))
                .define('W', blocksPredicate(woodBlocks))
                .added().create();
    }

    protected int getSize() {
        for (int i = 1; i < maxSize; i++) {
            HashedBlock b = hostTile.getHBlockOffset(0, -i, 0);
            if (baseBlocks.contains(b)) {
                return i;
            }
        }
        return -1;
    }


    @Override
    protected MultiMachineShape getStructure() {
        return MultiMachineShape.EmptyShape;
    }

    private static String[][] makeShape(int size) {
        int mid = size / 2;
        String[][] out = new String[size][size];
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                StringBuilder builder = new StringBuilder();
                for (int z = 0; z < size; z++) {
                    if (y == 0) {
                        if (x == mid && z == mid) {
                            builder.append('c');
                        } else {
                            builder.append('R');
                        }
                    } else if (y == size - 1) {
                        builder.append('B');
                    } else {
                        if (x == 0 || z == 0 || x == size - 1 || z == size - 1) {
                            builder.append('R');
                        } else {
                            builder.append('W');
                        }

                    }
                }
                out[y][x] = builder.toString();
            }
        }
        return out;
    }

    public static void main(String[] args) {
        String[][] v = makeShape(5);
        int a = 0;
    }
}
