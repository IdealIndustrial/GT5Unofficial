package idealindustrial.tile.meta.multi.struct;

import idealindustrial.util.worldgen.Vector3;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class MachineStructureBuilder<MachineTile extends IStructuredMachine> {
    List<MultiMachineStructureBox> boxes = new ArrayList<>();
    List<Consumer<MachineTile>> predicates = new ArrayList<>();

    public ShapeAdder addShape(String[][] shape) {
        return new ShapeAdder(shape);
    }

    public MultiMachineShape<MachineTile> create() {
        return new MultiMachineShape<>(boxes, predicates);
    }

    protected class ShapeAdder {
        String[][] shape;
        Map<Character, ICoordPredicate> signatureMap = new HashMap<>();

        public ShapeAdder(String[][] shape) {
            this.shape = shape;
        }

        public ShapeAdder define(char ch, ICoordPredicate predicate) {
            signatureMap.put(ch, predicate);
            return this;
        }

        public MachineStructureBuilder<MachineTile> added() {
            addToStruct();
            return MachineStructureBuilder.this;
        }

        private void addToStruct() {
            Vector3 controllerCoords = null;
            int xSize = shape[0][0].length();
            int zSize = shape[0].length;
            int ySize = shape.length;
            ICoordPredicate[][][] predicates = new ICoordPredicate[xSize][ySize][zSize];
            for (int y = 0; y < ySize; y++) {
                for (int z = 0; z < zSize; z++) {
                    for (int x = 0; x < xSize; x++) {
                        char ch = shape[y][z].charAt(x);
                        ICoordPredicate predicate = signatureMap.get(ch);
                        if (predicate == null) {
                            if (ch == 'c') {
                                predicate = new TruePredicate();
                                controllerCoords = new Vector3(x, y, z);
                            } else {
                                predicate = getSpecialPredicate(ch);
                            }
                        }
                        assert predicate != null : "no predicate for char: " + ch;
                        predicates[x][y][z] = predicate;
                    }
                }
            }
            assert controllerCoords != null : "no controller found in struct";
            boxes.add(new MultiMachineStructureBox(predicates, controllerCoords));
        }
    }

    protected static ICoordPredicate getSpecialPredicate(char ch) {
        return null;//todo impl
    }

    public static DirectBlockPredicate blockPredicate(Block block, int meta) {
        return new DirectBlockPredicate(block, meta, 0);
    }

    public static DirectBlockPredicate blockPredicate(Block block, int meta, int minAmount) {
        return new DirectBlockPredicate(block, meta, minAmount);
    }

    static {
        new MachineStructureBuilder<IStructuredMachine>()
                .addShape(new String[][]{
                        {
                                "AAA",
                                "AAA",
                                "AAA"
                        },
                        {
                                "AAA",
                                "AaA",
                                "AcA"
                        },
                        {
                                "BBB",
                                "BBB",
                                "BBB"
                        }

                })
                .define('A', blockPredicate(Blocks.iron_block, -1))
                .define('B', blockPredicate(Blocks.bedrock, -1))
                .define('a', blockPredicate(Blocks.air, -1)).addToStruct();
    }


}
