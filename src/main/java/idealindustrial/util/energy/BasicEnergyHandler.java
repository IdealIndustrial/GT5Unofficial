package idealindustrial.util.energy;

import idealindustrial.tile.interfaces.host.HostMachineTile;

public abstract class BasicEnergyHandler extends EnergyHandler {

    protected HostMachineTile baseTile;
    protected long minEnergyAmount, maxCapacity;
    protected long voltageIn, amperageIn, voltageOut, amperageOut;
    protected long currentAmperageOut = 0;

    public BasicEnergyHandler(HostMachineTile baseTile, long minEnergyAmount, long maxCapacity, long voltageIn, long amperageIn, long voltageOut, long amperageOut) {
        this.baseTile = baseTile;
        this.minEnergyAmount = minEnergyAmount;
        this.maxCapacity = maxCapacity;
        this.voltageIn = voltageIn;
        this.amperageIn = amperageIn;
        this.voltageOut = voltageOut;
        this.amperageOut = amperageOut;
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
    public boolean isAlmostFull() {
        return maxCapacity - stored  < maxCapacity / 10;
    }
}
