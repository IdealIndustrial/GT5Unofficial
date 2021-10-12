package idealindustrial.util.energy.electric;

import idealindustrial.util.energy.electric.system.CableSystem;

public class II_DefaultEUProducer implements EUProducer {
    protected BasicEnergyHandler handler;
    protected CableSystem system = null;

    public II_DefaultEUProducer(BasicEnergyHandler handler) {
        this.handler = handler;
    }

    @Override
    public boolean hasEnergy() {
        return availableAmperes() > 0;
    }

    @Override
    public long voltage() {
        return handler.voltageOut;
    }

    @Override
    public long availableAmperes() {
        return Math.min((handler.stored - handler.minEnergyAmount) / handler.voltageOut, handler.amperageOut - handler.currentAmperageOut);
    }

    @Override
    public void consume(long amperes) {
        handler.stored -= amperes * handler.voltageOut;
        handler.currentAmperageOut += amperes;
        assert handler.currentAmperageOut <= handler.amperageOut;
    }

    @Override
    public void reset() {
        handler.currentAmperageOut = 0;
    }

    @Override
    public void setSystem(CableSystem system) {
        if (this.system != null && this.system.isValid()) {
            this.system.invalidate();
        }
        this.system = system;
    }

    @Override
    public void onConnectionAppended() { //todo remove
        if (system != null) {
            system.invalidate();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        II_DefaultEUProducer that = (II_DefaultEUProducer) o;
        return handler.baseTile == that.handler.baseTile;
    }

    @Override
    public int hashCode() {
        return handler.baseTile.hashCode();
    }
}
