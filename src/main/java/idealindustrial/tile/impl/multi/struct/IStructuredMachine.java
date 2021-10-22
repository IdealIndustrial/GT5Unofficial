package idealindustrial.tile.impl.multi.struct;

import idealindustrial.util.worldgen.util.Vector3;
import net.minecraft.world.World;

public interface IStructuredMachine {

    int getRotation();

    Vector3 getPosition();

    World getWorld();
}
