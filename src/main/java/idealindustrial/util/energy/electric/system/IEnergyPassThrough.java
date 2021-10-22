package idealindustrial.util.energy.electric.system;

public interface IEnergyPassThrough {

    long maxAmperage();
    long maxVoltage();
    long loss();

    void onPassing(long voltage, long amperage);

    default void setSystem(CableSystem system) {

    }

    default void invalidate() {
        //do nothing in general
    }

    default void update() {
        //do nothing in general
    }

}