package gregtech.api.net;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

import java.util.HashMap;

public class GT_Packet_Key extends GT_Packet {

    int playerId;
    int dimensionId;
    boolean alt;
    boolean ctrl;

    public GT_Packet_Key(){
        super(true);
    }

    public GT_Packet_Key(int playerId, int dimensionId, boolean alt, boolean ctrl){
        super(false);
        this.playerId = playerId;
        this.alt = alt;
        this.ctrl = ctrl;
        this.dimensionId = dimensionId;
    }

    class PressedKeys {
        PressedKeys(boolean alt, boolean ctrl){
            this.alt = alt;
            this.ctrl = ctrl;
        }
        public boolean alt = false;
        public boolean ctrl = false;
    }

    public static GT_Packet_Key inst = new GT_Packet_Key(false);

    public static HashMap<EntityPlayer, PressedKeys> pressedKeys = new HashMap<>();

    public GT_Packet_Key(boolean aIsReference) {
        super(aIsReference);
    }

    @Override
    public byte getPacketID() {
        return 9;
    }

    @Override
    public byte[] encode() {
        ByteArrayDataOutput tOut = ByteStreams.newDataOutput(10);
        tOut.writeInt(playerId);
        tOut.writeInt(dimensionId);
        tOut.writeBoolean(alt);
        tOut.writeBoolean(ctrl);
        return tOut.toByteArray();
    }

    @Override
    public GT_Packet decode(ByteArrayDataInput aData) {
        return new GT_Packet_Key(aData.readInt(), aData.readInt(), aData.readBoolean(), aData.readBoolean());
    }

    @Override
    public void process(IBlockAccess aWorld) {
        World w = DimensionManager.getWorld(dimensionId);
        if (w != null) {
            Entity anEntity = w.getEntityByID(playerId);
            if(anEntity instanceof EntityPlayer) {
                pressedKeys.put((EntityPlayer)anEntity, new PressedKeys(alt, ctrl));
            }
        }
    }

    public static boolean isAltPressed(EntityPlayerMP aPlayer) {
        if(pressedKeys.get(aPlayer) == null) return false;
        return pressedKeys.get(aPlayer).alt;
    }

    public static boolean isCtrlPressed(EntityPlayer aPlayer) {
        if(pressedKeys.get(aPlayer) == null) return false;
        return pressedKeys.get(aPlayer).ctrl;
    }

}
