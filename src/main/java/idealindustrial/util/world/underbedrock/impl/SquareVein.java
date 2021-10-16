package idealindustrial.util.world.underbedrock.impl;


import idealindustrial.util.world.underbedrock.Vein;

public class SquareVein<T> implements Vein<T> {

    private final BoxCollider collider;
    private final T[][] elements;

    public SquareVein(int x, int z, T[][] vein) {
        this.collider = new BoxCollider(x, z, x + vein.length - 1, z + vein[0].length - 1);
        this.elements = vein;
    }

    @Override
    public BoxCollider getCollider() {
        return collider;
    }

    @Override
    public T[][] get() {
        return elements;
    }
}
