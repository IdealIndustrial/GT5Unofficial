package idealindustrial.util.energy.system;

import idealindustrial.tile.meta.connected.II_MetaConnected_Cable;

import java.util.List;

public class EnergyConnection implements IEnergyPassThrough {
    protected long maxAmperage, maxVoltage, loss;
    protected long currentAmperage, currentVoltage;
    protected List<II_MetaConnected_Cable> cables;
    protected IEnergyNode node1, node2;

    public EnergyConnection(List<II_MetaConnected_Cable> cables, IEnergyNode node1, IEnergyNode node2) { //todo: think about lazy loss amperage and voltage
        this.cables = cables;
        this.maxAmperage = cables.stream().mapToLong(II_MetaConnected_Cable::getAmperage).min().orElse(Long.MAX_VALUE);
        this.maxVoltage = cables.stream().mapToLong(II_MetaConnected_Cable::getVoltage).min().orElse(Long.MAX_VALUE);
        this.loss = cables.stream().mapToLong(II_MetaConnected_Cable::getLoss).sum();
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
    public void onPassing(long voltage, long amperage) {
        currentAmperage += amperage;
        currentVoltage += voltage;
    }

    @Override
    public void invalidate() {
        cables.forEach(II_MetaConnected_Cable::onSystemInvalidate);
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
            return node1;
        }
        assert node == node2;
        return node2;
    }
}
