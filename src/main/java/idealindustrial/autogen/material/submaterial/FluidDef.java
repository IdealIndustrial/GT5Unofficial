package idealindustrial.autogen.material.submaterial;


import net.minecraft.block.Block;
import net.minecraftforge.fluids.Fluid;

public class FluidDef {
    final MatterState state;
    final int temperature;
    Fluid fluid;
    Block block;

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

    public Block getBlock() {
        return block;
    }

    public void setFluid(Fluid fluid) {
        this.fluid = fluid;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public int getTemperature() {
        return temperature;
    }
}
