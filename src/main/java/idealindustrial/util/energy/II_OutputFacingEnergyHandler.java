package idealindustrial.util.energy;

import idealindustrial.tile.interfaces.base.II_BaseMachineTile;
import idealindustrial.tile.meta.II_BaseMetaTile_Facing1Output;

public class II_OutputFacingEnergyHandler extends II_OutputEnergyHandler {
    public II_OutputFacingEnergyHandler(II_BaseMetaTile_Facing1Output<?> metaTile, long minEnergyAmount, long maxCapacity, long voltageOut, long amperageOut, int outSide) {
        super(metaTile.getBase(), minEnergyAmount, maxCapacity, voltageOut, amperageOut, outSide);
    }

    @Override
    public void onConfigurationChanged() {
        super.onConfigurationChanged();
        outSide = ((II_BaseMetaTile_Facing1Output<?>) baseTile.getMetaTile()).outputFacing;
    }
}
