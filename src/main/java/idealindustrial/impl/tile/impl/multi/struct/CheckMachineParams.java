package idealindustrial.impl.tile.impl.multi.struct;

import net.minecraft.world.World;

public class CheckMachineParams {
    public enum CheckMode {
        BUILD, CHECK, RENDER
    }

    CheckMode mode;
    World world;
    IStructuredMachine multiTile;
    IGuideRenderer renderer;

    public CheckMachineParams(CheckMode mode, IStructuredMachine multiTile, IGuideRenderer renderer) {
        this.mode = mode;
        this.world = multiTile.getWorld();
        this.multiTile = multiTile;
        this.renderer = renderer;
    }
}
