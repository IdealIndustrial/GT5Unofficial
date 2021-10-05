package idealindustrial.tile.meta.multi.parts;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import gregtech.api.interfaces.ITexture;
import idealindustrial.textures.INetworkedTexture;
import idealindustrial.tile.interfaces.base.BaseMachineTile;
import idealindustrial.tile.interfaces.meta.MetaTile;
import idealindustrial.tile.meta.BaseMetaTile_Facing1Output;
import idealindustrial.tile.meta.BaseMetaTile_Facing2Main;
import idealindustrial.tile.meta.multi.BaseMultiMachine;
import idealindustrial.util.misc.II_StreamUtil;

import java.util.Arrays;

public abstract class BaseMetaTile_Hatch<B extends BaseMachineTile, M extends BaseMultiMachine<?>> extends BaseMetaTile_Facing1Output<B> {

    protected M multiBlock;

    public BaseMetaTile_Hatch(B baseTile, String name, ITexture[] baseTextures, ITexture[] overlays) {
        super(baseTile, name, baseTextures, overlays);
    }

    protected BaseMetaTile_Hatch(B baseTile, BaseMetaTile_Hatch<B, M> copyFrom) {
        super(baseTile, copyFrom);
        this.baseTextures = Arrays.copyOf(baseTextures, baseTextures.length);
        this.overlays = Arrays.copyOf(overlays, overlays.length);
    }

    @SuppressWarnings("unchecked")
    public void addToStructure(BaseMultiMachine<?> multiBlock) {
        this.multiBlock = (M) multiBlock;
        multiBlock.addHatch(this, getType());
    }

    public abstract BaseMultiMachine.HatchType getType();

    public void setTextures(INetworkedTexture[] newBaseTextures) {
        assert newBaseTextures.length == 8;
        this.baseTextures = newBaseTextures;
        baseTile.syncTileEntity();
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
