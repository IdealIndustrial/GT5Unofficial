package idealindustrial.tile.covers.implementations;

import gregtech.api.interfaces.ITexture;
import gregtech.api.objects.GT_RenderedTexture;
import idealindustrial.itemgen.material.II_Material;
import idealindustrial.itemgen.material.Prefixes;
import idealindustrial.tile.IOType;
import idealindustrial.tile.covers.II_BaseCoverBehavior;
import idealindustrial.tile.interfaces.base.II_BaseTile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class II_CoverPlate implements II_BaseCoverBehavior<II_BaseTile> {

    protected II_Material material;
    protected ITexture texture;

    public II_CoverPlate(II_Material material) {
        this.material = material;
        this.texture = new GT_RenderedTexture(material.getSolidRenderInfo().getTextureSet().mTextures[Prefixes.block.textureIndex], material.getSolidRenderInfo().getColorAsArray());
    }

    @Override
    public ITexture getTexture(long var, int side, II_BaseTile tile) {
        return texture;
    }

    @Override
    public boolean getIO(IOType type, boolean input) {
        return false;
    }
}
