package idealindustrial.util.energy.electric.system;

public interface IInfoEnergyPassThrough extends IEnergyPassThrough {

    long voltageLastTick();
    long amperageLastTick();
}
