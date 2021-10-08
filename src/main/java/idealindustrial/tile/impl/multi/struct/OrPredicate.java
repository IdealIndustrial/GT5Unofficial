package idealindustrial.tile.impl.multi.struct;

import idealindustrial.util.worldgen.Vector3;

public class OrPredicate extends BiPredicate {
    public OrPredicate(ICoordPredicate left, ICoordPredicate right) {
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
