package idealindustrial.util.misc;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.world.BlockEvent;

public class II_EventUtil {

    public static boolean canBreak(EntityPlayer player, int x, int y, int z) {
        if (player instanceof EntityPlayerMP) {
            BlockEvent.BreakEvent event = ForgeHooks.onBlockBreakEvent(player.worldObj, ((EntityPlayerMP) player).theItemInWorldManager.getGameType(), (EntityPlayerMP) player, x, y, z);
            if (event.isCanceled()) {
                ((EntityPlayerMP) player).playerNetServerHandler.sendPacket(new S23PacketBlockChange(x, y, z, player.worldObj));
                return false;
            }

        }
        return true;
    }
}
