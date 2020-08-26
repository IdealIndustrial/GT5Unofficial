package gregtech.api.interfaces;

import net.minecraft.block.Block;

public interface IFastRenderedTileEntity {

    ITexture[][] getTextures();

    ITexture[][] getTextures(boolean tCovered);

    void rebakeMap();
}
