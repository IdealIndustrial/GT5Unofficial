package idealindustrial.autogen.material.submaterial.render;

import gregtech.api.enums.TextureSet;
import gregtech.api.interfaces.IIconContainer;

import java.awt.*;

public class FluidRenderInfo extends RenderInfo {

    IIconContainer texture;

    public FluidRenderInfo(Color color, IIconContainer texture) {
        super(color);
        this.texture = texture;
    }

    @Override
    public TextureSet getTextureSet() {
        return null;
    }

    @Override
    public IIconContainer getTexture() {
        return texture;
    }
}
