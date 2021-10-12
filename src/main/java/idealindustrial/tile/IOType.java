package idealindustrial.tile;

public enum  IOType {
    ITEM, FLUID, ENERGY, Kinetic, ALL;

    public boolean is(IOType type) {
        return type == ALL || type == this || this == ALL;
    }
}
