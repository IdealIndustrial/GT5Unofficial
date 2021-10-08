package gregtech.api.metatileentity;

import idealindustrial.util.energy.electric.EUConsumer;
import idealindustrial.util.energy.electric.EUProducer;

public interface IEnergyContainer {

    EUProducer getProducer(int side);

    EUConsumer getConsumer(int side);
}
