package idealindustrial.recipe;

import idealindustrial.util.misc.II_Util;

public class II_RecipeEnergyParams extends II_MachineEnergyParams{

    public final long duration;

    public II_RecipeEnergyParams(long voltage, long amperage, long duration) {
        super(II_Util.getTier(voltage), voltage, amperage);
        this.duration = duration;
    }

    public long total() {
        return duration * voltage * amperage;
    }
}
