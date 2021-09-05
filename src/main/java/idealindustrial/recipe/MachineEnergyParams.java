package idealindustrial.recipe;

import idealindustrial.util.parameter.RecipedMachineStats;

public class MachineEnergyParams {

    public final int tier;
    public final long voltage;
    public final long amperage;

    public MachineEnergyParams(int tier, long voltage, long amperage) {
        this.tier = tier;
        this.voltage = voltage;
        this.amperage = amperage;
    }

    public MachineEnergyParams(RecipedMachineStats stats) {
        this(stats.tier, stats.voltageIn, stats.amperageIn);
    }



    public boolean areValid(MachineEnergyParams directMachineParams) {
        return  directMachineParams.amperage >= amperage &&
                directMachineParams.voltage >= voltage &&
                directMachineParams.tier >= tier;
    }

    //computation, quantum energy et.c

}
