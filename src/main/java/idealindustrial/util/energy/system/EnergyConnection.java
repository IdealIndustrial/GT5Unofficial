package idealindustrial.util.energy.system;

import idealindustrial.tile.meta.connected.MetaConnected_Cable;

import java.util.List;

public class EnergyConnection implements IInfoEnergyPassThrough {
    protected long maxAmperage, maxVoltage, loss;
    protected long currentAmperage, currentVoltage;
    protected List<MetaConnected_Cable> cables;
    protected IEnergyNode node1, node2;

    public EnergyConnection(List<MetaConnected_Cable> cables, IEnergyNode node1, IEnergyNode node2) { //todo: think about lazy loss amperage and voltage
        this.cables = cables;
        this.maxAmperage = cables.stream().mapToLong(MetaConnected_Cable::getAmperage).min().orElse(Long.MAX_VALUE);
        this.maxVoltage = cables.stream().mapToLong(MetaConnected_Cable::getVoltage).min().orElse(Long.MAX_VALUE);
        this.loss = cables.stream().mapToLong(MetaConnected_Cable::getLoss).sum();
        this.node1 = node1;
        this.node2 = node2;
    }

    @Override
    public long maxAmperage() {
        return maxVoltage;
    }

    @Override
    public long maxVoltage() {
        return maxVoltage;
    }

    @Override
    public long loss() {
        return loss;
    }

    @Override
    public void setSystem(CableSystem system) {
        cables.forEach(c -> c.system = system);
    }

    @Override
    public void onPassing(long voltage, long amperage) {
        currentAmperage += amperage;
        currentVoltage = Math.max(voltage, currentVoltage);//todo:same for cross
    }

    @Override
    public void invalidate() {
        cables.forEach(MetaConnected_Cable::onSystemInvalidate);
    }

    @Override
    public void update() {
        if (currentVoltage > maxVoltage() || currentAmperage > maxAmperage()) {
            cables.forEach(c -> c.checkEnergy(currentVoltage, currentAmperage));
        }
        currentVoltage = currentAmperage = 0;
    }

    public boolean isValid() {
        return node1 != null && node2 != null;
    }

    protected IEnergyNode getOther(IEnergyNode node) {
        if (node == node1) {
            return node2;
        }
        assert node == node2;
        return node1;
    }

    @Override
    public long voltageLastTick() {
        return currentVoltage;
    }

    @Override
    public long amperageLastTick() {
        return currentAmperage;
    }
}
