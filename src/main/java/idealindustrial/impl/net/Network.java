package idealindustrial.impl.net;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.common.network.FMLOutboundHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import cpw.mods.fml.relauncher.Side;
import gregtech.api.net.*;
import idealindustrial.impl.tile.host.PacketCover;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.MessageToMessageCodec;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import java.util.EnumMap;
import java.util.List;

@ChannelHandler.Sharable
public class Network
        extends MessageToMessageCodec<FMLProxyPacket, GT_Packet>
        implements IGT_NetworkHandler {
    private final EnumMap<Side, FMLEmbeddedChannel> mChannel;
    private final GT_Packet[] mSubChannels;
    public static Network NW = new Network();

    public Network() {
        this.mChannel = NetworkRegistry.INSTANCE.newChannel("II_Core", this, new HandlerShared());
        this.mSubChannels = new GT_Packet[]{null, null, new GT_Packet_Block_Event(),null,null, new GT_Packet_ExtendedBlockEvent_Server(), new GT_Packet_ExtendedBlockEvent(), new GT_Packet_ByteStream(), new PacketCover()};
    }

    protected void encode(ChannelHandlerContext aContext, GT_Packet aPacket, List<Object> aOutput) {
        aOutput.add(new FMLProxyPacket(Unpooled.buffer().writeByte(aPacket.getPacketID()).writeBytes(aPacket.encode()).copy(), (String) aContext.channel().attr(NetworkRegistry.FML_CHANNEL).get()));
    }

    protected void decode(ChannelHandlerContext aContext, FMLProxyPacket aPacket, List<Object> aOutput) {
        ByteArrayDataInput aData = ByteStreams.newDataInput(aPacket.payload().array());
        aOutput.add(this.mSubChannels[aData.readByte()].decode(aData));
    }

    public void sendToPlayer(GT_Packet aPacket, EntityPlayerMP aPlayer) {
    	if(aPacket==null){
    		System.out.println("packet null");return;
    	}
    	if(aPlayer==null){
    		System.out.println("player null");return;
    	}
        ((FMLEmbeddedChannel) this.mChannel.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER);
        ((FMLEmbeddedChannel) this.mChannel.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(aPlayer);
        ((FMLEmbeddedChannel) this.mChannel.get(Side.SERVER)).writeAndFlush(aPacket);
    }

    public void sendToAllAround(GT_Packet aPacket, NetworkRegistry.TargetPoint aPosition) {
        ((FMLEmbeddedChannel) this.mChannel.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALLAROUNDPOINT);
        ((FMLEmbeddedChannel) this.mChannel.get(Side.SERVER)).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(aPosition);
        ((FMLEmbeddedChannel) this.mChannel.get(Side.SERVER)).writeAndFlush(aPacket);
    }

    public void sendToServer(GT_Packet aPacket) {
        ((FMLEmbeddedChannel) this.mChannel.get(Side.CLIENT)).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
        ((FMLEmbeddedChannel) this.mChannel.get(Side.CLIENT)).writeAndFlush(aPacket);
    }

    public void sendPacketToAllPlayersInRange(World aWorld, GT_Packet aPacket, int aX, int aZ) {
        if (!aWorld.isRemote) {
            for (Object tObject : aWorld.playerEntities) {
                if (!(tObject instanceof EntityPlayerMP)) {
                    break;
                }
                EntityPlayerMP tPlayer = (EntityPlayerMP) tObject;
                Chunk tChunk = aWorld.getChunkFromBlockCoords(aX, aZ);
                if (tPlayer.getServerForPlayer().getPlayerManager().isPlayerWatchingChunk(tPlayer, tChunk.xPosition, tChunk.zPosition)) {
                    sendToPlayer(aPacket, tPlayer);
                }
            }
        }
    }

    @ChannelHandler.Sharable
    static final class HandlerShared
            extends SimpleChannelInboundHandler<GT_Packet> {
        protected void channelRead0(ChannelHandlerContext ctx, GT_Packet aPacket)
                throws Exception {
            EntityPlayer aPlayer = Minecraft.getMinecraft().thePlayer;
            try {
                aPacket.process(aPlayer == null ? null : aPlayer.worldObj);
            }catch ( Exception e) {
                e.printStackTrace();
            }
        }
    }
}
