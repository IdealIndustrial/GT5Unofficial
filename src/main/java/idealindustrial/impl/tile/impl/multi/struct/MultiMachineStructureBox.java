package idealindustrial.impl.tile.impl.multi.struct;

import idealindustrial.impl.world.util.CoordinateMatrix;
import idealindustrial.impl.world.util.Vector3;

import java.util.ArrayList;

public class MultiMachineStructureBox extends CoordinateMatrix<CheckMachineParams, MatrixCoordPredicate> {

    public MultiMachineStructureBox(MatrixCoordPredicate[][][] matrix, Vector3 controller) {
        this.matrix = matrix;
        this.center = controller;
        this.subMatrices = new ArrayList<>();
    }


}
