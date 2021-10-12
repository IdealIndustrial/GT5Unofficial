package idealindustrial.util.energy.electric.system;

import idealindustrial.tile.interfaces.host.HostMachineTile;
import idealindustrial.util.energy.electric.system.CableSystem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EnergySystemHandler {

    protected static List<CableSystem> systems = new ArrayList<>();

    public static void onTick() {
        for (Iterator<CableSystem> iterator = systems.iterator(); iterator.hasNext(); ) {
            CableSystem cableSystem = iterator.next();
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
    public static void initSystem(HostMachineTile tile, int side) {
        try {
            systems.add(new CableSystem(tile, side));
        }
        catch (Throwable e) {
            e.printStackTrace();//same as top one
        }
    }
}
