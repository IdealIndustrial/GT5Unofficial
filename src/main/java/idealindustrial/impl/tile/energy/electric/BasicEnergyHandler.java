package idealindustrial.impl.tile.energy.electric;

import idealindustrial.impl.recipe.MachineEnergyParams;
import idealindustrial.api.tile.host.HostMachineTile;
import idealindustrial.util.misc.II_Util;

public abstract class BasicEnergyHandler extends EnergyHandler {

    protected HostMachineTile baseTile;
    protected long minEnergyAmount, maxCapacity;
    protected long voltageIn, amperageIn, voltageOut, amperageOut;
    protected long currentAmperageOut = 0;
    protected MachineEnergyParams params;

    public BasicEnergyHandler(HostMachineTile baseTile, long minEnergyAmount, long maxCapacity, long voltageIn, long amperageIn, long voltageOut, long amperageOut) {
        this.baseTile = baseTile;
        this.minEnergyAmount = minEnergyAmount;
        this.maxCapacity = maxCapacity;
        this.voltageIn = voltageIn;
        this.amperageIn = amperageIn;
        this.voltageOut = voltageOut;
        this.amperageOut = amperageOut;
        this.params = new MachineEnergyParams(II_Util.getTier(voltageIn), voltageIn, amperageIn);
    }

    @Override
    public long drain(long energy, boolean doDrain) {
        if (stored < minEnergyAmount) {
            return 0;
        }
        long drained = Math.min(energy, stored);
        if (doDrain) {
            stored -= drained;
        }
        return drained;
    }

    @Override
    public long fill(long energy, boolean doFill) {
        long toFill = Math.min(energy, maxCapacity - stored);
        if (doFill) {
            stored += toFill;
        }
        return toFill;
    }

    @Override
    public boolean isAlmostFull() {
        return maxCapacity - stored  < maxCapacity / 10;
    }

    @Override
    public MachineEnergyParams getParams() {
        return params;
    }
}
