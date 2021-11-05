package idealindustrial.impl.textures;

import idealindustrial.api.textures.IconContainer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

public class IconContainerImpl implements IconContainer {

    IIcon icon;
    ResourceLocation location;

    public IconContainerImpl(IIcon icon, ResourceLocation location) {
        assert icon != null && location != null;
        this.icon = icon;
        this.location = location;
    }

    public IconContainerImpl(IIcon icon) {
       this(icon, TextureMap.locationBlocksTexture);
    }

    @Override
    public IIcon getIcon() {
        return icon;
    }

    @Override
    public ResourceLocation getFile() {
        return location;
    }
}
