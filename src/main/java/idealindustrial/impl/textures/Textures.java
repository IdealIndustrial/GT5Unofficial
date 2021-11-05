package idealindustrial.impl.textures;

import idealindustrial.II_Values;
import idealindustrial.api.textures.ITexture;
import idealindustrial.impl.autogen.material.submaterial.render.TextureSet;

public class Textures {
    public static final ITexture[] baseTiredTextures = new ITexture[II_Values.maxTier + 1];
    public static ITexture output = new RenderedTexture(TextureManager.INSTANCE.blockTexture("test/in"));
    public static ITexture input = new RenderedTexture(TextureManager.INSTANCE.blockTexture("test/out"));
    public static ITexture input_energy = input;
    public static ITexture output_energy = output;
    static void init() {
        for (int tier = 0; tier < baseTiredTextures.length; tier++) {
            baseTiredTextures[tier] = new RenderedTexture(TextureManager.INSTANCE.blockTexture("tired/base/" + tier));
        }
    }

    public static final TextureSet testSet = new TextureSet("test");
}
