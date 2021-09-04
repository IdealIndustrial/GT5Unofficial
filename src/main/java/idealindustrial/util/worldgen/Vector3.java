package idealindustrial.util.worldgen;

import idealindustrial.util.misc.II_Math;

public class Vector3 {
    public int x, y, z;

    public Vector3(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3() {

    }

    public Vector3 add(Vector3 vec) {
        return new Vector3(x + vec.x, y + vec.y, z + vec.z);
    }

    public Vector3d toDVector() {
        return new Vector3d(x, y, z);
    }

    public Vector3 rotateY(int angle, Vector3d center) {
        return toDVector().rotated(Axis.Y, angle * II_Math.NINETY_DEGREES_RAD, center).toVector();
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + ", " + z + "]";
    }
}
