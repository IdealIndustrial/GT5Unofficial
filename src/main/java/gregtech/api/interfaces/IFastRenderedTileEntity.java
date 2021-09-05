package gregtech.api.interfaces;

import idealindustrial.render.CustomRenderer;
import net.minecraft.item.ItemStack;

public interface IFastRenderedTileEntity {

    ITexture[][] getTextures();

    ITexture[][] getTextures(ItemStack aStack, byte aFacing, boolean aActive, boolean aRedstone, boolean placeCovers);

    ITexture[][] getTextures(boolean tCovered);

    void rebakeMap();

    default CustomRenderer getCustomRenderer() { //todo : remove default
        return null;
    }
}
