package idealindustrial.tile.impl.multi.struct;

public abstract class BiPredicate implements MatrixCoordPredicate {
    
    protected MatrixCoordPredicate left, right;

    public BiPredicate(MatrixCoordPredicate left, MatrixCoordPredicate right) {
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
