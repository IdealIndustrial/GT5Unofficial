package idealindustrial.util.misc;

public class II_DirUtil {

    /**
     * converts direction to down/up/side value
     */
    public static int directionToSide(int dir) {
        return II_Math.clamp(dir, 0, 2);
    }
}
