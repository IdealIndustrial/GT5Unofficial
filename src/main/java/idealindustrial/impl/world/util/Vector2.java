package idealindustrial.impl.world.util;


public class Vector2 {
    public int x, y;

    public Vector2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector2 minus(Vector2 vec) {
        return new Vector2(x - vec.x, y - vec.y);
    }

    public Vector2 divide(int val) {
        return new Vector2(x / val, y / val);
    }

    public Vector2 add(int x, int y) {
        return new Vector2(this.x + x, this.y + y);
    }
}
