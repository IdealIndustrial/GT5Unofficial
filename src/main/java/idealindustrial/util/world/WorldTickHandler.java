package idealindustrial.util.world;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import idealindustrial.items.tools.ToolDrill;
import idealindustrial.util.energy.electric.system.EnergySystemHandler;
import idealindustrial.util.energy.kinetic.system.KineticSystemHandler;

public class WorldTickHandler {

    public static WorldTickHandler INSTANCE = new WorldTickHandler();

    private WorldTickHandler() {

    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.side == Side.SERVER) {
            if (event.phase == TickEvent.Phase.END) {
                EnergySystemHandler.onTick();
                KineticSystemHandler.onTick();
                ToolDrill.onServerTick();
            }
            else {
                ChunkLoadingMonitor.tickStart();
            }
        }
    }
}
