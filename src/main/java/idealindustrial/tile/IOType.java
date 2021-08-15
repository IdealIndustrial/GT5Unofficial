package idealindustrial.tile;

public enum  IOType {
    ITEM, FLUID, ENERGY, ALL;

    public boolean is(IOType type) {
        return type == ALL || type == this;
    }
}
