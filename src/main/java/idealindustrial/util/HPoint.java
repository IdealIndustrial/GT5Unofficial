package idealindustrial.util;

import gnu.trove.set.TLongSet;
import gnu.trove.set.hash.TLongHashSet;

import java.util.HashSet;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

//hackery thing
//stores x, y, z in long
//27 bit signed x, 10 bit unsigned y, 27 bit signed z
public class HPoint {
    public int x, y, z;

    public long toLong() {
        long compressedX = Math.abs(x) & 0x3FFFFFF | (x >= 0 ? 0 : (1L << 26));
        long compressedY = y & 0x3FF;
        long compressedZ = Math.abs(z) & 0x3FFFFFF | (z >= 0 ? 0 : (1L << 26));
//        System.out.println("Bin " + Long.toBinaryString(compressedX)
//                + " " + Long.toBinaryString(compressedY) + " " + Long.toBinaryString(compressedZ));
        return compressedX | compressedY << 27L | compressedZ << 37L;
    }

    public static long toLong(int x, int y, int z) {
        long compressedX = Math.abs(x) & 0x3FFFFFF | (x >= 0 ? 0 : (1L << 26));
        long compressedY = y & 0x3FF;
        long compressedZ = Math.abs(z) & 0x3FFFFFF | (z >= 0 ? 0 : (1L << 26));
        return compressedX | compressedY << 27L | compressedZ << 37L;
    }

    public void fromLong(long l) {
        x = (int) (0x3FFFFFFL & l);
        if ((l & (1L << 26)) != 0L) {
            x = -x;
        }
        y = (int) (((0x3FFL << 27) & l) >> 27);
        z = (int) (((0x3FFFFFFL << 37) & l) >> 37);
        if ((l & (1L << 63)) != 0L) {
            z = -z;
        }
    }

    @Override
    public String toString() {
        return "" + x + " " + y + " " + z;
    }

    public static void main(String[] args) {
        final int smallTestsCount = 100, randomTestsCount = 10_000_000;
        System.out.println("Testing small:");
        Random random = new Random();
        HPoint p = new HPoint();
        for (int i = 0; i < smallTestsCount; i++) {
            int x = random.nextInt(10) - 5;
            int y = random.nextInt(10);
            int z = random.nextInt(10) - 5;
            p.x = x;
            p.y = y;
            p.z = z;
            long toL = p.toLong();
            p.fromLong(toL);
            assert x == p.x && y == p.y && z == p.z : "In: " + x + " " + y + " " + z + "\nOut: " + p;
        }
        System.out.println("Ok");
        System.out.println("Testing random:");
        final int maxX = 1 << 25, maxY = (1 << 10);
        for (int i = 0; i < randomTestsCount; i++) {
            int x = random.nextInt(maxX * 2) - maxX;
            int y = random.nextInt(maxY);
            int z = random.nextInt(maxX * 2) - maxX;
            p.x = x;
            p.y = y;
            p.z = z;
            long toL = p.toLong();
            p.fromLong(toL);
            assert x == p.x && y == p.y && z == p.z : "In: " + x + " " + y + " " + z + "\nOut: " + p + "\nBin: " + Long.toBinaryString(toL);
        }
        System.out.println("Ok");
        System.out.println("Tests passed");

        System.out.println("Heating");
        computeSpeed(false);
        computeSimplePointSpeed(false);
        System.out.println("Performance HPoint: ");
        computeSpeed(true);
        System.out.println("Performance Point: ");
        computeSimplePointSpeed(true);

    }

    private static void computeSpeed(boolean log) {
        int size = 10_000_000;
        if (log) {
            System.out.println("Create Obj");
        }
        Timer timer = new Timer();
        timer.start();
        long[] ar = new long[size];
        HPoint p = new HPoint();
        for (int i = 0; i < size; i++) {
            p.x = 1;
            p.y = 2;
            p.z = 3;
            ar[i] = p.toLong();
        }
        if (log) {
            System.out.println("Elapsed: " + timer.next());
            System.out.println("Hash objs");
        }
        TLongSet set = new TLongHashSet();
        for (int i = 0; i < size; i++) {
            set.add(ar[i]);
        }
        if (log) {
            System.out.println("Elapsed: " + timer.next());
        }
    }

    private static class Point {
        int x, y, z;

        public Point(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x &&
                    y == point.y &&
                    z == point.z;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z);
        }
    }

    private static void computeSimplePointSpeed(boolean log) {
        int size = 10_000_000;
        if (log) {
            System.out.println("Create Obj");
        }
        Timer timer = new Timer();
        timer.start();
        Point[] ar = new Point[size];
        for (int i = 0; i < size; i++) {
            ar[i] = new Point(1, 2, 3);
        }
        if (log) {
            System.out.println("Elapsed: " + timer.next());
            System.out.println("Hash objs");
        }
        Set<Point> set = new HashSet<>();
        for (int i = 0; i < size; i++) {
            set.add(ar[i]);
        }
        if (log) {
            System.out.println("Elapsed: " + timer.next());
        }
    }

    private static class Timer {
        long time;

        public void start() {
            time = System.currentTimeMillis();
        }

        public long next() {
            long cur = System.currentTimeMillis();
            long out = cur - time;
            time = cur;
            return out;
        }
    }


}
