package idealindustrial.util.misc;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.Arrays;
import java.util.function.IntFunction;

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

    public static void writeLongArray(long[] array, ByteArrayDataOutput stream) {
        stream.writeInt(array.length);
        for (long i : array) {
            stream.writeLong(i);
        }
    }

    public static long[] readLongArray(ByteArrayDataInput stream) {
        long[] array = new long[stream.readInt()];
        for (int i = 0; i < array.length; i++) {
            array[i] = stream.readLong();
        }
        return array;
    }

    public static void writeNBTLongArray(NBTTagCompound nbt, long[] ar, String name) {
        for (int i = 0; i < ar.length; i++) {
            nbt.setLong(name + i, ar[i]);
        }
    }

    public static long[] readNBTLongArray(NBTTagCompound nbt, int length, String name) {
        long[] ar = new long[length];
        for (int i = 0; i < length; i++) {
            ar[i] = nbt.getLong(name + i);
        }
        return ar;
    }

    public static <T> T[] concatArrays(T[] a, T[] b) {
        T[] result = Arrays.copyOf(a, a.length + b.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        return result;
    }

    public static <T> T[] arrayOf(T t, T[] ar) {
        Arrays.fill(ar, t);
        return ar;
    }

}
