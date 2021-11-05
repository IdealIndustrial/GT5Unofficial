package idealindustrial.impl.world.util;

import idealindustrial.api.world.util.CoordConsumer;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class BoundingBox {
    protected int minX, minY, minZ, maxX, maxY, maxZ;

    public BoundingBox(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }

    public BoundingBox (Vector3 min, Vector3 max) {
        this(min.x, min.y, min.z, max.x, max.y, max.z);
    }


    public BoundingBox add(BoundingBox box) {
        return new BoundingBox(min(minX, box.minX), min(minY, box.minY), min(minZ, box.minZ),
                max(maxX, box.maxX), max(maxY, box.maxY), max(maxZ, box.maxZ));
    }

    public void forEach(CoordConsumer coordConsumer) {
        for (int x = minX; x <= maxX; x++) {
            for (int z = minZ; z <= maxZ; z++) {
                for (int y = minY; y <= maxY; y++) {
                    coordConsumer.apply(new Vector3(x, y, z));
                }
            }
        }
    }


    public int getMinX() {
        return minX;
    }

    public int getMinY() {
        return minY;
    }

    public int getMinZ() {
        return minZ;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    public int getMaxZ() {
        return maxZ;
    }

    public Vector3 getMin() {
        return new Vector3(minX, minY, minZ);
    }

    public Vector3 getMax() {
        return new Vector3(maxX, maxY, maxZ);
    }

    public BoundingBox move(Vector3 vec) {
        return new BoundingBox(getMin().add(vec), getMax().add(vec));
    }

    public int volume() {
        return (maxX - minX) * (maxY - minY) * (maxZ - minZ);
    }

    public static class BoxBuilder {
        BoundingBox box = new BoundingBox(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);

        public BoxBuilder addX(int x) {
            box.minX = min(x, box.minX);
            box.maxX = max(x, box.maxX);
            return this;
        }

        public BoxBuilder addY(int y) {
            box.minY = min(y, box.minY);
            box.maxY = max(y, box.maxY);
            return this;
        }

        public BoxBuilder addZ(int z) {
            box.minZ = min(z, box.minZ);
            box.maxZ = max(z, box.maxZ);
            return this;
        }

        public BoxBuilder addPosition(Vector3 vector) {
            addX(vector.x);
            addY(vector.y);
            addZ(vector.z);
            return this;
        }

        public BoxBuilder setX(int x1, int x2) {
            box.minX = min(x1, x2);
            box.maxX = max(x1, x2);
            return this;
        }

        public BoxBuilder setY(int y1, int y2) {
            box.minY = min(y1, y2);
            box.maxY = max(y1, y2);
            return this;
        }

        public BoxBuilder setZ(int z1, int z2) {
            box.minZ = min(z1, z2);
            box.maxZ = max(z1, z2);
            return this;
        }

        public BoxBuilder setPosition(Vector3 v1, Vector3 v2) {
            setX(v1.x, v2.x);
            setY(v1.y, v2.y);
            setZ(v1.z, v2.z);
            return this;
        }

        public BoundingBox getBox() {
            return box;
        }
    }

    public static BoxBuilder builder() {
        return new BoxBuilder();
    }
}
