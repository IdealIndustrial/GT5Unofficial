package idealindustrial.util.energy;

import idealindustrial.util.misc.II_NBTSerializable;

public abstract class II_EnergyHandler implements II_NBTSerializable {
    //class with public field... todo: check about inlining ( may be replace with interface)

    public long stored = 0L;

    public abstract EUProducer getProducer(int side);

    public abstract EUConsumer getConsumer(int side);

}
