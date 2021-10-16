package idealindustrial.util.world.underbedrock.impl;

public class BoxCollider {
    private final int x1, x2, z1, z2;

    public BoxCollider(int x1, int z1, int x2, int z2) {
        this.x1 = x1;
        this.x2 = x2;
        this.z1 = z1;
        this.z2 = z2;
    }

    public boolean intersects(BoxCollider box) {
        return !(z1 < box.z2 || z2 > box.z1 || x2 < box.x1 || x1 > box.x2);
    }

    public int getX1() {
        return x1;
    }

    public int getX2() {
        return x2;
    }

    public int getZ1() {
        return z1;
    }

    public int getZ2() {
        return z2;
    }
}
