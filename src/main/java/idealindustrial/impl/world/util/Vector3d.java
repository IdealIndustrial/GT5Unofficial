package idealindustrial.impl.world.util;

import gregtech.api.interfaces.tileentity.IHasWorldObjectAndCoords;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;

public class Vector3d {
    public double x, y, z;

    public Vector3d() {
    }

    public Vector3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3d(Entity entity) {
        this(entity.posX, entity.posY, entity.posZ);
    }

    public Vector3d(TileEntity entity) {
        this(entity.xCoord, entity.yCoord, entity.zCoord);
    }

    public Vector3d(IHasWorldObjectAndCoords base) {
        this(base.getXCoord(), base.getYCoord(), base.getZCoord());
    }

    public Vector3d minus(Vector3d vec) {
        return new Vector3d(x - vec.x, y - vec.y, z - vec.z);
    }

    public Vector3d minusM(Vector3d vec) {
        x -= vec.x;
        y -= vec.y;
        z -= vec.z;
        return this;
    }

    public Vector3d add(Vector3d vec) {
        return new Vector3d(x + vec.x, y + vec.y, z + vec.z);
    }

    public Vector3 toVector() {
        return new Vector3((int)Math.round(x), (int) Math.round(y), (int) Math.round(z));
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + ", " + z + "]";
    }

    public Vector3d rotated(Axis axis, double angle, Vector3d rotationCenter) {
        Vector3d neg = minus(rotationCenter);
        Vector3d res = new Vector3d();
        switch (axis) {
            case X:
                Vector2d rotatedX = rotate(new Vector2d(neg.y, neg.z), angle);
                res.y += rotatedX.x;
                res.z += rotatedX.y;
                res.x = neg.x;
                break;
            case Y:
                Vector2d rotatedY = rotate(new Vector2d(neg.x, neg.z), angle);
                res.x += rotatedY.x;
                res.z += rotatedY.y;
                res.y = neg.y;
                break;
            case Z:
                Vector2d rotatedZ = rotate(new Vector2d(neg.x, neg.y), angle);
                res.x += rotatedZ.x;
                res.y += rotatedZ.y;
                res.z = neg.z;
                break;
        }


        return res.add(rotationCenter);

    }

    protected Vector2d rotate(Vector2d vec, double angle) {
        double x = vec.x * Math.cos(angle) - vec.y * Math.sin(angle);
        double y = vec.x * Math.sin(angle) + vec.y * Math.cos(angle);
        return new Vector2d(x, y);
    }



}
