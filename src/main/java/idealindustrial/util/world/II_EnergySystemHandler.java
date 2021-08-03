package idealindustrial.util.world;

import idealindustrial.tile.base.II_BaseTile;
import idealindustrial.util.energy.system.II_CableSystem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class II_EnergySystemHandler {

    protected static List<II_CableSystem> systems = new ArrayList<>();

    public static void onTick() {
        for (Iterator<II_CableSystem> iterator = systems.iterator(); iterator.hasNext(); ) {
            II_CableSystem cableSystem = iterator.next();
            if (!cableSystem.isValid()) {
                iterator.remove();
            }
            cableSystem.update();
        }
    }

    /**
     * creates energy system from producer tile
     * @param tile - producer tile
     * @param side - side of tile with producer
     */
    public static void initSystem(II_BaseTile tile, int side) {
        systems.add(new II_CableSystem(tile, side));
    }
}
