package idealindustrial.tile.covers.implementations;

import idealindustrial.autogen.material.submaterial.MatterState;
import idealindustrial.textures.ITexture;
import idealindustrial.textures.RenderedTexture;
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
