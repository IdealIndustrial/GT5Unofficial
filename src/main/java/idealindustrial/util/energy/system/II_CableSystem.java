package idealindustrial.util.energy.system;

import idealindustrial.tile.meta.connected.II_MetaConnected_Cable;
import net.minecraft.world.World;

public class II_CableSystem {
    protected boolean invalid = false;

    public void invalidate() {
        invalid = true;
    }

    public void init(II_MetaConnected_Cable cable) {

    }



}
