package idealindustrial.tile.impl.multi.struct;

import idealindustrial.tile.impl.multi.MultiMachineBase;
import idealindustrial.util.worldgen.util.MatrixCoordConsumer;
import net.minecraft.block.Block;

public interface MatrixCoordPredicate extends MatrixCoordConsumer<CheckMachineParams> {
    
    void resetCounters();
    
    void checkCounters();
    
    default MatrixCoordPredicate and(MatrixCoordPredicate predicate) {
        return new AndPredicate(this, predicate);
    }
    
    default MatrixCoordPredicate or(MatrixCoordPredicate predicate) {
        return new OrPredicate(this, predicate);
    }

    default MatrixCoordPredicate orBlock(Block block, int meta) {
        return orBlock(block, meta, 0);
    }

    default MatrixCoordPredicate orBlock(Block block, int meta, int minAmount) {
        return or(new DirectBlockPredicate(block, meta, minAmount));
    }

    default MatrixCoordPredicate orHatch(MultiMachineBase.HatchType... types) {
        return or(new HatchPredicate(types));
    }

}
