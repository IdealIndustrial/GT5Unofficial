package idealindustrial.tile.meta.multi.struct;

import idealindustrial.util.worldgen.ICoordConsumer;

public interface ICoordPredicate extends ICoordConsumer<CheckMachineParams> {
    
    void resetCounters();
    
    void checkCounters();
    
    default ICoordPredicate and(ICoordPredicate predicate) {
        return new AndPredicate(this, predicate);
    }
    
    default ICoordPredicate or(ICoordPredicate predicate) {
        return new OrPredicate(this, predicate);
    }

}
