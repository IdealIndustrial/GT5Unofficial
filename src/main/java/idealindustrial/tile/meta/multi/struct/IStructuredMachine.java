package idealindustrial.tile.meta.multi.struct;

import idealindustrial.util.worldgen.Vector3;
import net.minecraft.world.World;

public interface IStructuredMachine {

    int getRotation();

    Vector3 getPosition();

    World getWorld();
}
