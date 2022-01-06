package idealindustrial.impl.tile.impl.multi.struct;

import idealindustrial.impl.tile.impl.multi.struct.predicates.MatrixCoordPredicate;
import idealindustrial.impl.world.util.CoordinateMatrix;
import idealindustrial.impl.world.util.Vector3;

import java.util.ArrayList;

public class MultiMachineStructureBox extends CoordinateMatrix<CheckMachineParams, MatrixCoordPredicate> {

    boolean canBeXInverted;
    boolean canBeZInverted;


    public MultiMachineStructureBox(MatrixCoordPredicate[][][] matrix, Vector3 controller, boolean canBeXInverted, boolean canBeZInverted) {
        this.matrix = matrix;
        this.center = controller;
        this.subMatrices = new ArrayList<>();
        this.canBeXInverted = canBeXInverted;
        this.canBeZInverted = canBeZInverted;
    }

    public boolean canBeXInverted() {
        return canBeXInverted;
    }

    public boolean canBeZInverted() {
        return canBeZInverted;
    }
}
