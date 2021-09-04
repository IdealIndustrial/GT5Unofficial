package idealindustrial.util.misc;

public class II_Math {
    public static int clamp(int val, int min, int max) {
        return Math.max(min, Math.min(max, val));
    }

    public static final double NINETY_DEGREES_RAD = Math.toRadians(90);
}
