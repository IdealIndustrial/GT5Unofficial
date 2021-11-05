package idealindustrial.impl.world.util;

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

    public Vector3 addm(Vector3 vec) {
        x += vec.x;
        y += vec.y;
        z += vec.z;
        return this;
    }

    public Vector3d toDVector() {
        return new Vector3d(x, y, z);
    }

    public Vector3 rotateY(int angle, Vector3d center) {
        return toDVector().rotated(Axis.Y, angle * II_Math.NINETY_DEGREES_RAD, center).add(center).toVector();
    }

    public Vector3 rotateYm(int angle, Vector3d center) {
        return set(rotateY(angle, center));
    }

    public Vector3 set(Vector3 vector3) {
        x = vector3.x;
        ;
        y = vector3.y;
        ;
        z = vector3.z;
        return this;
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + ", " + z + "]";
    }

    public Vector3 invert() {
        return new Vector3(-x, -y, -z);
    }

    public Vector3 invertm() {
        x = -x;
        y = -y;
        z = -z;
        return this;
    }

    public Vector3 minus(Vector3 vec) {
        return new Vector3(x - vec.x, y - vec.y, z - vec.z);
    }
}
