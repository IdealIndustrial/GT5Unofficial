package idealindustrial.autogen.material.submaterial.render;

import gregtech.api.enums.TextureSet;
import gregtech.api.interfaces.IIconContainer;

import java.awt.*;

public class SolidRenderInfo extends RenderInfo {

    protected TextureSet textureSet;

    public SolidRenderInfo(Color color, TextureSet textureSet) {
        super(color);
        this.textureSet = textureSet;
    }

    @Override
    public TextureSet getTextureSet() {
        return textureSet;
    }

    @Override
    public IIconContainer getTexture() {
        return null;
    }
}
