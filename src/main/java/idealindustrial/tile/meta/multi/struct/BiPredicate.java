package idealindustrial.tile.meta.multi.struct;

import idealindustrial.util.worldgen.Vector3;

public abstract class BiPredicate implements ICoordPredicate {
    
    protected ICoordPredicate left, right;

    public BiPredicate(ICoordPredicate left, ICoordPredicate right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void resetCounters() {
        left.resetCounters();
        right.resetCounters();
    }

    @Override
    public void checkCounters() {
        left.checkCounters();
        right.checkCounters();
    }

   
}
