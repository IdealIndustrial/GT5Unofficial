package idealindustrial.api.tile.energy.electric;

public interface IInfoEnergyPassThrough extends IEnergyPassThrough {

    long voltageLastTick();
    long amperageLastTick();
}
