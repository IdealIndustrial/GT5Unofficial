package idealindustrial.util.misc;

import com.google.common.collect.HashMultimap;
import idealindustrial.items.GT_FluidDisplayItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import org.omg.CORBA.Object;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Supplier;

public class II_Util {

    public static <T> Defaultable<T> makeDefault(T defaultValue) {
        return new DefaultableImpl<>(defaultValue);
    }

    public static <T> Supplier<T> singletonSupplier(T value) {
        return () -> value;
    }

    @SuppressWarnings("unchecked")
    public static <K, V> void rehash(Map<K, V> map) {
        List<K> keys = new ArrayList<>(map.size());
        List<V> values  = new ArrayList<>(map.size());//not using entries to save some performance
        int i = 0;
        for (Map.Entry<K, V> entry : map.entrySet()) {
            keys.add(entry.getKey());
            values.add(entry.getValue());
            ++i;
        }
        map.clear();
        for (int j = 0; j < i; j++) {
            map.put(keys.get(j), values.get(j));
        }
    }

    public static <K> void rehash(Set<K> set) {
        List<K> keys = new ArrayList<>(set.size());
        int i = 0;
        for (K k : set) {
            keys.add(k);
            ++i;
        }
        set.clear();
        for (int j = 0; j < i; j++) {
            set.add(keys.get(j));
        }
    }

    public static <K, V> void rehash(HashMultimap<K, V> map) {
        List<K> keys = new ArrayList<>(map.size());
        List<V> values = new ArrayList<>(map.size());//not using entries to save some performance
        int i = 0;
        for (Map.Entry<K, V> entry : map.entries()) {
            keys.add(entry.getKey());
            values.add(entry.getValue());
            ++i;
        }
        map.clear();
        for (int j = 0; j < i; j++) {
            map.put(keys.get(j), values.get(j));
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] nonNull(T[] ar) {
        return ar == null ? (T[]) new Object[0] : ar;
    }

    public static boolean[] trueAr(int size) {
        boolean[] ar = new boolean[size];
        Arrays.fill(ar, true);
        return ar;
    }

    public static long intsToLong(int a, int b) {
        return ((long) a) << 32L | ((long) b & 0xFFFFFFFFL);
    }

    public static int intAFromLong(long l) {
        return (int) (l >> 32);
    }

    public static int intBFromLong(long l)  {
        return (int) l;
    }

    public static int getTier(long usage) {
        if (usage <= 8) {
            return 0;
        }
        return (int) Math.floor(Math.log10(usage * 2 - 1)/Math.log10(4)) - 1;
    }

    public static long getVoltage(int tier) {
        return 1L << (tier * 2 + 3);
    }

    public static void main(String[] args) {
        System.out.println("   ");
    }

    public static long randomBetween(long minQuantity, long maxQuantity, Random random) {
        long spread = Math.max(0, maxQuantity - minQuantity);
        if (spread == 0) {
            return minQuantity;
        }
        return minQuantity + (Math.abs(random.nextLong()) % spread);
    }

    public static int randomBetween(int minQuantity, int maxQuantity, Random random) {
        int spread = Math.max(0, maxQuantity - minQuantity);
        if (spread == 0) {
            return minQuantity;
        }
        return minQuantity + random.nextInt(spread);
    }

    public static double randomBetween(double minQuantity, double maxQuantity, Random random) {
        double spread = Math.max(0, maxQuantity - minQuantity);
        if (Math.abs(spread) < 0.000000001) {
            return minQuantity;
        }
        return minQuantity +  (Math.abs(random.nextDouble()) % spread);
    }

    public static void sendChatToPlayer(EntityPlayer player, String s) {
        player.addChatComponentMessage(new ChatComponentText(s));
    }

    public static ItemStack copyAmount(int amount, ItemStack stack) {
        if (stack == null) {
            return null;
        }
        ItemStack out = stack.copy();
        out.stackSize = amount;
        return out;
    }

    public static int getColorAsInt(Color color) {
        return (color.getRed() << 16) | (color.getGreen() << 8) | color.getBlue();
    }

}
