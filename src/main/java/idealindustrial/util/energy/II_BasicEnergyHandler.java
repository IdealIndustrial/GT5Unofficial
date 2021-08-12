package idealindustrial.util.energy;

import idealindustrial.tile.interfaces.base.II_BaseMachineTile;
import net.minecraft.nbt.NBTTagCompound;

public abstract class II_BasicEnergyHandler extends II_EnergyHandler{

    protected II_BaseMachineTile baseTile;
    protected long minEnergyAmount, maxCapacity;
    protected long voltageIn, amperageIn, voltageOut, amperageOut;
    protected long currentAmperageOut = 0;

    public II_BasicEnergyHandler(II_BaseMachineTile baseTile, long minEnergyAmount, long maxCapacity, long voltageIn, long amperageIn, long voltageOut, long amperageOut) {
        this.baseTile = baseTile;
        this.minEnergyAmount = minEnergyAmount;
        this.maxCapacity = maxCapacity;
        this.voltageIn = voltageIn;
        this.amperageIn = amperageIn;
        this.voltageOut = voltageOut;
        this.amperageOut = amperageOut;
    }
}