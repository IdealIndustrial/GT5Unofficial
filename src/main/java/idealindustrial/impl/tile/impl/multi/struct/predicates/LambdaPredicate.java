package idealindustrial.impl.tile.impl.multi.struct.predicates;

import idealindustrial.impl.tile.impl.multi.struct.CheckMachineParams;
import idealindustrial.impl.tile.impl.multi.struct.IGuideRenderer;
import idealindustrial.impl.tile.impl.multi.struct.MachineStructureException;
import idealindustrial.impl.world.util.Vector3;
import net.minecraft.world.World;

public class LambdaPredicate extends MatrixCoordPredicateBase {

    public interface WorldPredicate {
        boolean apply(World world, int x, int y, int z);
    }

    final WorldPredicate predicate;

    public LambdaPredicate(WorldPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public void resetCounters() {

    }

    @Override
    public void checkCounters() {

    }

    @Override
    protected void build(CheckMachineParams params, World w, Vector3 position) {

    }

    @Override
    protected void render(CheckMachineParams params, World w, Vector3 position, IGuideRenderer renderer) {

    }

    @Override
    protected void check(CheckMachineParams params, World w, Vector3 position) {
        if (!predicate.apply(w, position.x, position.y, position.z)) {
            MachineStructureException.invalidBlockAt(position);
        }
    }
}
