package idealindustrial.impl.world.gen;

import idealindustrial.api.world.util.Shape3D;
import idealindustrial.impl.world.util.BoundingBox;
import idealindustrial.impl.world.util.Vector3;

import static java.lang.Math.pow;

public class Torus implements Shape3D {

    int height;
    int radius;
    int innerRadius;
    int maxRadius;
    double yDivider;

    public Torus(int height, int radius, int innerRadius) {
        this.height = height;
        this.radius = radius;
        this.innerRadius = innerRadius;
        this.maxRadius = radius + innerRadius;
        this.yDivider = ((double) height) / innerRadius;
    }

    @Override
    public boolean contains(Vector3 pos) {
        int xSq = pos.x * pos.x;
        int y = pos.y;
        int zSq = pos.z * pos.z;
        int radSq = radius * radius;
        return pow((xSq + zSq + pow(y / yDivider, 2) + radSq - innerRadius * innerRadius), 2) <= 4 * radSq * (xSq + zSq);
    }

    @Override
    public BoundingBox getBoundingBox() {
        return new BoundingBox(-maxRadius, -height, -maxRadius, maxRadius, height, maxRadius);
    }
}
