package idealindustrial.util.parameter;

import idealindustrial.api.tile.fluid.FluidHandler;
import idealindustrial.api.tile.inventory.InternalInventory;
import idealindustrial.api.tile.inventory.RecipedInventory;
import idealindustrial.util.misc.II_TileUtil;
import idealindustrial.util.misc.II_Util;

public class RecipedMachineStats {

    public final int tier, inSlots, outSlots, specialSlots, stackSize, fluidsIn, fluidsOut, fluidCapacity;
    public final long voltageIn, amperageIn, energyCapacity;

    public RecipedMachineStats(int tier, int inSlots, int outSlots, int specialSlots, int stackSize, int fluidsIn, int fluidsOut, int fluidCapacity, long amperageIn, long energyCapacity) {
        this.tier = tier;
        this.inSlots = inSlots;
        this.outSlots = outSlots;
        this.specialSlots = specialSlots;
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

    public FluidHandler tankIn() {
        return II_TileUtil.constructFluidHandler(fluidsIn, fluidCapacity);
    }

    public FluidHandler tankOut() {
        return II_TileUtil.constructFluidHandler(fluidsOut, fluidCapacity);
    }

    public RecipedInventory inventoryIn() {
        return II_TileUtil.constructRecipedInventory(inSlots, stackSize);
    }

    public InternalInventory inventoryOut() {
        return II_TileUtil.constructInternalInventory(outSlots, stackSize);
    }

    public InternalInventory inventorySpecial() {
        return II_TileUtil.constructInternalInventory(specialSlots, stackSize);
    }


    //todo: deprecate
    public int totalSlotsToRender() {
        return inSlots + outSlots + fluidsIn + fluidsOut;
    }

    public int inventorySize() {
        return inSlots + outSlots;
    }

    public int fluidInventorySize() {
        return fluidsIn + fluidsOut;
    }



}
