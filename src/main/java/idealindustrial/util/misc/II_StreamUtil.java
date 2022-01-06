package idealindustrial.util.misc;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import gnu.trove.TIntCollection;
import gnu.trove.iterator.TIntIterator;
import gnu.trove.set.TIntSet;
import idealindustrial.api.textures.ITexture;
import idealindustrial.api.textures.INetworkedTexture;
import idealindustrial.impl.textures.TextureManager;
import net.minecraft.nbt.NBTTagCompound;

import java.util.*;
import java.util.function.IntConsumer;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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

    @SafeVarargs
    public static <T> T getAny(T... ar) {
        for (T t : ar) {
            if (t != null) {
                return t;
            }
        }
        return null;
    }

    public static <T> int indexOf(T[] ar, T elem) {
        for (int i = 0; i < ar.length; i++) {
            if (ar[i] != null && ar[i].equals(elem)) {
                return i;
            }
        }
        return -1;
    }

    @SafeVarargs
    public static <T> Set<T> set(T... ar) {
        return new HashSet<>(Arrays.asList(ar));
    }

    public static IntStream stream(TIntCollection collection) {
        TIntIterator iterator = collection.iterator();
        return StreamSupport.intStream(Spliterators.spliteratorUnknownSize(new PrimitiveIterator.OfInt(){

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public int nextInt() {
                return iterator.next();
            }
        }, Spliterator.ORDERED), false);
    }

    @SafeVarargs
    public static <T> ArrayList<T> list(T...ts) {
        return new ArrayList<>(Arrays.asList(ts));
    }

}
