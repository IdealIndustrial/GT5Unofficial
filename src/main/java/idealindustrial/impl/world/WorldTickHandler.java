package idealindustrial.impl.world;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import idealindustrial.impl.item.tools.ToolWithBreakingMatrix;
import idealindustrial.impl.tile.energy.electric.system.EnergySystemHandler;
import idealindustrial.impl.tile.energy.kinetic.system.KineticSystemHandler;

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
                ToolWithBreakingMatrix.onServerTick();
            }
            else {
                ChunkLoadingMonitor.tickStart();
            }
        }
    }
}
