package gregtech.api.metatileentity.reimplement.basetile;

import gregtech.api.interfaces.tileentity.IEnergyConnected;
import idealindustrial.util.energy.EUConsumer;
import idealindustrial.util.energy.EUProducer;

public interface IEnergyContainer extends IEnergyConnected {

    EUProducer getProducer(int side);

    EUConsumer getConsumer(int side);
}
