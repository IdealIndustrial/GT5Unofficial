package idealindustrial.util.world;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;

public class II_WorldTickHandler {

    public static II_WorldTickHandler INSTANCE = new II_WorldTickHandler();

    private II_WorldTickHandler() {

    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && event.side == Side.SERVER) {
            II_EnergySystemHandler.onTick();
        }
    }
}
