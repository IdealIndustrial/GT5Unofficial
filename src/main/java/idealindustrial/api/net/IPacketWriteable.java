package idealindustrial.api.net;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

public interface IPacketWriteable {

    void writeToPacket(ByteArrayDataOutput stream);

    void readFromPacket(ByteArrayDataInput stream);
}
