package idealindustrial.tile.covers.implementations;

import gregtech.api.interfaces.ITexture;
import gregtech.api.objects.GT_RenderedTexture;
import idealindustrial.autogen.material.II_Material;
import idealindustrial.autogen.material.Prefixes;
import idealindustrial.tile.IOType;
import idealindustrial.tile.covers.BaseCoverBehavior;
import idealindustrial.tile.interfaces.host.HostTile;

public class CoverPlate implements BaseCoverBehavior<HostTile> {

    protected II_Material material;
    protected ITexture texture;

    public CoverPlate(II_Material material) {
        this.material = material;
        this.texture = new GT_RenderedTexture(material.getSolidRenderInfo().getTextureSet().mTextures[Prefixes.block.textureIndex], material.getSolidRenderInfo().getColorAsArray());
    }

    @Override
    public ITexture getTexture(long var, int side, HostTile tile) {
        return texture;
    }

    @Override
    public boolean getIO(IOType type, boolean input) {
        return false;
    }
}
