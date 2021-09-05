package idealindustrial.util.energy;

import idealindustrial.tile.meta.BaseMetaTile_Facing1Output;

public class OutputFacingEnergyHandler extends OutputEnergyHandler {
    public OutputFacingEnergyHandler(BaseMetaTile_Facing1Output<?> metaTile, long minEnergyAmount, long maxCapacity, long voltageOut, long amperageOut, int outSide) {
        super(metaTile.getBase(), minEnergyAmount, maxCapacity, voltageOut, amperageOut, outSide);
    }

    @Override
    public void onConfigurationChanged() {
        super.onConfigurationChanged();
        outSide = ((BaseMetaTile_Facing1Output<?>) baseTile.getMetaTile()).outputFacing;
    }
}
