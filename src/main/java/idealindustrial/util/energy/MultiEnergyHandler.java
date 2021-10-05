package idealindustrial.util.energy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MultiEnergyHandler extends EnergyHandler {
    List<EnergyHandler> subHandlers = new ArrayList<>();

    public void addHandler(EnergyHandler energyHandler) {
        assert !subHandlers.contains(energyHandler);
        subHandlers.add(energyHandler);
    }

    @Override
    public EUProducer getProducer(int side) {
        return null;
    }

    @Override
    public EUConsumer getConsumer(int side) {
        return null;
    }

    @Override
    public boolean isAlmostFull() {
        return subHandlers.stream().anyMatch(EnergyHandler::isAlmostFull);
    }

    @Override
    public long drain(long energy, boolean doDrain) {
        Iterator<EnergyHandler> iterator = subHandlers.iterator();
        long drained = 0;
        while (energy > 0 && iterator.hasNext()) {
            drained += iterator.next().drain(energy - drained, doDrain);
        }
        return drained;

    }
}
