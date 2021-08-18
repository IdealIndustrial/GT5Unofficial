package idealindustrial.textures;

import gregtech.api.interfaces.ITexture;
import gregtech.api.objects.GT_RenderedTexture;
import idealindustrial.II_Values;

public class II_Textures {
    public static final ITexture[] baseTiredTextures = new ITexture[II_Values.maxTier + 1];
    static void init() {
        for (int tier = 0; tier < baseTiredTextures.length; tier++) {
            baseTiredTextures[tier] = new GT_RenderedTexture(II_TextureManager.INSTANCE.blockTexture("tired/base/" + tier));
        }
    }
}
