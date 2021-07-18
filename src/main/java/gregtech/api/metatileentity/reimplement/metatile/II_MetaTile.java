package gregtech.api.metatileentity.reimplement.metatile;

import idealindustrial.util.fluid.II_FluidHandler;
import idealindustrial.util.inventory.II_BaseInventory;
import idealindustrial.util.inventory.II_InternalInventory;

public interface II_MetaTile {


    boolean hasInventory();
    II_InternalInventory getInputsHandler();
    II_InternalInventory getOutputsHandler();
    II_BaseInventory getSpecialItemHandler();


    boolean hasFluidTank();
    II_FluidHandler getInputTank();
    II_FluidHandler getOutputTank();
}
