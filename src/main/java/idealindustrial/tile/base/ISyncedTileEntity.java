package idealindustrial.tile.base;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

public interface ISyncedTileEntity {

    void writeTile(ByteArrayDataOutput stream);

    void readTile(ByteArrayDataInput stream);

    boolean receiveClientEvent(int id, int value);
}
