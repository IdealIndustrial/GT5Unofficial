package idealindustrial.util.misc;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

public class II_StreamUtil {

    public static void writeIntArray(int[] array, ByteArrayDataOutput stream) {
        stream.writeInt(array.length);
        for (int i : array) {
            stream.writeInt(i);
        }
    }

    public static int[] readIntArray(ByteArrayDataInput stream) {
        int[] array = new int[stream.readInt()];
        for (int i = 0; i < array.length; i++) {
            array[i] = stream.readInt();
        }
        return array;
    }
}
