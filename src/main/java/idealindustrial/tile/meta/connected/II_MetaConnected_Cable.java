package idealindustrial.tile.meta.connected;

import gregtech.api.enums.Materials;
import gregtech.api.enums.TextureSet;
import gregtech.api.interfaces.ITexture;
import gregtech.api.objects.GT_RenderedTexture;
import idealindustrial.tile.base.II_BaseTile;
import idealindustrial.tile.base.II_BaseTileImpl;
import idealindustrial.tile.meta.II_MetaTile;
import idealindustrial.util.energy.system.II_CableSystem;
import net.minecraft.tileentity.TileEntity;

public class II_MetaConnected_Cable extends II_BaseMetaConnected {
    public II_CableSystem system;
    protected long loss, voltage, amperage;

    public II_MetaConnected_Cable(II_BaseTile baseTile) {
        super(baseTile, "baseCable",
                new ITexture[]{new GT_RenderedTexture(Materials.Copper.mIconSet.mTextures[TextureSet.INDEX_wire], Materials.Copper.mRGBa),
                        new GT_RenderedTexture(Materials.Copper.mIconSet.mTextures[TextureSet.INDEX_wire], Materials.Copper.mRGBa)},
                new ITexture[]{null, null}
        );
    }

    @Override
    public boolean canConnect(int side) {
        TileEntity tile = baseTile.getTileEntityAtSide((byte) side);
        return tile instanceof II_BaseTile && ((II_BaseTile) tile).getMetaTile() instanceof II_MetaConnected_Cable;
    }

    private II_MetaConnected_Cable(II_BaseTile baseTile, String name, ITexture[] baseTextures, ITexture[] overlays) {
        super(baseTile, name, baseTextures, overlays);
    }

    @Override
    public II_MetaTile newMetaTile(II_BaseTile baseTile) {
        return new II_MetaConnected_Cable(baseTile, name, baseTextures, overlays);
    }

    @Override
    public void onConnectionUpdate() {
        if (system != null) {
            system.invalidate();
        }
    }

    @Override
    public void onPostTick(long timer, boolean serverSide) {
        if (system == null) {
            initDataSystem();
        }
    }

    public void initDataSystem() {
        system = new II_CableSystem();
        system.init(this);
    }

    public long getLoss() {
        return loss;
    }

    public long getVoltage() {
        return voltage;
    }

    public long getAmperage() {
        return amperage;
    }
}
