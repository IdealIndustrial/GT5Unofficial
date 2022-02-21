package idealindustrial.api.tile.render;

import idealindustrial.api.textures.ITexture;
import net.minecraft.item.ItemStack;

public interface IFastRenderedTileEntity {

    ITexture[][] getTextures();

    ITexture[][] getTextures(ItemStack aStack, byte aFacing, boolean aActive, boolean aRedstone, boolean placeCovers);

    ITexture[][] getTextures(boolean tCovered);

    void rebakeMap();

    CustomRenderer getCustomRenderer();
}
