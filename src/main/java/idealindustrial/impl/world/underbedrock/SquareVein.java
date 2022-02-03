package idealindustrial.impl.world.underbedrock;


import idealindustrial.api.world.underbedrock.Vein;
import idealindustrial.impl.world.util.Vector2;

public class SquareVein<T> implements Vein<T> {

    private final T[][] elements;
    private final Vector2 pos;

    public SquareVein(int x, int z, T[][] vein) {
        this.elements = vein;
        this.pos = new Vector2(x, z);
    }

    @Override
    public boolean isFull(int x, int y) {
        if (x < 0 || y < 0 || x >= elements.length || y >= elements.length) {
            return false;
        }
        return elements[x][y] != null;
    }

    @Override
    public int size() {
        return elements.length;
    }

    @Override
    public Vector2 position() {
        return pos;
    }

    @Override
    public T[][] get() {
        return elements;
    }
}
