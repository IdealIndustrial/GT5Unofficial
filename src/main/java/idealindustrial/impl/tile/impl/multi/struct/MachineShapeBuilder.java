package idealindustrial.impl.tile.impl.multi.struct;

import idealindustrial.impl.tile.impl.multi.struct.predicates.DirectBlockPredicate;
import idealindustrial.impl.tile.impl.multi.struct.predicates.MatrixCoordPredicate;
import idealindustrial.impl.tile.impl.multi.struct.predicates.TruePredicate;
import idealindustrial.impl.world.util.Vector3;
import net.minecraft.init.Blocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class MachineShapeBuilder {
    List<MultiMachineStructureBox> boxes = new ArrayList<>();
    List<Consumer<IStructuredMachine>> predicates = new ArrayList<>();


    public static MachineShapeBuilder start() {
        return new MachineShapeBuilder();
    }

    public ShapeAdder addShape(String[][] shape) {
        return new ShapeAdder(shape);
    }

    public MultiMachineShape create() {
        return new MultiMachineShape(boxes, predicates);
    }

    public class ShapeAdder {
        String[][] shape;
        Map<Character, MatrixCoordPredicate> signatureMap = new HashMap<>();
        boolean xInv, zInv;

        public ShapeAdder(String[][] shape) {
            this.shape = new String[shape.length][shape[0].length];
            for (int y = 0; y < shape.length; y++) {
                this.shape[y] = shape[shape.length - y- 1];
            }
//            this.shape = shape;
        }

        public ShapeAdder define(char ch, MatrixCoordPredicate predicate) {
            predicate.setChar(ch);
            signatureMap.put(ch, predicate);
            return this;
        }

        public MachineShapeBuilder added() {
            addToStruct();
            return MachineShapeBuilder.this;
        }

        public ShapeAdder setInversions(boolean x, boolean z) {
            xInv = x;
            zInv = z;
            return this;
        }

        private void addToStruct() {
            Vector3 controllerCoords = null;
            int xSize = shape[0][0].length();
            int zSize = shape[0].length;
            int ySize = shape.length;
            MatrixCoordPredicate[][][] predicates = new MatrixCoordPredicate[xSize][ySize][zSize];
            for (int y = 0; y < ySize; y++) {
                for (int z = 0; z < zSize; z++) {
                    for (int x = 0; x < xSize; x++) {
                        char ch = shape[y][z].charAt(x);
                        MatrixCoordPredicate predicate = signatureMap.get(ch);
                        if (predicate == null) {
                            if (ch == 'c') {
                                predicate = new TruePredicate();
                                controllerCoords = new Vector3(x, y, z);
                            } else if (ch == 'a') {
                                predicate = new DirectBlockPredicate(Blocks.air, 0, 0);
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
            boxes.add(new MultiMachineStructureBox(predicates, controllerCoords, xInv, zInv));
        }
    }

    protected static MatrixCoordPredicate getSpecialPredicate(char ch) {
        return null;//todo impl
    }

// *   static { //example
//        new MachineStructureBuilder()
//                .addShape(new String[][]{
//                        {
//                                "AAA",
//                                "AAA",
//                                "AAA"
//                        },
//                        {
//                                "AAA",
//                                "AaA",
//                                "AcA"
//                        },
//                        {
//                                "BBB",
//                                "BBB",
//                                "BBB"
//                        }
//
//                })
//                .define('A', blockPredicate(Blocks.iron_block, -1))
//                .define('B', blockPredicate(Blocks.bedrock, -1))
//                .define('a', blockPredicate(Blocks.air, -1)).addToStruct();
//  *  }


}
