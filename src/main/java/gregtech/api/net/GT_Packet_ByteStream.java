package gregtech.api.net;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import idealindustrial.tile.interfaces.ISyncedTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;

public class GT_Packet_ByteStream extends GT_Packet {
    private int x, z;
    private short y;
    private ByteArrayDataOutput output;
    private ByteArrayDataInput input;

    public GT_Packet_ByteStream() {
        super(true);
    }

    public GT_Packet_ByteStream(int x, int y, int z, ByteArrayDataOutput output) {
        super(false);
        this.x = x;
        this.y = (short)y;
        this.z = z;
        this.output = output;
    }

    protected GT_Packet_ByteStream(int x, int y, int z, ByteArrayDataInput input) {
        super(false);
        this.x = x;
        this.y = (short)y;
        this.z = z;
        this.input = input;
    }

    @Override
    public byte[] encode() {
        ByteArrayDataOutput tOut = ByteStreams.newDataOutput(10);
        tOut.writeInt(x);
        tOut.writeShort(y);
        tOut.writeInt(z);
        tOut.write(output.toByteArray());
        return tOut.toByteArray();
    }

    @Override
    public GT_Packet decode(ByteArrayDataInput aData) {
        return new GT_Packet_ByteStream(aData.readInt(), aData.readShort(), aData.readInt(), aData);
    }

    @Override
    public void process(IBlockAccess aWorld) {
        if (aWorld != null) {
            TileEntity tTileEntity = aWorld.getTileEntity(x, y, z);
            if (tTileEntity instanceof ISyncedTileEntity) {
                ((ISyncedTileEntity) tTileEntity).readTile(input);
            }
        }
    }

    @Override
    public byte getPacketID() {
        return 7;
    }
}
