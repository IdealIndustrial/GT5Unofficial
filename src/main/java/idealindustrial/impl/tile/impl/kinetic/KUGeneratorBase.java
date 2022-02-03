package idealindustrial.impl.tile.impl.kinetic;

import gnu.trove.set.hash.TLongHashSet;
import idealindustrial.api.textures.ITexture;
import idealindustrial.api.tile.energy.kinetic.KineticTile;
import idealindustrial.impl.textures.RenderedTexture;
import idealindustrial.impl.textures.TextureManager;
import idealindustrial.impl.tile.IOType;
import idealindustrial.impl.tile.impl.TileFacing1Output;
import idealindustrial.api.tile.host.HostMachineTile;
import idealindustrial.api.tile.meta.Tile;
import idealindustrial.util.misc.II_DirUtil;
import idealindustrial.util.misc.II_StreamUtil;
import idealindustrial.util.misc.II_TileUtil;

public class KUGeneratorBase extends TileFacing1Output<HostMachineTile> {

    public KUGeneratorBase(HostMachineTile hostTile, String name, ITexture[] baseTextures, ITexture[] overlays) {
        super(hostTile, name, baseTextures, overlays);
    }

    public KUGeneratorBase(HostMachineTile hostTile, TileFacing1Output<?> copyFrom) {
        super(hostTile, copyFrom);
    }

    public static KUGeneratorBase testMachine() {
        return new KUGeneratorBase(II_TileUtil.makeBaseMachineTile(), "Test Kinetic Generator",
                II_StreamUtil.repeated("test/cob2", 8).map(TextureManager.INSTANCE::blockTexture).map(RenderedTexture::new).toArray(ITexture[]::new),
                II_StreamUtil.setInNullAr(idealindustrial.impl.textures.Textures.input, new ITexture[8], 3, 7)
                );
    }

    private TLongHashSet tmpSet = new TLongHashSet();

    @Override
    public void onPostTick(long timer, boolean serverSide) {
        if (!serverSide) {
            return;
        }
        Tile<?> tile = II_TileUtil.getMetaTileAtSide(hostTile, outputFacing);
        if (!(tile instanceof KineticTile)) {
            return;
        }
        KineticTile kTile = (KineticTile) tile;
        int opSide = II_DirUtil.getOppositeSide(outputFacing);
        if (!II_TileUtil.canInput(opSide, IOType.Kinetic, tile.getHost())) {
            return;
        }
        long usage = kTile.powerUsage(tmpSet, opSide, getSpeed());
        tmpSet.clear();
        if (usage != 0) {
            double satisfaction = Math.min(((double) getMaxPower()) / usage, 1);
            useFuel(satisfaction);
            kTile.usePower(tmpSet, opSide, (int) ((double) getSpeed() * satisfaction), satisfaction);
            tmpSet.clear();
        }
        else {
            kTile.usePower(tmpSet, opSide,getSpeed(), 0);
            tmpSet.clear();
        }
    }

    public long getMaxPower() {
        return 50;
    }

    public void useFuel(double amount) {

    }

    public int getSpeed() {
        return 10;
    }

    @Override
    protected IOType getOutputIOType() {
        return IOType.Kinetic;
    }

    @Override
    public boolean getIOatSide(int side, IOType type, boolean input) {
        return type == IOType.Kinetic && side == outputFacing && !input;
    }

    @Override
    public Tile<HostMachineTile> newMetaTile(HostMachineTile baseTile) {
        return new KUGeneratorBase(baseTile, this);
    }

}
