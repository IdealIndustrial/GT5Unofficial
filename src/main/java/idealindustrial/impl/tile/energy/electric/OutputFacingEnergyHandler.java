package idealindustrial.impl.tile.energy.electric;

import idealindustrial.impl.tile.impl.TileFacing1Output;

@Deprecated
public class OutputFacingEnergyHandler extends OutputEnergyHandler {
    private OutputFacingEnergyHandler(TileFacing1Output<?> metaTile, long minEnergyAmount, long maxCapacity, long voltageOut, long amperageOut, int outSide) {
        super(metaTile.getHost(), minEnergyAmount, maxCapacity, voltageOut, amperageOut, outSide);
    }

}
