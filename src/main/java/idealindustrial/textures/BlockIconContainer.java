package idealindustrial.textures;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;

class BlockIconContainer extends BaseIconContainer {


    public BlockIconContainer(String name) {
        super(name);
    }

    @Override
    public ResourceLocation getFile() {
        return TextureMap.locationBlocksTexture;
    }
}
