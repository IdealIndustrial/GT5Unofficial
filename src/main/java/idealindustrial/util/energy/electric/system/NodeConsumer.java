package idealindustrial.util.energy.electric.system;

import idealindustrial.util.energy.electric.EUConsumer;

import java.util.Collections;
import java.util.List;

//wrapper class... maybe it can be removed?
public class NodeConsumer implements IEnergyNode, EUConsumer {

    protected EUConsumer consumer;
    protected EnergyConnection connection;

    public NodeConsumer(EUConsumer consumer, EnergyConnection connection) {
        this.consumer = consumer;
        this.connection = connection;
    }


    @Override
    public boolean needsEnergy() {
        return consumer.needsEnergy();
    }

    @Override
    public long requestAmperes() {
        return consumer.requestAmperes();
    }

    @Override
    public void acceptEnergy(long voltage, long amperage) {
        consumer.acceptEnergy(voltage, amperage);
    }

    @Override
    public EnergyNodeType type() {
        return EnergyNodeType.CONSUMER;
    }

    @Override
    public List<EnergyConnection> connections() {
        return Collections.singletonList(connection);
    }

    @Override
    public long maxAmperage() {
        return 0;
    }

    @Override
    public long maxVoltage() {
        return 0;
    }

    @Override
    public long loss() {
        return 0;
    }

    @Override
    public void onPassing(long voltage, long amperage) {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NodeConsumer consumer1 = (NodeConsumer) o;
        return consumer.equals(consumer1.consumer);
    }

    @Override
    public int hashCode() {
        return consumer.hashCode();
    }

    @Override
    public void update() {

    }
}
