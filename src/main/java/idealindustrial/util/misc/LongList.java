package idealindustrial.util.misc;

import java.util.Arrays;

public class LongList {
    private final static double maxLoadFactor = 0.75;
    private final static double incTimes = 1.5;
    private long[] contents;
    private int size = 0;

    public LongList(int size) {
        this.contents = new long[size * 2 + 1];
    }

    public LongList() {
        this(10);
    }

    public long get(int i) {
        assert i < size;
        return contents[i];
    }

    public void set(int i, long l) {
        assert i < size;
        contents[i] = l;
    }

    public void add(long l) {
        checkSize(size + 1);
        contents[size++] = l;
    }

    protected void checkSize(int newSize) {
        if (((double) newSize) / contents.length > maxLoadFactor) {
            contents = Arrays.copyOf(contents, (int) (contents.length * incTimes));
        }
    }
}
