package idealindustrial.impl.tile.impl.multi.struct.predicates;

import idealindustrial.impl.tile.impl.multi.struct.CheckMachineParams;
import idealindustrial.impl.tile.impl.multi.struct.MachineStructureException;
import idealindustrial.impl.world.util.Vector3;

public class OrPredicate extends BiPredicate {
    public OrPredicate(MatrixCoordPredicate left, MatrixCoordPredicate right) {
        super(left, right);
    }

    @Override
    public void apply(CheckMachineParams mode, Vector3 position, int rotation) {
        try {
            left.apply(mode, position, rotation);
        } catch (MachineStructureException e) {
            try {
                right.apply(mode, position, rotation);
            } catch (MachineStructureException.NotEnoughInfoException ignored) {
                throw e;
            }
        }
    }
}
