package idealindustrial.impl.tile.impl.kinetic;

import idealindustrial.api.textures.ITexture;
import idealindustrial.api.tile.energy.kinetic.KUSplitter;
import idealindustrial.api.tile.energy.kinetic.KineticEnergyHandler;
import idealindustrial.impl.textures.RenderedTexture;
import idealindustrial.impl.textures.TextureManager;
import idealindustrial.impl.tile.IOType;
import idealindustrial.impl.tile.impl.TileFacing1Output;
import idealindustrial.api.tile.host.HostMachineTile;
import idealindustrial.api.tile.meta.Tile;
import idealindustrial.impl.tile.energy.kinetic.*;
import idealindustrial.impl.tile.energy.kinetic.system.KineticSystem;
import idealindustrial.util.misc.II_StreamUtil;
import idealindustrial.util.misc.II_TileUtil;

public class TileKUSplitter extends TileFacing1Output<HostMachineTile> implements KUSplitter {

    KineticSystem system;
    public static TileKUSplitter testMachine() {
        return new TileKUSplitter(II_TileUtil.makeBaseMachineTile(), "KU Splitter",
                II_StreamUtil.repeated("test/cob3", 8).map(TextureManager.INSTANCE::blockTexture).map(RenderedTexture::new).toArray(ITexture[]::new),
                II_StreamUtil.setInNullAr(idealindustrial.impl.textures.Textures.input, new ITexture[8], 3, 7)
        );
    }

    public TileKUSplitter(HostMachineTile hostTile, String name, ITexture[] baseTextures, ITexture[] overlays) {
        super(hostTile, name, baseTextures, overlays);
        hostTile.onWorldStateUpdated(wa -> {
            if (system != null) {
                system.invalidate();
            }
        });
    }

    protected TileKUSplitter(HostMachineTile hostTile, TileFacing1Output<?> copyFrom) {
        super(hostTile, copyFrom);
    }

    @Override
    public Tile<HostMachineTile> newMetaTile(HostMachineTile baseTile) {
        return new TileKUSplitter(baseTile, this);
    }

    @Override
    public boolean getIOatSide(int side, IOType type, boolean input) {
        return type == IOType.Kinetic;
    }

    @Override
    public boolean hasKineticEnergy() {
        return true;
    }

    @Override
    public KineticEnergyHandler getKineticHandler() {
        return EmptyKineticHandler.INSTANCE;
    }

    //
//    @Override
//    protected IOType getOutputIOType() {
//        return IOType.Kinetic;
//    }
//
    @Override
    protected void onOutputFacingChanged() {
        super.onOutputFacingChanged();
        if (system != null) {
            system.invalidate();
        }
    }

    @Override
    public void onPostTick(long timer, boolean serverSide) {
        super.onPostTick(timer, serverSide);
    }

    @Override
    public int getInputSide() {
        return outputFacing;
    }

    @Override
    public void setSystem(KineticSystem system) {
        this.system = system;
    }
}
