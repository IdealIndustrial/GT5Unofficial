package idealindustrial.impl.tile.impl.multi.struct;

import idealindustrial.impl.world.util.Vector3;
import net.minecraft.world.World;

public class CheckMachineParams {
    public enum CheckMode {
        BUILD, CHECK, RENDER, EVAL
    }

    public CheckMode mode;
    public World world;
    public IStructuredMachine multiTile;
    public IGuideRenderer renderer;
    public CoordVisitor visitor;

    public CheckMachineParams(CheckMode mode, IStructuredMachine multiTile) {
        this.mode = mode;
        this.world = multiTile.getWorld();
        this.multiTile = multiTile;
    }

    public CheckMachineParams setRenderer(IGuideRenderer renderer) {
        this.renderer = renderer;
        return this;
    }

    public CheckMachineParams setVisitor(CoordVisitor visitor) {
        this.visitor = visitor;
        return this;
    }

    public interface CoordVisitor {
        void visit(char ch, Vector3 pos);
    }
}
