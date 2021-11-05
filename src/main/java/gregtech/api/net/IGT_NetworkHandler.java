package gregtech.api.net;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

public interface IGT_NetworkHandler {
    void sendToPlayer(GT_Packet aPacket, EntityPlayerMP aPlayer);

    void sendToAllAround(GT_Packet aPacket, TargetPoint aPosition);

    void sendToServer(GT_Packet aPacket);

    void sendPacketToAllPlayersInRange(World aWorld, GT_Packet aPacket, int aX, int aZ);
}