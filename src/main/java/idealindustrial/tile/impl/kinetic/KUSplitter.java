package idealindustrial.tile.impl.kinetic;

import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.objects.GT_RenderedTexture;
import idealindustrial.tile.IOType;
import idealindustrial.tile.impl.TileFacing1Output;
import idealindustrial.tile.impl.connected.ConnectedRotor;
import idealindustrial.tile.interfaces.host.HostMachineTile;
import idealindustrial.tile.interfaces.meta.Tile;
import idealindustrial.util.energy.kinetic.KUConsumer;
import idealindustrial.util.energy.kinetic.KUProducer;
import idealindustrial.util.energy.kinetic.KineticEnergyHandler;
import idealindustrial.util.energy.kinetic.system.KineticSystem;
import idealindustrial.util.energy.kinetic.system.KineticSystemHandler;
import idealindustrial.util.misc.II_DirUtil;
import idealindustrial.util.misc.II_StreamUtil;
import idealindustrial.util.misc.II_TileUtil;

public class KUSplitter extends TileFacing1Output<HostMachineTile> implements KUConsumer, KineticEnergyHandler {

    public static KUSplitter testMachine() {
        return new KUSplitter(II_TileUtil.makeBaseMachineTile(), "KU Splitter",
                II_StreamUtil.repeated(Textures.BlockIcons.CONCRETE_DARK_COBBLE, 8).map(GT_RenderedTexture::new).toArray(ITexture[]::new),
                II_StreamUtil.setInNullAr(idealindustrial.textures.Textures.input, new ITexture[8], 3, 7)
        );
    }

    SubSystem[] connected = new SubSystem[6];
    int speed = 0;
    int[] powers = new int[6];
    int totalPower = 0;

    public KUSplitter(HostMachineTile hostTile, String name, ITexture[] baseTextures, ITexture[] overlays) {
        super(hostTile, name, baseTextures, overlays);
    }

    protected KUSplitter(HostMachineTile hostTile, TileFacing1Output<?> copyFrom) {
        super(hostTile, copyFrom);
    }

    @Override
    public KineticEnergyHandler getKineticHandler() {
        return this;
    }

    @Override
    public Tile<HostMachineTile> newMetaTile(HostMachineTile baseTile) {
        return new KUSplitter(baseTile, this);
    }

    @Override
    public boolean getIOatSide(int side, IOType type, boolean input) {
        return type == IOType.Kinetic && (!input || side == outputFacing);
    }

    @Override
    protected IOType getOutputIOType() {
        return IOType.Kinetic;
    }

    @Override
    protected void onOutputFacingChanged() {
        super.onOutputFacingChanged();
        for (SubSystem subSystem : connected) {
            if (subSystem != null) {
                subSystem.invalidate();
            }
        }
    }

    @Override
    public void onPostTick(long timer, boolean serverSide) {
        super.onPostTick(timer, serverSide);
        for (int i = 0; i < 6; i++) {
            if (connected[i] == null && i != outputFacing) {
                Tile<?> tile = II_TileUtil.getMetaTileAtSide(hostTile, i);
                if (tile instanceof ConnectedRotor && ((ConnectedRotor) tile).isConnected(II_DirUtil.getOppositeSide(i))) {
                    KineticSystemHandler.addSystem(new SubSystem(hostTile, i));
                }
            }
        }
    }

    @Override
    public int getPowerUsage() {
        totalPower = 0;
        for (int i = 0, connectedLength = connected.length; i < connectedLength; i++) {
            SubSystem subSystem = connected[i];
            if (subSystem == null) {
                continue;
            }
            totalPower += subSystem.calculatePower();
            powers[i] = totalPower;
        }
        return totalPower;
    }

    @Override
    public void supply(int power, int speed) {
        this.speed = speed;
        float powerModification = ((float) power) / totalPower;
        powerModification = Math.min(powerModification, 1f);
        for (int i = 0; i < 6; i++) {
            if (connected[i] == null) {
                continue;
            }
            connected[i].supply((int) (powers[i] * powerModification));
        }
    }

    @Override
    public KUConsumer getConsumer(int side) {
        return side == outputFacing ? this : null;
    }

    @Override
    public KUProducer getProducer(int side) {
        return side != outputFacing ? new SidedProducer(side) : null;
    }

    @Override
    public boolean hasKineticEnergy() {
        return true;
    }

    private class SubSystem extends KineticSystem {

        int[] requests;
        int requestPower;

        public SubSystem(HostMachineTile tile, int side) {
            super(tile, side);
        }

        @Override
        public boolean shouldUpdate() {
            return false;
        }

        protected int calculatePower() {
            requestPower = 0;
            requests = new int[consumers.size()];
            for (int i = 0; i < consumers.size(); i++) {
                KUConsumer consumer = consumers.get(i);
                requests[i] = consumer.getPowerUsage();
                requestPower += requests[i];
            }
            lastSpeed = speed;
            return requestPower;
        }

        protected void supply(int power) {
            float powerModification = ((float) power) / requestPower;
            powerModification = Math.min(powerModification, 1f);
            int calculatedSpeed = speed;
            for (int i = 0; i < consumers.size(); i++) {
                KUConsumer consumer = consumers.get(i);
                consumer.supply((int) (powerModification * requests[i]), calculatedSpeed);
            }
        }

        @Override
        public void update() {

        }

        @Override
        public void invalidate() {
            if (isValid) {
                super.invalidate();
                speed = 0;
                for (int i = 0; i < 6; i++) {
                    if (connected[i] == null) {
                        continue;
                    }
                    connected[i].invalidate();
                }
            }
        }
    }


    private class SidedProducer implements KUProducer {
        int side;

        public SidedProducer(int side) {
            this.side = side;
        }

        @Override
        public int getSpeed(int powerRequest) {
            return 0;
        }

        @Override
        public int getTotalPower() {
            return 0;
        }

        @Override
        public void onAddedToSystem(KineticSystem system) {

        }

        @Override
        public void onConnectionAppended() {
            if (connected[side] != null) {
                connected[side].invalidate();
            }
        }

        @Override
        public void setSystem(KineticSystem system) {
            assert system == null || system instanceof SubSystem;
            connected[side] = (SubSystem) system;
            if (system != null) {
                system.setShouldUpdate(false);
            }
        }
    }

}
