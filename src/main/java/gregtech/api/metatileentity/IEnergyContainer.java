package gregtech.api.metatileentity;

import gregtech.api.interfaces.tileentity.IColoredTileEntity;
import gregtech.api.interfaces.tileentity.IEnergyConnected;
import idealindustrial.util.energy.EUConsumer;
import idealindustrial.util.energy.EUProducer;

public interface IEnergyContainer {

    EUProducer getProducer(int side);

    EUConsumer getConsumer(int side);
}
