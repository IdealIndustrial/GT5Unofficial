package idealindustrial.util.energy.system;

import idealindustrial.tile.meta.connected.II_MetaConnected_Cable;

import java.util.ArrayList;
import java.util.List;

public class Cross implements IEnergyNode {

    protected List<EnergyConnection> connections;
    protected II_MetaConnected_Cable cable;
    protected long amperage, voltage;

    public Cross(II_MetaConnected_Cable cable) {
        this.connections = new ArrayList<>();
        this.cable = cable;
    }

    @Override
    public EnergyNodeType type() {
        return EnergyNodeType.CROSS;
    }

    @Override
    public List<EnergyConnection> connections() {
        return connections;
    }

    @Override
    public long maxAmperage() {
        return cable.getAmperage();
    }

    @Override
    public long maxVoltage() {
        return cable.getVoltage();
    }

    @Override
    public long loss() {
        return cable.getLoss();
    }

    @Override
    public void onPassing(long voltage, long amperage) {
        this.voltage += voltage;
        this.amperage += amperage;
    }

    @Override
    public void invalidate() {
        cable.onSystemInvalidate();
    }

    @Override
    public void update() {
        if (voltage > maxVoltage() || amperage > maxAmperage()) {
            cable.checkEnergy(voltage, amperage);
        }
        voltage = amperage = 0;
    }

    protected void addConnection(EnergyConnection connection) {
        assert !connections.contains(connection);
        connections.add(connection);
    }
}
