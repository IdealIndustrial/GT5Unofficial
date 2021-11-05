package idealindustrial.impl.recipe;

import idealindustrial.util.misc.II_Util;

public class RecipeEnergyParams extends MachineEnergyParams {

    public final long duration;

    public RecipeEnergyParams(long voltage, long amperage, long duration) {
        super(II_Util.getTier(voltage), voltage, amperage);
        this.duration = duration;
    }

    public long total() {
        return duration * voltage * amperage;
    }
}
