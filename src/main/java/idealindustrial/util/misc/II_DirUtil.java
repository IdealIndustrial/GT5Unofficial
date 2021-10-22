package idealindustrial.util.misc;

import idealindustrial.util.worldgen.util.Vector3d;
import net.minecraft.util.MathHelper;

import java.util.stream.IntStream;

import static java.lang.Math.abs;

public class II_DirUtil {

    /**
     * converts direction to down/up/side value
     */
    public static int directionToSide(int dir) {
        return II_Math.clamp(dir, 0, 2);
    }

    //GT5u method, may be rewrite is necessary
    public static int determineWrenchingSide(int side, float hitX, float hitY, float hitZ) {
        int tBack = getOppositeSide(side);
        switch (side) {
            case 0:
            case 1:
                if (hitX < 0.25) {
                    if (hitZ < 0.25) return tBack;
                    if (hitZ > 0.75) return tBack;
                    return 4;
                }
                if (hitX > 0.75) {
                    if (hitZ < 0.25) return tBack;
                    if (hitZ > 0.75) return tBack;
                    return 5;
                }
                if (hitZ < 0.25) return 2;
                if (hitZ > 0.75) return 3;
                return side;
            case 2:
            case 3:
                if (hitX < 0.25) {
                    if (hitY < 0.25) return tBack;
                    if (hitY > 0.75) return tBack;
                    return 4;
                }
                if (hitX > 0.75) {
                    if (hitY < 0.25) return tBack;
                    if (hitY > 0.75) return tBack;
                    return 5;
                }
                if (hitY < 0.25) return 0;
                if (hitY > 0.75) return 1;
                return side;
            case 4:
            case 5:
                if (hitZ < 0.25) {
                    if (hitY < 0.25) return tBack;
                    if (hitY > 0.75) return tBack;
                    return 2;
                }
                if (hitZ > 0.75) {
                    if (hitY < 0.25) return tBack;
                    if (hitY > 0.75) return tBack;
                    return 3;
                }
                if (hitY < 0.25) return 0;
                if (hitY > 0.75) return 1;
                return side;
        }
        return -1;
    }

    public static int getDirection(Vector3d vec) {
        double[] plain = new double[6];
        plain[0] = vec.y;
        plain[2] = vec.z;
        plain[4] = vec.x;
        for (int i = 0; i < 6; i += 2) {
            if (plain[i] > 0) {
                plain[i + 1] = plain[i];
                plain[i] = 0;
            }
        }
        return IntStream.iterate(0, i -> ++i).limit(6).reduce((i1, i2) -> abs(plain[i1]) > abs(plain[i2]) ? i1 : i2).orElse(0);
    }

    public static void main(String[] args) {
        System.out.println(getDirection(new Vector3d(-1, 0, 3)));
    }

    private static final int[] opposites = new int[]{1, 0, 3, 2, 5, 4};

    public static int getOppositeSide(int side) {
        return opposites[side];
    }

    public static int getPlacingFace(float rotationYaw, float rotationPitch, boolean useY) {
        int var7 = MathHelper.floor_double(rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3;
        int var8 = Math.round(rotationPitch);
        if ((var8 >= 65) && useY) {
            return 1;
        } else if ((var8 <= -65) && useY) {
            return 0;
        } else {
            switch (var7) {
                case 0:
                    return 2;
                case 1:
                    return 5;
                case 2:
                    return 3;
                case 3:
                    return 4;
            }
        }
        return 2;
    }
}
