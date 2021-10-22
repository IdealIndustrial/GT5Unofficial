package idealindustrial.util.worldgen.impl;

import idealindustrial.util.worldgen.util.BoundingBox;
import idealindustrial.util.worldgen.util.Shape3D;
import idealindustrial.util.worldgen.util.Vector3;

public class ClampedEllipsoid implements Shape3D {

    int height;
    int radiusSq;
    int radius;

    public ClampedEllipsoid(int height, int radius) {
        this.height = height;
        this.radius = radius;
        this.radiusSq = radius * radius;
    }

    @Override
    public boolean contains(Vector3 pos) {
        int a = radiusSq / height;
        return pos.x * pos.x + pos.z * pos.z + pos.y * pos.y * a <= height * a;
    }

    @Override
    public BoundingBox getBoundingBox() {
        return new BoundingBox(-radius, -height, -radius, radius, height, radius);
    }
}