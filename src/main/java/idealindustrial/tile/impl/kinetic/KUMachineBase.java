package idealindustrial.tile.impl.kinetic;

import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.objects.GT_RenderedTexture;
import idealindustrial.tile.IOType;
import idealindustrial.tile.impl.TileFacing2Main;
import idealindustrial.tile.interfaces.host.HostMachineTile;
import idealindustrial.tile.interfaces.meta.Tile;
import idealindustrial.util.energy.kinetic.KUConsumer;
import idealindustrial.util.energy.kinetic.KUProducer;
import idealindustrial.util.energy.kinetic.KineticEnergyHandler;
import idealindustrial.util.misc.II_StreamUtil;
import idealindustrial.util.misc.II_TileUtil;

public class KUMachineBase extends TileFacing2Main<HostMachineTile> implements KineticEnergyHandler, KUConsumer {

    public static KUMachineBase testMachine() {
        return new KUMachineBase(II_TileUtil.makeBaseMachineTile(), "TestConsumerKinetic",
                II_StreamUtil.repeated(Textures.BlockIcons.CONCRETE_DARK_COBBLE_MOSSY, 10).map(GT_RenderedTexture::new).toArray(ITexture[]::new),
                II_StreamUtil.setInNullAr(idealindustrial.textures.Textures.input, new ITexture[10], 3, 8)
                );
    }

    public KUMachineBase(HostMachineTile baseTile, String name, ITexture[] baseTextures, ITexture[] overlays) {
        super(baseTile, name, baseTextures, overlays);
    }

    public KUMachineBase(HostMachineTile baseTile, KUMachineBase copyFrom) {
        super(baseTile, copyFrom);
    }

    @Override
    public boolean hasKineticEnergy() {
        return true;
    }

    @Override
    public boolean getIOatSide(int side, IOType type, boolean input) {
        return type == IOType.Kinetic && side == outputFacing && input;
    }

    @Override
    protected IOType getOutputIOType() {
        return IOType.Kinetic;
    }

    @Override
    public Tile<HostMachineTile> newMetaTile(HostMachineTile baseTile) {
        return new KUMachineBase(baseTile, this);
    }

    @Override
    public int getPowerUsage() {
        return 10;
    }

    @Override
    public void supply(int power, int speed) {
//        System.out.println("Got " + power + " " + speed) ;
    }

    @Override
    public KUConsumer getConsumer(int side) {
        if (side == outputFacing) {
            return this;
        }
        return null;
    }

    @Override
    public KUProducer getProducer(int side) {
        return null;
    }


    @Override
    public KineticEnergyHandler getKineticHandler() {
        return this;
    }
}
