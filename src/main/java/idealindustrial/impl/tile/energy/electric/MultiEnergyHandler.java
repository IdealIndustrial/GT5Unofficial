package idealindustrial.impl.tile.energy.electric;

import idealindustrial.api.tile.energy.electric.EUConsumer;
import idealindustrial.api.tile.energy.electric.EUProducer;
import idealindustrial.impl.recipe.MachineEnergyParams;
import idealindustrial.util.misc.II_Util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MultiEnergyHandler extends EnergyHandler {
    List<EnergyHandler> inHandlers = new ArrayList<>(), outHandlers = new ArrayList<>();

    public void addIn(EnergyHandler energyHandler) {
        assert !inHandlers.contains(energyHandler);
        inHandlers.add(energyHandler);
    }

    public void addOut(EnergyHandler energyHandler) {
        assert !outHandlers.contains(energyHandler);
        outHandlers.add(energyHandler);
    }

    public boolean isEmpty() {
        return inHandlers.isEmpty() && outHandlers.isEmpty();
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
        return inHandlers.stream().anyMatch(EnergyHandler::isAlmostFull);
    }

    @Override
    public MachineEnergyParams getParams() {//xtremely shitty, add cache at lest
        long voltage = 0;
        long amperage = 0;
        for (EnergyHandler h : inHandlers) {
            voltage = Math.max(voltage, h.getParams().voltage);
            amperage += h.getParams().amperage;
        }
        return new MachineEnergyParams(II_Util.getTier(voltage), voltage, amperage);
    }

    @Override
    public long drain(long energy, boolean doDrain) {
        Iterator<EnergyHandler> iterator = inHandlers.iterator();
        long drained = 0;
        while (energy - drained > 0 && iterator.hasNext()) {
            drained += iterator.next().drain(energy - drained, doDrain);
        }
        return drained;
    }

    @Override
    public long fill(long energy, boolean doFill) {
        Iterator<EnergyHandler> iterator = outHandlers.iterator();
        long filled = 0;
        while (energy - filled > 0 && iterator.hasNext()) {
            filled += iterator.next().fill(energy - filled, doFill);
        }
        return filled;
    }
}
