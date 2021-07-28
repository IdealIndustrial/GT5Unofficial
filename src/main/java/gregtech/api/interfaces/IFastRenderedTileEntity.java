package gregtech.api.interfaces;

import idealindustrial.render.II_CustomRenderer;
import net.minecraft.item.ItemStack;

public interface IFastRenderedTileEntity {

    ITexture[][] getTextures();

    ITexture[][] getTextures(ItemStack aStack, byte aFacing, boolean aActive, boolean aRedstone, boolean placeCovers);

    ITexture[][] getTextures(boolean tCovered);

    void rebakeMap();

    default II_CustomRenderer getCustomRenderer() { //todo : remove default
        return null;
    }
}
