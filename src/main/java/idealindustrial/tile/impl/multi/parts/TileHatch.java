package idealindustrial.tile.impl.multi.parts;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import idealindustrial.textures.ITexture;
import idealindustrial.textures.INetworkedTexture;
import idealindustrial.tile.impl.multi.MultiMachineBase;
import idealindustrial.tile.interfaces.host.HostMachineTile;
import idealindustrial.tile.impl.TileFacing1Output;
import idealindustrial.util.misc.II_StreamUtil;

import java.util.Arrays;

public abstract class TileHatch<H extends HostMachineTile, M extends MultiMachineBase<?>> extends TileFacing1Output<H> {

    protected M multiBlock;

    public TileHatch(H hostTile, String name, ITexture[] baseTextures, ITexture[] overlays) {
        super(hostTile, name, baseTextures, overlays);
    }

    protected TileHatch(H hostTile, TileHatch<H, M> copyFrom) {
        super(hostTile, copyFrom);
        this.baseTextures = Arrays.copyOf(baseTextures, baseTextures.length);
        this.overlays = Arrays.copyOf(overlays, overlays.length);
    }

    @SuppressWarnings("unchecked")
    public void addToStructure(MultiMachineBase<?> multiBlock) {
        this.multiBlock = (M) multiBlock;
        multiBlock.addHatch(this, getType());
    }

    public abstract MultiMachineBase.HatchType getType();

    public void setTextures(INetworkedTexture[] newBaseTextures) {
        assert newBaseTextures.length == 8;
        this.baseTextures = newBaseTextures;
        hostTile.syncTileEntity();
    }

    @Override
    public void writeTile(ByteArrayDataOutput stream) {
        super.writeTile(stream);
        II_StreamUtil.writeTextureArray(baseTextures, stream);
        II_StreamUtil.writeTextureArray(overlays, stream);
    }

    @Override
    public void readTile(ByteArrayDataInput stream) {
        super.readTile(stream);
        baseTextures = II_StreamUtil.readTextureArray(stream, baseTextures);
        overlays = II_StreamUtil.readTextureArray(stream, overlays);
    }
}
