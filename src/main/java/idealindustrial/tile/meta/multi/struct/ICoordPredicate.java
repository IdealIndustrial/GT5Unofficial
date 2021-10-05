package idealindustrial.tile.meta.multi.struct;

import idealindustrial.tile.meta.multi.BaseMultiMachine;
import idealindustrial.util.worldgen.ICoordConsumer;
import net.minecraft.block.Block;

public interface ICoordPredicate extends ICoordConsumer<CheckMachineParams> {
    
    void resetCounters();
    
    void checkCounters();
    
    default ICoordPredicate and(ICoordPredicate predicate) {
        return new AndPredicate(this, predicate);
    }
    
    default ICoordPredicate or(ICoordPredicate predicate) {
        return new OrPredicate(this, predicate);
    }

    default ICoordPredicate orBlock(Block block, int meta) {
        return orBlock(block, meta, 0);
    }

    default ICoordPredicate orBlock(Block block, int meta, int minAmount) {
        return or(new DirectBlockPredicate(block, meta, minAmount));
    }

    default ICoordPredicate orHatch(BaseMultiMachine.HatchType... types) {
        return or(new HatchPredicate(types));
    }

}
