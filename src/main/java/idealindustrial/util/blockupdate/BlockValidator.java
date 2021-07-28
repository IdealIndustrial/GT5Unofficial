package idealindustrial.util.blockupdate;

import net.minecraft.world.World;

public interface BlockValidator {

    boolean isValid(World world, int x, int y, int z);
}
