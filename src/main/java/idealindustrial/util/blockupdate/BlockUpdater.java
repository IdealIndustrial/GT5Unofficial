package idealindustrial.util.blockupdate;

import net.minecraft.world.World;

public interface BlockUpdater {
    void update(World world, int x, int y, int z);
}
