package idealindustrial.tile.impl.multi.struct;

import idealindustrial.util.worldgen.util.Vector3;

public class AndPredicate extends BiPredicate {
    public AndPredicate(MatrixCoordPredicate left, MatrixCoordPredicate right) {
        super(left, right);
    }

    @Override
    public void apply(CheckMachineParams mode, Vector3 position, int rotation) {
        left.apply(mode, position, rotation);
        right.apply(mode, position, rotation);
    }
}