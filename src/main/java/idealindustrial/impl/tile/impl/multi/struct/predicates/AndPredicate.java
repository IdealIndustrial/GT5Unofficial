package idealindustrial.impl.tile.impl.multi.struct.predicates;

import idealindustrial.impl.tile.impl.multi.struct.CheckMachineParams;
import idealindustrial.impl.world.util.Vector3;

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
