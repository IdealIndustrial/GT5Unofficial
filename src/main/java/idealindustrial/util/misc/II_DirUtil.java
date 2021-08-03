package idealindustrial.util.misc;

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

    private static final int[] opposites = new int[]{1, 0, 3,2, 5, 4};
    public static int getOppositeSide(int side) {
        return opposites[side];
    }
}
