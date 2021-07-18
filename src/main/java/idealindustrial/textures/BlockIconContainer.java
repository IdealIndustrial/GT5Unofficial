package idealindustrial.textures;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;

class BlockIconContainer extends BaseIconContainer {


    public BlockIconContainer(String name, boolean hasOverlay) {
        super(name, hasOverlay);
    }

    @Override
    public ResourceLocation getTextureFile() {
        return TextureMap.locationBlocksTexture;
    }
}
