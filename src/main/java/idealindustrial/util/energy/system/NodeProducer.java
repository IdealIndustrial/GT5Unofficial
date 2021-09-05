package idealindustrial.util.energy.system;

import idealindustrial.util.energy.EUProducer;

import java.util.Collections;
import java.util.List;

//wrapper class,,, may be remove ?
public class NodeProducer implements IEnergyNode, EUProducer {

    protected EUProducer producer;
    protected EnergyConnection connection;

    public NodeProducer(EUProducer producer, EnergyConnection connection) {
        this.producer = producer;
        this.connection = connection;
    }

    protected NodeProducer(EUProducer producer) {
        this.producer = producer;
    }

    @Override
    public boolean hasEnergy() {
        return producer.hasEnergy();
    }

    @Override
    public long voltage() {
        return producer.voltage();
    }

    @Override
    public long availableAmperes() {
        return producer.availableAmperes();
    }

    @Override
    public void consume(long amperes) {
        producer.consume(amperes);
    }

    @Override
    public void reset() {
        producer.reset();
    }

    @Override
    public void setSystem(CableSystem system) {
        producer.setSystem(system);
    }

    @Override
    public EnergyNodeType type() {
        return EnergyNodeType.PRODUCER;
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
        NodeProducer producer1 = (NodeProducer) o;
        return producer.equals(producer1.producer);
    }

    @Override
    public int hashCode() {
        return producer.hashCode();
    }


    public void setConnection(EnergyConnection connection) {
        this.connection = connection;
    }
}
