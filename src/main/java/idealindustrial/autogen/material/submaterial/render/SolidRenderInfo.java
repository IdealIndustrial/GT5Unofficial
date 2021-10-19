package idealindustrial.autogen.material.submaterial.render;

import java.awt.*;

public class SolidRenderInfo extends RenderInfo {

    protected TextureSet textureSet;

    public SolidRenderInfo(TextureSet textureSet) {
        super(null);
        this.textureSet = textureSet;
    }

    @Override
    public TextureSet getTextureSet() {
        return textureSet;
    }

}
