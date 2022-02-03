package idealindustrial.impl.tile.impl.kinetic;

import gnu.trove.set.TLongSet;
import idealindustrial.api.textures.ITexture;
import idealindustrial.api.tile.energy.kinetic.KineticTile;
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

import static idealindustrial.util.misc.II_DirUtil.getOppositeSide;
import static idealindustrial.util.misc.II_TileUtil.getMetaTileAtSide;

public class TileKUSplitter extends TileFacing1Output<HostMachineTile> implements KineticTile {

    public static TileKUSplitter testMachine() {
        return new TileKUSplitter(II_TileUtil.makeBaseMachineTile(), "KU Splitter",
                II_StreamUtil.repeated("test/cob3", 8).map(TextureManager.INSTANCE::blockTexture).map(RenderedTexture::new).toArray(ITexture[]::new),
                II_StreamUtil.setInNullAr(idealindustrial.impl.textures.Textures.input, new ITexture[8], 3, 7)
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
    protected IOType getOutputIOType() {
        return IOType.Kinetic;
    }


    @Override
    public void onPostTick(long timer, boolean serverSide) {
        super.onPostTick(timer, serverSide);
    }

    @Override
    public long powerUsage(TLongSet passed, int side, int speed) {
        localPoint.setPosition(hostTile);
        if (!passed.add(localPoint.toLong())) {
            return 0;
        }
        long sum = 0;
        for (int i = 0; i < 6; i++) {
            if (i == side) {
                continue;
            }
            Tile<?> other = getMetaTileAtSide(hostTile, i);
            int opSide = getOppositeSide(i);
            if (other instanceof KineticTile && II_TileUtil.canInteract(opSide, IOType.Kinetic, other.getHost())) {
                sum += ((KineticTile) other).powerUsage(passed, opSide, speed);
            }
        }
        return sum;
    }

    @Override
    public void usePower(TLongSet passed, int side, int speed, double satisfaction) {
        localPoint.setPosition(hostTile);
        if (!passed.add(localPoint.toLong())) {
            return;
        }
        for (int i = 0; i < 6; i++) {
            if (i == side) {
                continue;
            }
            Tile<?> other = getMetaTileAtSide(hostTile, i);
            int opSide = getOppositeSide(i);
            if (other instanceof KineticTile && II_TileUtil.canInteract(opSide, IOType.Kinetic, other.getHost())) {
               ((KineticTile) other).usePower(passed, opSide, speed, satisfaction);
            }
        }
    }
}
