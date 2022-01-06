package idealindustrial.impl.tile.impl.multi.struct.predicates;

public abstract class BiPredicate implements MatrixCoordPredicate {
    
    protected MatrixCoordPredicate left, right;

    public BiPredicate(MatrixCoordPredicate left, MatrixCoordPredicate right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void setChar(char ch) {
        left.setChar(ch);
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
