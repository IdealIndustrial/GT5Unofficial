package idealindustrial.impl.autogen.material.submaterial;

public class FluidInfo { // liquid means Forge fluid =)
    FluidDef[] contents = new FluidDef[3];

    public boolean hasLiquid() {
        return get(MatterState.Liquid) != null;
    }

    public boolean hasGas() {
        return get(MatterState.Gas) != null;
    }

    public boolean hasPlasma() {
        return get(MatterState.Plasma) != null;
    }

    public FluidDef get(MatterState state) {
        return contents[state.ordinal() - 1];
    }

    public boolean has(MatterState state) {
        return get(state) != null;
    }

    public void set(MatterState state, FluidDef fluid) {
        assert state != MatterState.Solid;
        contents[state.ordinal() - 1] = fluid;
    }
}
