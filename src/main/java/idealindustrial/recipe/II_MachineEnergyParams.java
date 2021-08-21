package idealindustrial.recipe;

import idealindustrial.util.parameter.II_RecipedMachineStats;

public class II_MachineEnergyParams {

    public final int tier;
    public final long voltage;
    public final long amperage;

    public II_MachineEnergyParams(int tier, long voltage, long amperage) {
        this.tier = tier;
        this.voltage = voltage;
        this.amperage = amperage;
    }

    public II_MachineEnergyParams(II_RecipedMachineStats stats) {
        this(stats.tier, stats.voltageIn, stats.amperageIn);
    }



    public boolean areValid(II_MachineEnergyParams directMachineParams) {
        return  directMachineParams.amperage >= amperage &&
                directMachineParams.voltage >= voltage &&
                directMachineParams.tier >= tier;
    }

    //computation, quantum energy et.c

}
