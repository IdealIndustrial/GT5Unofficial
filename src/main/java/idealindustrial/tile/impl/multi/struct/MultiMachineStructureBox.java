package idealindustrial.tile.impl.multi.struct;

import idealindustrial.util.worldgen.CoordinateMatrix;
import idealindustrial.util.worldgen.Vector3;

import java.util.ArrayList;

public class MultiMachineStructureBox extends CoordinateMatrix<CheckMachineParams, ICoordPredicate> {

    public MultiMachineStructureBox(ICoordPredicate[][][] matrix, Vector3 controller) {
        this.matrix = matrix;
        this.center = controller;
        this.subMatrices = new ArrayList<>();
    }


}
