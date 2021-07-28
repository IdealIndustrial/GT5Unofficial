package idealindustrial.util.energy.system;

public interface IEnergyPassThrough {

    long maxAmperage();
    long maxVoltage();
    long loss();

    void onPassing(long voltage, long amperage);
    void check(long voltage, long amperage);
    void update();
}
