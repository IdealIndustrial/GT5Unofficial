package idealindustrial.impl.autogen.material.submaterial;

public enum MatterState {
    Solid,
    Liquid,
    Gas,
    Plasma;

    public boolean isSolid() {
        return this == Solid;
    }

    public boolean isLiquid() {
        return this == Liquid;
    }

    public boolean isGaseous() {
        return this == Gas;
    }

    public boolean isPlasma() {
        return this == Plasma;
    }

}
