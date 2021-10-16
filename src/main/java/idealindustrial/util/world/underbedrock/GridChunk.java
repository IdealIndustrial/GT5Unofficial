package idealindustrial.util.world.underbedrock;



import idealindustrial.util.world.underbedrock.impl.BoxCollider;

import java.util.ArrayList;
import java.util.List;

public class GridChunk<T> {
    private final T[][] elements;
    private final List<BoxCollider> colliders = new ArrayList<>();
    private final int size;
    private boolean modified;

    @SuppressWarnings("unchecked")
    public GridChunk(int size) {
        this.size = size;
        elements = (T[][]) new Object[size][size];
    }

    public boolean isFree(BoxCollider collider) {
        for (BoxCollider col : colliders) {
            if (col.intersects(collider)) {
                return false;
            }
        }
        return true;
    }

    public void insert(Vein<T> vein) {
        BoxCollider box = vein.getCollider();
        assert isFree(box);
        T[][] toInsert = vein.get();
        int startX = box.getX1(), startZ = box.getZ1();
        for (int xi = 0; xi < toInsert.length; xi++) {
            System.arraycopy(toInsert[xi], 0, elements[startX + xi], startZ, toInsert[xi].length);
        }
        colliders.add(box);
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
