package idealindustrial.util.energy.system;

public interface IInfoEnergyPassThrough extends IEnergyPassThrough {

    long voltageLastTick();
    long amperageLastTick();
}
