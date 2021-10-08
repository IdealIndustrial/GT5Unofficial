package idealindustrial.util.energy.electric.system;

import gregtech.api.util.GT_Utility;
import idealindustrial.tile.interfaces.host.HostMachineTile;
import idealindustrial.tile.interfaces.host.HostTile;
import idealindustrial.tile.interfaces.meta.Tile;
import idealindustrial.tile.impl.connected.ConnectedCable;
import idealindustrial.util.energy.electric.EnergyHandler;
import idealindustrial.util.misc.II_DirUtil;
import idealindustrial.util.misc.II_TileUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

import java.util.*;

public class CableSystem {
    protected boolean valid = true;
    protected List<NodeProducer> producers = new ArrayList<>();
    protected List<NodeConsumer> consumers = new ArrayList<>();
    protected List<IEnergyPassThrough> passThroughList = new ArrayList<>();
    protected Map<ConnectedCable, Cross> crossMap = new HashMap<>(); // tempMap for system construction and
    protected Map<ConnectedCable, IInfoEnergyPassThrough> cableMap = new HashMap<>(); // map from cable to system segment
    protected EnergyPathCache cache = new EnergyPathCache(this);
    protected List<ConnectedCable> cables = new ArrayList<>();
    protected List<CalculationTask> tasks = new ArrayList<>();

    public void invalidate() {
        valid = false;
        passThroughList.forEach(IEnergyPassThrough::invalidate);
        producers.forEach(p -> p.setSystem(null));
    }

    public boolean isValid() {
        return valid;
    }

    public void update() {
        for (NodeConsumer consumer : consumers) {
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
                producer.consume(transfer);
                long voltage = producer.voltage() - path.loss();
                if (voltage <= 0) {
                    continue;
                }
                if (tasks.isEmpty()) {
                    path.onPassing(producer.voltage(), transfer);
                }
                else {
                    path.fullSimulate(producer.voltage(), transfer); //if we are checking voltage and amperage we should simulate better
                }
                consumer.acceptEnergy(voltage, transfer);
                need -= transfer;
                if (need <= 0) {
                    break;
                }
            }
        }
        for (NodeProducer producer : producers) {
            producer.reset();
        }

        for (Iterator<CalculationTask> iterator = tasks.iterator(); iterator.hasNext(); ) {
            CalculationTask task = iterator.next();
            task.update();
            if (task.finished()) {
                iterator.remove();
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
    public CableSystem(HostMachineTile tile, int side) {
        assert tile.getEnergyHandler().getProducer(side) != null;
        NodeProducer firstNode = new NodeProducer(tile.getProducer(side));
        firstNode.setConnection(constructConnection(tile, firstNode, new ArrayList<>(), side));
        fireConnectionConstructed(firstNode.connection);
        producers.add(firstNode);
        producers.forEach(p -> p.setSystem(this));
        passThroughList.forEach(p -> p.setSystem(this));
        cables.forEach(c -> c.system = this);

    }

    protected void constructCross(Cross cross, ConnectedCable cable, EnergyConnection connection, int connectionSide) {
        cross.addConnection(connection);
        fireConnectionConstructed(connection);
        for (int i = 0; i < 6; i++) {
            if (i == connectionSide) {
                continue;
            }
            if (cable.isConnected(i)) {
                EnergyConnection nextConnection = constructConnection(cable.getHost(), cross, new ArrayList<>(), i);
                if (nextConnection.isValid()) {
                    cross.addConnection(nextConnection);
                    fireConnectionConstructed(nextConnection);
                } else {
                    cables.addAll(nextConnection.cables);
                }
            }
        }
        fireCrossConstructed(cross);
    }

    //currently only II_BaseTile producer type is supported.
    // If some integration is necessary II_BaseTile should be replaced with some type of IWorldReader
    protected EnergyConnection constructConnection(HostTile tile, IEnergyNode node1, List<ConnectedCable> cables, int sideTo) {
        Tile<?> metaTile = II_TileUtil.getMetaTileAtSide(tile, sideTo);
        if (metaTile instanceof ConnectedCable) {
            ConnectedCable cable = (ConnectedCable) metaTile;
            int count = cable.connectionCount();
            if (count == 1) {
                cables.add(cable);
                return new EnergyConnection(cables, node1, null);
            } else if (count == 2) {
                cables.add(cable);
                int opSide = II_DirUtil.getOppositeSide(sideTo);
                for (int i = 0; i < 6; i++) {
                    if (i == opSide) {
                        continue;
                    }
                    if (cable.isConnected(i)) {
                        return constructConnection(cable.getHost(), node1, cables, i);
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
        if (tileEntity instanceof HostTile) {
            HostMachineTile next = (HostMachineTile) tileEntity;
            EnergyHandler handler = next.getEnergyHandler();
            if (handler.getProducer(side) != null) {
                NodeProducer producer = new NodeProducer(handler.getProducer(side), connection);
                producers.add(producer);
                return producer;
            } else if (handler.getConsumer(side) != null) {
                NodeConsumer consumer = new NodeConsumer(handler.getConsumer(side), connection);
                consumers.add(consumer);
                return consumer;
            }
        }
        return null;
    }

    protected void fireConnectionConstructed(EnergyConnection connection) {
        passThroughList.add(connection);
        connection.cables.forEach(c -> cableMap.put(c, connection));
    }

    protected void fireCrossConstructed(Cross cross) {
        passThroughList.add(cross);
        cableMap.put(cross.cable, cross);
    }

    public IInfoEnergyPassThrough getInfo(ConnectedCable cable) {
        return cableMap.get(cable);
    }

    //todo: replace may be with submit callback or something like that
    public void submitTask(int duration, EntityPlayer player, IInfoEnergyPassThrough info) {
        tasks.add(new CalculationTask(duration, player, info));
    }

    protected static class CalculationTask {
        int durationLeft;
        int duration;
        long totalV, maxV, totalA, maxA;
        EntityPlayer player;
        IInfoEnergyPassThrough info;

        public CalculationTask(int duration, EntityPlayer player, IInfoEnergyPassThrough info) {
            this.duration = this.durationLeft = duration;
            this.player = player;
            this.info = info;
        }

        public void update() {
            if (info != null) {
                totalA += info.amperageLastTick();
                totalV += info.voltageLastTick();
                maxA = Math.max(maxA, info.amperageLastTick());
                maxV = Math.max(maxV, info.voltageLastTick());
            }
            durationLeft--;
            if (durationLeft <= 0) {
                GT_Utility.sendChatToPlayer(player, "Max Voltage/Amperage last second: " + maxV + "/" + maxA);
                GT_Utility.sendChatToPlayer(player, "Average Voltage/Amperage last second: " + (totalV / duration) + "/" + (((double) maxA) / duration));
            }
        }

        public boolean finished() {
            return durationLeft <= 0;
        }
    }


}
