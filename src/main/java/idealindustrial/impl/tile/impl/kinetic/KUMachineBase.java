package idealindustrial.impl.tile.impl.kinetic;

import gnu.trove.set.TLongSet;
import idealindustrial.api.textures.ITexture;
import idealindustrial.api.tile.energy.kinetic.KineticTile;
import idealindustrial.impl.textures.RenderedTexture;
import idealindustrial.impl.textures.TextureManager;
import idealindustrial.impl.tile.IOType;
import idealindustrial.impl.tile.impl.TileFacing2Main;
import idealindustrial.api.tile.host.HostMachineTile;
import idealindustrial.api.tile.meta.Tile;
import idealindustrial.util.misc.II_StreamUtil;
import idealindustrial.util.misc.II_TileUtil;

import static idealindustrial.api.tile.energy.kinetic.KineticTile.localPoint;

public class KUMachineBase extends TileFacing2Main<HostMachineTile> implements KineticTile {

    public static KUMachineBase testMachine() {
        return new KUMachineBase(II_TileUtil.makeBaseMachineTile(), "TestConsumerKinetic",
                II_StreamUtil.repeated("test/cob1", 10).map(TextureManager.INSTANCE::blockTexture).map(RenderedTexture::new).toArray(ITexture[]::new),
                II_StreamUtil.setInNullAr(idealindustrial.impl.textures.Textures.input, new ITexture[10], 3, 8)
                );
    }

    public KUMachineBase(HostMachineTile baseTile, String name, ITexture[] baseTextures, ITexture[] overlays) {
        super(baseTile, name, baseTextures, overlays);
    }

    public KUMachineBase(HostMachineTile baseTile, KUMachineBase copyFrom) {
        super(baseTile, copyFrom);
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
    public long powerUsage(TLongSet passed, int side, int speed) {
        localPoint.setPosition(hostTile);
        if (!passed.add(localPoint.toLong())) {
            return 0;
        }
        return getPowerRequest();
    }

    @Override
    public void usePower(TLongSet passed, int side, int speed, double satisfaction) {
        localPoint.setPosition(hostTile);
        if (!passed.add(localPoint.toLong())) {
            return;
        }
        powerAdded(speed, satisfaction);
    }

    void powerAdded(int speed, double satisfaction) {

    }

    long getPowerRequest() {
        return 10;
    }
}
