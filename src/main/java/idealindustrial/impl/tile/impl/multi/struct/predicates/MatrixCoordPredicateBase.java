package idealindustrial.impl.tile.impl.multi.struct.predicates;

import idealindustrial.impl.tile.impl.multi.struct.CheckMachineParams;
import idealindustrial.impl.tile.impl.multi.struct.IGuideRenderer;
import idealindustrial.impl.world.util.Vector3;
import net.minecraft.world.World;

public abstract class MatrixCoordPredicateBase implements MatrixCoordPredicate {

    protected char ch;

    @Override
    public final void apply(CheckMachineParams params, Vector3 position, int rotation) {
        World w = params.world;
        switch (params.mode) {
            case BUILD:
                build(params, w, position);
                break;
            case CHECK:
                check(params, w, position);
                break;
            case RENDER:
                render(params, w, position, params.renderer);
                break;
            case EVAL:
                visit(params, position);
                break;
        }
    }

    @Override
    public void setChar(char ch) {
        assert this.ch == 0;
        this.ch = ch;
    }

    protected abstract void build(CheckMachineParams params, World w, Vector3 position);

    protected abstract void render(CheckMachineParams params, World w, Vector3 position, IGuideRenderer renderer);

    protected abstract void check(CheckMachineParams params, World w, Vector3 position);

    private void visit(CheckMachineParams params, Vector3 position) {
        params.visitor.visit(ch, position);
    }
}
