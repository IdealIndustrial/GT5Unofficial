package idealindustrial.impl.tile.impl.multi.struct;

import idealindustrial.impl.world.util.Vector3;
import net.minecraft.world.World;

public interface IStructuredMachine {

    int getRotation();

    Vector3 getPosition();

    World getWorld();
}
