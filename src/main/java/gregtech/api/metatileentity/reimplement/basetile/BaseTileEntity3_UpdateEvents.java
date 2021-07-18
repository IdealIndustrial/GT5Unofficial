package gregtech.api.metatileentity.reimplement.basetile;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import gregtech.api.enums.GT_Values;
import gregtech.api.interfaces.tileentity.IMachineBlockUpdateable;
import gregtech.api.net.GT_Packet_ByteStream;

public abstract class BaseTileEntity3_UpdateEvents extends BaseTileEntity2_Updatable implements ISynchronizedTileEntity, IMachineBlockUpdateable {

    boolean updateClient = false;

    @Override
    public void onPostTick(long timer, boolean serverSide) {
        super.onPostTick(timer, serverSide);
        if (serverSide) {
            if (updateClient && timer % 10 == 0) {
                sendClientData();
            }
        }
    }

    @Override
    public void issueClintUpdate() {
        updateClient = true;
    }

    public void sendClientData() {
        @SuppressWarnings("UnstableApiUsage")
        ByteArrayDataOutput output = ByteStreams.newDataOutput(100);
        writeClientData(output);
        GT_Values.NW.sendPacketToAllPlayersInRange(worldObj,
                new GT_Packet_ByteStream(xCoord, yCoord, zCoord, output),
                xCoord, zCoord);
    }

    @Override
    public void receiveStream(ByteArrayDataInput stream) {
        readClientData(stream);
    }

    public void writeClientData(ByteArrayDataOutput data) {

    }

    public void readClientData(ByteArrayDataInput data) {

    }



    @Override
    public void onMachineBlockUpdate() {
        if (alive()) metaTileEntity.onMachineBlockUpdate();
    }
}
