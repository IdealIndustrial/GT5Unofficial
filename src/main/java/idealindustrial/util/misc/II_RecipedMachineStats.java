package idealindustrial.util.misc;

import idealindustrial.util.fluid.II_FluidHandler;
import idealindustrial.util.inventory.II_InternalInventory;
import idealindustrial.util.inventory.II_RecipedInventory;

public class II_RecipedMachineStats {

    public final int tier, inSlots, outSlots, stackSize, fluidsIn, fluidsOut, fluidCapacity;
    public final long voltageIn, amperageIn, energyCapacity;

    public II_RecipedMachineStats(int tier, int inSlots, int outSlots, int stackSize, int fluidsIn, int fluidsOut, int fluidCapacity, long amperageIn, long energyCapacity) {
        this.tier = tier;
        this.inSlots = inSlots;
        this.outSlots = outSlots;
        this.stackSize = stackSize;
        this.fluidsIn = fluidsIn;
        this.fluidsOut = fluidsOut;
        this.fluidCapacity = fluidCapacity;
        this.voltageIn = II_Util.getVoltage(tier);
        this.amperageIn = amperageIn;
        this.energyCapacity = energyCapacity;
    }

    public boolean hasTank() {
        return fluidsIn > 0 || fluidsOut > 0;
    }

    public II_FluidHandler tankIn() {
        return II_TileUtil.constructFluidHandler(fluidsIn, fluidCapacity);
    }

    public II_FluidHandler tankOut() {
        return II_TileUtil.constructFluidHandler(fluidsOut, fluidCapacity);
    }

    public II_RecipedInventory inventoryIn() {
        return II_TileUtil.constructRecipedInventory(inSlots, stackSize);
    }

    public II_InternalInventory inventoryOut() {
        return II_TileUtil.constructInternalInventory(outSlots, stackSize);
    }


}
