package idealindustrial.util.misc;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.sun.prism.Texture;
import gregtech.api.interfaces.ITexture;
import idealindustrial.textures.INetworkedTexture;
import idealindustrial.textures.TextureManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.Arrays;
import java.util.function.IntFunction;
import java.util.stream.Stream;

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

    public static void writeTextureArray(ITexture[] array, ByteArrayDataOutput stream) {
        stream.writeInt(array.length);
        for (ITexture texture : array) {
            if (texture instanceof INetworkedTexture) {
                stream.writeInt(((INetworkedTexture) texture).getID());
            }
            else {
                stream.writeInt(-1);
            }
        }
    }

    public static ITexture[] readTextureArray(ByteArrayDataInput stream, ITexture[] basicArray) {
        ITexture[] array = new ITexture[stream.readInt()];
        for (int i = 0; i < array.length; i++) {
            int id = stream.readInt();
            if (id == -1) {
                array[i] = basicArray[i];
            }
            else {
                array[i] = TextureManager.INSTANCE.getNetworkedTexture(id);
            }
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

    public static <T> T[] setInNullAr(T t, T[] ar, int... positions) {
        for (int i : positions) {
            ar[i] = t;
        }
        return ar;
    }

    public static <T> Stream<T> repeated(T t, int times) {
        return Stream.generate(() -> t).limit(times);
    }

}
