package idealindustrial.util.energy.system;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class EnergyPathCache {

    protected final CableSystem system;
    protected Map<Pair<NodeProducer, NodeConsumer>, EnergyPath> cache = new HashMap<>();


    public EnergyPathCache(CableSystem system) {
        this.system = system;
    }

    public EnergyPath get(NodeProducer producer, NodeConsumer consumer) {
        Pair<NodeProducer, NodeConsumer> pair = new ImmutablePair<>(producer, consumer);
        EnergyPath path = cache.get(pair);
        if (path != null) {
            return path;
        }
        cacheWays(producer);
        assert cache.containsKey(pair);
        return cache.get(pair);
    }

    protected void cacheWays(NodeProducer producer) {
        EnergyPath path = new EnergyPath(new ArrayList<>());
        path.from = producer;
        path.append(producer.connection);
        nextStep(producer.connection.getOther(producer), path, new HashSet<>());

    }

    protected void nextStep(IEnergyNode node, EnergyPath path, Set<IEnergyNode> passedCrossSet) {
        switch (node.type()) {
            case CROSS:
                if (!passedCrossSet.add(node)) {
                    return;
                }
                path.append(node);
                for (EnergyConnection connection : node.connections()) {
                    EnergyPath copy = path.copy();
                    copy.append(connection);
                    nextStep(connection.getOther(node), copy, passedCrossSet);
                }
            case PRODUCER:
                return;
            case CONSUMER:
                path.to = (NodeConsumer) node;
                cache.put(new ImmutablePair<>(path.from, path.to), path);
        }
    }
}
