package idealindustrial.textures;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import java.util.function.Supplier;

public class CopiedIconContainer implements IconContainer {

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
    public ResourceLocation getFile() {
        return TextureMap.locationBlocksTexture;
    }
}
