package idealindustrial.textures;

import gregtech.api.interfaces.ITexture;
import gregtech.api.objects.GT_RenderedTexture;
import idealindustrial.II_Values;

public class Textures {
    public static final ITexture[] baseTiredTextures = new ITexture[II_Values.maxTier + 1];
    public static ITexture output = new GT_RenderedTexture(gregtech.api.enums.Textures.BlockIcons.OVERLAY_PIPE_OUT);
    public static ITexture input = new GT_RenderedTexture(gregtech.api.enums.Textures.BlockIcons.OVERLAY_PIPE_IN);
    static void init() {
        for (int tier = 0; tier < baseTiredTextures.length; tier++) {
            baseTiredTextures[tier] = new GT_RenderedTexture(TextureManager.INSTANCE.blockTexture("tired/base/" + tier));
        }
    }
}
