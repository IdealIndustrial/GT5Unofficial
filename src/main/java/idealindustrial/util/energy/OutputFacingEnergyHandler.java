package idealindustrial.util.energy;

import idealindustrial.tile.impl.TileFacing1Output;

public class OutputFacingEnergyHandler extends OutputEnergyHandler {
    public OutputFacingEnergyHandler(TileFacing1Output<?> metaTile, long minEnergyAmount, long maxCapacity, long voltageOut, long amperageOut, int outSide) {
        super(metaTile.getHost(), minEnergyAmount, maxCapacity, voltageOut, amperageOut, outSide);
    }

    @Override
    public void onConfigurationChanged() {
        super.onConfigurationChanged();
        outSide = ((TileFacing1Output<?>) baseTile.getMetaTile()).outputFacing;
    }
}
