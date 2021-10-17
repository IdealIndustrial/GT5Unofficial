package idealindustrial.tile.impl.kinetic;

import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.objects.GT_RenderedTexture;
import idealindustrial.tile.IOType;
import idealindustrial.tile.impl.TileFacing1Output;
import idealindustrial.tile.impl.connected.ConnectedRotor;
import idealindustrial.tile.interfaces.host.HostMachineTile;
import idealindustrial.tile.interfaces.meta.Tile;
import idealindustrial.util.energy.kinetic.*;
import idealindustrial.util.energy.kinetic.system.KineticSystem;
import idealindustrial.util.energy.kinetic.system.KineticSystemHandler;
import idealindustrial.util.misc.II_DirUtil;
import idealindustrial.util.misc.II_StreamUtil;
import idealindustrial.util.misc.II_TileUtil;

import java.util.Set;

public class TileKUSplitter extends TileFacing1Output<HostMachineTile> implements KUSplitter {

    KineticSystem system;
    public static TileKUSplitter testMachine() {
        return new TileKUSplitter(II_TileUtil.makeBaseMachineTile(), "KU Splitter",
                II_StreamUtil.repeated(Textures.BlockIcons.CONCRETE_DARK_COBBLE, 8).map(GT_RenderedTexture::new).toArray(ITexture[]::new),
                II_StreamUtil.setInNullAr(idealindustrial.textures.Textures.input, new ITexture[8], 3, 7)
        );
    }

    public TileKUSplitter(HostMachineTile hostTile, String name, ITexture[] baseTextures, ITexture[] overlays) {
        super(hostTile, name, baseTextures, overlays);
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
