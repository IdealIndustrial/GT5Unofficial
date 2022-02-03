package idealindustrial.impl.world.underbedrock;


import idealindustrial.api.world.underbedrock.Vein;
import idealindustrial.impl.world.util.Vector2;

public class GridChunk<T> {
    private final T[][] elements;
    private final int size;
    private boolean modified;

    @SuppressWarnings("unchecked")
    public GridChunk(int size) {
        this.size = size;
        elements = (T[][]) new Object[size][size];
    }


    public void insert(Vein<T> vein) {
        Vector2 pos = vein.position();
        for (int x = 0; x < vein.size(); x++) {
            for (int z = 0; z < vein.size(); z++) {
                int tx = x + pos.x, tz = z + pos.y;
                if (tx < 0 || tx >= elements.length || tz < 0 || tz >= elements.length) {
                    return;
                }
                if (elements[tx][tz] == null) {
                    continue;
                }
                if (vein.isFull(x, z)) {
                    return;
                }
            }
        }

        T[][] toInsert = vein.get();
        for (int x = 0; x < vein.size(); x++) {
            for (int z = 0; z < vein.size(); z++) {
                if (toInsert[x][z] == null) {
                    continue;
                }
                int tx = x + pos.x, tz = z + pos.y;
                elements[tx][tz] = toInsert[x][z];
            }
        }
    }

    public T get(int x, int z) {
        return elements[x][z];
    }

    public void set(int x, int z, T t) {
        elements[x][z] = t;
    }

    public int size() {
        return size;
    }

    public boolean wasModified() {
        return modified;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }
}
