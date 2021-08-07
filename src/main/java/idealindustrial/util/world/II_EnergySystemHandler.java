package idealindustrial.util.world;

import idealindustrial.tile.interfaces.base.II_BaseMachineTile;
import idealindustrial.tile.interfaces.base.II_BaseTile;
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
            try {
                cableSystem.update();
            }
            catch (Throwable e) {
                e.printStackTrace();//shitty, but only for debug
            }
        }
    }

    /**
     * creates energy system from producer tile
     * @param tile - producer tile
     * @param side - side of tile with producer
     */
    public static void initSystem(II_BaseMachineTile tile, int side) {
        try {
            systems.add(new II_CableSystem(tile, side));
        }
        catch (Throwable e) {
            e.printStackTrace();//same as top one
        }
    }
}
