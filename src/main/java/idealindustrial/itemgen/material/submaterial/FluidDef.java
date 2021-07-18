package idealindustrial.itemgen.material.submaterial;


import gregtech.api.interfaces.IIconContainer;
import net.minecraftforge.fluids.Fluid;

public class FluidDef {
    final MatterState state;
    final int temperature;
    Fluid fluid;

    //should we add viscosity and et.c. ?


    public FluidDef(MatterState state, int temperature) {
        this.state = state;
        this.temperature = temperature;
    }

    public MatterState getState() {
        return state;
    }

    public Fluid getFluid() {
        return fluid;
    }

    public void setFluid(Fluid fluid) {
        this.fluid = fluid;
    }

    public int getTemperature() {
        return temperature;
    }
}
