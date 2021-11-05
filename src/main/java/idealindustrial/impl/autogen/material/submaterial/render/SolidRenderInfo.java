package idealindustrial.impl.autogen.material.submaterial.render;

@Deprecated
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
