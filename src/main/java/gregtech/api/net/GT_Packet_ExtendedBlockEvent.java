package gregtech.api.net;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;

public class GT_Packet_ExtendedBlockEvent extends GT_Packet {
    private int mX, mZ;
    private short mY;
    private int mID;
    private int mValue;

    public GT_Packet_ExtendedBlockEvent() {
        super(true);
    }

    public GT_Packet_ExtendedBlockEvent(int aX, short aY, int aZ, int aID, int aValue) {
        super(false);
        mX = aX;
        mY = aY;
        mZ = aZ;
        mID = aID;
        mValue = aValue;
    }

    @Override
    public byte[] encode() {
        ByteArrayDataOutput tOut = ByteStreams.newDataOutput(10);
        tOut.writeInt(mX);
        tOut.writeShort(mY);
        tOut.writeInt(mZ);
        tOut.writeInt(mID);
        tOut.writeInt(mValue);
        return tOut.toByteArray();
    }

    @Override
    public GT_Packet decode(ByteArrayDataInput aData) {
        return new GT_Packet_ExtendedBlockEvent(aData.readInt(), aData.readShort(), aData.readInt(), aData.readInt(), aData.readInt());
    }

    @Override
    public void process(IBlockAccess aWorld) {
        if (aWorld != null) {
            TileEntity tTileEntity = aWorld.getTileEntity(mX, mY, mZ);
            if (tTileEntity != null) tTileEntity.receiveClientEvent(mID, mValue);
        }
    }

    @Override
    public byte getPacketID() {
        return 6;
    }
}
