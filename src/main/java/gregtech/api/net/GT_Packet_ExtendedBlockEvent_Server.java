package gregtech.api.net;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.DimensionManager;

public class GT_Packet_ExtendedBlockEvent_Server extends GT_Packet {
    private int x, y, z, d;
    private int id;
    private int value;

    public GT_Packet_ExtendedBlockEvent_Server() {
        super(true);
    }

    public GT_Packet_ExtendedBlockEvent_Server(int aX, int aY, int aZ, int dim, int aID, int aValue) {
        super(false);
        x = aX;
        y = aY;
        z = aZ;
        id = aID;
        value = aValue;
        d = dim;
    }

    @Override
    public byte[] encode() {
        ByteArrayDataOutput tOut = ByteStreams.newDataOutput(10);
        tOut.writeInt(x);
        tOut.writeShort(y);
        tOut.writeInt(z);
        tOut.writeInt(d);
        tOut.writeInt(id);
        tOut.writeInt(value);
        return tOut.toByteArray();
    }

    @Override
    public GT_Packet decode(ByteArrayDataInput aData) {
        return new GT_Packet_ExtendedBlockEvent_Server(aData.readInt(), aData.readShort(), aData.readInt(), aData.readInt(), aData.readInt(), aData.readInt());
    }

    @Override
    public void process(IBlockAccess aWorld) {
        aWorld = DimensionManager.getWorld(d);
        if (aWorld != null) {
            TileEntity tTileEntity = aWorld.getTileEntity(x, y, z);
            if (tTileEntity != null) tTileEntity.receiveClientEvent(id, value);
        }
    }

    @Override
    public byte getPacketID() {
        return 5;
    }
}
