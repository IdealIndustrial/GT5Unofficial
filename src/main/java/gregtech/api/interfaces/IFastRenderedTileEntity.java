package gregtech.api.interfaces;

import net.minecraft.item.ItemStack;

public interface IFastRenderedTileEntity {

    ITexture[][] getTextures();

    ITexture[][] getTextures(ItemStack aStack, byte aFacing, boolean aActive, boolean aRedstone, boolean placeCovers, byte aFluidFacing);

    ITexture[][] getTextures(boolean tCovered);

    void rebakeMap();
}
