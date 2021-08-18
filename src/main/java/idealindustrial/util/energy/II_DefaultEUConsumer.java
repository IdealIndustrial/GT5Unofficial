package idealindustrial.util.energy;

public class II_DefaultEUConsumer implements EUConsumer {

    protected II_BasicEnergyHandler handler;
    protected int side;
    protected boolean notifyOnFull;

    public II_DefaultEUConsumer(II_BasicEnergyHandler handler, int side) {
        this.handler = handler;
        this.side = side;
    }

    @Override
    public boolean needsEnergy() {
        return handler.maxCapacity - handler.stored >= handler.voltageIn;
    }

    @Override
    public long requestAmperes() {
        return Math.min((handler.maxCapacity - handler.stored) / handler.voltageIn, handler.amperageIn);
    }

    @Override
    public void acceptEnergy(long voltage, long amperage) {
        if (voltage > handler.voltageIn) {
            handler.baseTile.overVoltage();
        }
        handler.stored += voltage * amperage;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        II_DefaultEUConsumer that = (II_DefaultEUConsumer) o;
        return side == that.side && handler.baseTile.equals(that.handler.baseTile);
    }

    @Override
    public int hashCode() {
        return handler.baseTile.hashCode() * 31 + side*7;
    }
}
