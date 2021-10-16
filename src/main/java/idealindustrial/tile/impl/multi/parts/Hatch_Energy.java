package idealindustrial.tile.impl.multi.parts;

import gregtech.api.interfaces.ITexture;
import idealindustrial.textures.Textures;
import idealindustrial.tile.IOType;
import idealindustrial.tile.impl.multi.MultiMachineBase;
import idealindustrial.tile.interfaces.host.HostMachineTile;
import idealindustrial.tile.interfaces.meta.Tile;
import idealindustrial.util.energy.electric.InputEnergyHandler;
import idealindustrial.util.misc.II_StreamUtil;
import idealindustrial.util.misc.II_Util;

public abstract class Hatch_Energy extends TileHatch<HostMachineTile, MultiMachineBase<?>> {
    int tier;
    protected Hatch_Energy(HostMachineTile hostTile, String name, ITexture outFace, int tier) {
        super(hostTile, name,
                II_StreamUtil.arrayOf(Textures.baseTiredTextures[tier], new ITexture[8]),
                II_StreamUtil.setInNullAr(outFace, new ITexture[8], 3, 7)
        );
        hasEnergy = true;
        this.tier = tier;

    }

    protected Hatch_Energy(HostMachineTile hostTile, Hatch_Energy copyFrom) {
        super(hostTile, copyFrom);
        hasEnergy = true;
        this.tier = copyFrom.tier;
    }

    @Override
    public void onInInventoryModified(int inventory) {
        if (multiBlock != null) {
            multiBlock.onInInventoryModified(inventory);
        }
    }

    public static class EnergyHatch extends Hatch_Energy {

        public EnergyHatch(HostMachineTile hostTile, String name, int tier) {
            super(hostTile, name, Textures.output, tier);
            energyHandler = new InputEnergyHandler(hostTile,0, (2 << (tier + 3)) * 200, II_Util.getVoltage(tier), 2);
        }

        protected EnergyHatch(HostMachineTile hostTile, EnergyHatch copyFrom) {
            super(hostTile, copyFrom);
            energyHandler = new InputEnergyHandler(this.hostTile,0, (2 << (tier + 3)) * 200, II_Util.getVoltage(tier), 2);
        }

        @Override
        public MultiMachineBase.HatchType getType() {
            return MultiMachineBase.HatchType.EnergyIn;
        }

        @Override
        public Tile<HostMachineTile> newMetaTile(HostMachineTile baseTile) {
            return new EnergyHatch(baseTile, this);
        }

        @Override
        public boolean getIOatSide(int side, IOType type, boolean input) {
            if (type.is(IOType.ENERGY)) {
                return side == outputFacing;
            }
            return false;
        }

        @Override
        protected IOType getOutputIOType() {
            return IOType.ENERGY;
        }
    }

    public static class DynamoHatch extends Hatch_Energy {//todo impl

        public DynamoHatch(HostMachineTile hostTile, String name, int tier) {
            super(hostTile, name, Textures.input, tier);
        }

        protected DynamoHatch(HostMachineTile baseTile, DynamoHatch copyFrom) {
            super(baseTile, copyFrom);
        }

        @Override
        public MultiMachineBase.HatchType getType() {
            return MultiMachineBase.HatchType.EnergyOut;
        }

        @Override
        public Tile<HostMachineTile> newMetaTile(HostMachineTile baseTile) {
            return new DynamoHatch(baseTile, this);
        }
    }


}
