package idealindustrial.impl.tile.covers.implementations;

import idealindustrial.impl.autogen.material.submaterial.MatterState;
import idealindustrial.api.textures.ITexture;
import idealindustrial.impl.textures.RenderedTexture;
import idealindustrial.impl.autogen.material.II_Material;
import idealindustrial.impl.tile.IOType;
import idealindustrial.api.tile.BaseCoverBehavior;
import idealindustrial.api.tile.host.HostTile;

public class CoverPlate implements BaseCoverBehavior<HostTile> {

    protected II_Material material;
    protected ITexture texture;

    public CoverPlate(II_Material material) {
        this.material = material;
        this.texture = new RenderedTexture(material.getRenderInfo().getTextureSet().plate, material.getRenderInfo().getColorAsArray(MatterState.Solid));
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
