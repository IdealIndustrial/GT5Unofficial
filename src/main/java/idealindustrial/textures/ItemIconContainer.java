package idealindustrial.textures;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;

class ItemIconContainer extends BaseIconContainer {

    public ItemIconContainer(String name) {
        super(name);
    }

    @Override
    public ResourceLocation getFile() {
        return TextureMap.locationItemsTexture;
    }
}
