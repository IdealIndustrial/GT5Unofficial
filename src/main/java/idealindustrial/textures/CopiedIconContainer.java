package idealindustrial.textures;

import gregtech.api.interfaces.IIconContainer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class CopiedIconContainer implements IIconContainer {

    protected Supplier<IIcon> supplier;
    protected IIcon icon = null;

    public CopiedIconContainer(Supplier<IIcon> supplier) {
        this.supplier = supplier;
    }

    @Override
    public IIcon getIcon() {
        if (icon == null) {
            icon = supplier.get();
        }
        return icon;
    }

    @Override
    public IIcon getOverlayIcon() {
        return null;
    }

    @Override
    public ResourceLocation getTextureFile() {
        return TextureMap.locationBlocksTexture;
    }
}
