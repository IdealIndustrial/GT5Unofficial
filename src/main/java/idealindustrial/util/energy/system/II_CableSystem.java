package idealindustrial.util.energy.system;

import idealindustrial.tile.base.II_BaseTile;
import idealindustrial.tile.meta.II_MetaTile;
import idealindustrial.tile.meta.connected.II_MetaConnected_Cable;
import idealindustrial.util.energy.EUConsumer;
import idealindustrial.util.energy.EUProducer;
import idealindustrial.util.energy.II_EnergyHandler;
import idealindustrial.util.misc.II_DirUtil;
import idealindustrial.util.misc.II_TileUtil;
import net.minecraft.tileentity.TileEntity;

import java.util.*;

public class II_CableSystem {
    protected boolean valid = true;
    protected List<NodeProducer> producers = new ArrayList<>();
    protected List<NodeConsumer> consumers = new ArrayList<>();
    protected List<IEnergyPassThrough> passThroughList = new ArrayList<>();
    protected Map<II_MetaConnected_Cable, Cross> crossMap = new HashMap<>(); // tempMap for system construction
    protected EnergyPathCache cache = new EnergyPathCache(this);

    public void invalidate() {
        valid = false;
        passThroughList.forEach(IEnergyPassThrough::invalidate);
        producers.forEach(p -> p.setHasSystem(false));
    }

    public boolean isValid() {
        return valid;
    }

    public void update() {
        for(NodeConsumer consumer : consumers) {
            if (!consumer.needsEnergy()) {
                continue;
            }
            long need = consumer.requestAmperes();
            for (NodeProducer producer : producers) {
                if (!producer.hasEnergy()) {
                    continue;
                }
                long transfer = Math.min(producer.availableAmperes(), need);
                EnergyPath path = cache.get(producer, consumer);
                assert path != null;
                long voltage = producer.voltage() - path.loss();
                path.onPassing(voltage, transfer);
                consumer.acceptEnergy(voltage, transfer);
                need -= transfer;
                if (need <= 0) {
                    break;
                }
            }
        }
        for (IEnergyPassThrough passThrough : passThroughList) {
            passThrough.update();
        }
    }


    /**
     * cable system can be constructed only if produces exists so
     *
     * @param tile tile of producer
     * @param side side of producer
     */
    public II_CableSystem(II_BaseTile tile, int side) {
        assert tile.getEnergyHandler().getProducer(side) != null;
        NodeProducer firstNode = new NodeProducer(tile.getProducer(side));
        firstNode.setConnection(constructConnection(tile, firstNode, new ArrayList<>(), II_DirUtil.getOppositeSide(side)));
        producers.add(firstNode);
        producers.forEach(p -> p.setHasSystem(true));
    }

    protected void constructCross(Cross cross, II_MetaConnected_Cable cable, EnergyConnection connection, int connectionSide) {
        cross.addConnection(connection);
        for (int i = 0; i < 6; i++) {
            if (i == connectionSide) {
                continue;
            }
            if (cable.isConnected(i)) {
                EnergyConnection nextConnection = constructConnection(cable.getBase(), cross, new ArrayList<>(), i);
                if (nextConnection.isValid()) {
                    passThroughList.add(nextConnection);
                }
            }
        }
        passThroughList.add(cross);
    }

    //currently only II_BaseTile producer type is supported.
    // If some integration is necessary II_BaseTile should be replaced with some type of IWorldReader
    protected EnergyConnection constructConnection(II_BaseTile tile, IEnergyNode node1, List<II_MetaConnected_Cable> cables, int sideTo) {
        II_MetaTile metaTile = II_TileUtil.getMetaTileAtSide(tile, sideTo);
        if (metaTile instanceof II_MetaConnected_Cable) {
            II_MetaConnected_Cable cable = (II_MetaConnected_Cable) metaTile;
            int count = cable.connectionCount();
            if (count == 1) {
                return new EnergyConnection(cables, node1, null);
            } else if (count == 2) {
                cables.add(cable);
                for (int i = 0; i < 6; i++) {
                    if (i == sideTo) {
                        continue;
                    }
                    if (cable.isConnected(i)) {
                        return constructConnection(cable.getBase(), node1, cables, i);
                    }
                }
                throw new EnergySystemConstructionException("wrong");
            } else if (count > 2) {
                if (crossMap.containsKey(cable)) {
                    Cross cross = crossMap.get(cable);
                    return new EnergyConnection(cables, node1, cross);
                } else {
                    Cross cross = new Cross(cable);
                    crossMap.put(cable, cross);
                    EnergyConnection connection = new EnergyConnection(cables, node1, cross);
                    constructCross(cross, cable, connection, II_DirUtil.getOppositeSide(sideTo));
                    return connection;
                }
            }
        }

        TileEntity tileEntity = tile.getTileEntityAtSide(sideTo);
        int side = II_DirUtil.getOppositeSide(sideTo);
        EnergyConnection connection = new EnergyConnection(cables, node1, null);
        connection.node2 = getEnergyNode(tileEntity, side, connection);
        return connection;
    }


    protected IEnergyNode getEnergyNode(TileEntity tileEntity, int side, EnergyConnection connection) {
        if (tileEntity == null) {
            return null;
        }
        if (tileEntity instanceof II_BaseTile) {
            II_BaseTile next = (II_BaseTile) tileEntity;
            II_EnergyHandler handler = next.getEnergyHandler();
            if (handler.getProducer(side) != null) {
                NodeProducer producer = new NodeProducer(handler.getProducer(side), connection);
                producers.add(producer);
                return producer;
            }
            else if (handler.getConsumer(side) != null) {
                NodeConsumer consumer = new NodeConsumer(handler.getConsumer(side), connection);
                consumers.add(consumer);
                return consumer;
            }
        }
        return null;
    }


}
