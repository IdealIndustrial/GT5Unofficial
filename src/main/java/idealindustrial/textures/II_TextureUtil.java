package idealindustrial.textures;

import gregtech.api.enums.Textures;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.interfaces.ITexture;
import gregtech.api.objects.GT_RenderedTexture;
import idealindustrial.util.misc.II_StreamUtil;

import java.util.Arrays;

public class II_TextureUtil {
    public static final TextureConfiguration facing1Configuration = new TextureConfiguration(true,"bottom", "top", "side", "output");
    public static final TextureConfiguration facing2Configuration = new TextureConfiguration(true,"bottom", "top", "side", "output", "main");

    public static class TextureConfiguration {
        public final String[] textureNames;

        public TextureConfiguration(boolean hasActive, String... textureNames) {
            if (hasActive) {
                this.textureNames = II_StreamUtil.concatArrays(textureNames, Arrays.stream(textureNames).map(s -> s + "_active").toArray(String[]::new));
            }
            else {
                this.textureNames = textureNames;
            }
        }

        public IIconContainer[] loadAll(String prefixPath) {
            IIconContainer[] loaded = new IIconContainer[textureNames.length];
            for (int i = 0; i < loaded.length; i++) {
                loaded[i] = II_TextureManager.INSTANCE.blockTexture(prefixPath + textureNames[i]);
            }
            return loaded;
        }
    }

    public static ITexture[] asGtRendered(IIconContainer... containers) {
        return asGtRendered(containers, new short[]{255, 255, 255, 0});
    }

    public static ITexture[] asGtRendered(IIconContainer[] containers, short[] rgba) {
        return Arrays.stream(containers).map(c -> new GT_RenderedTexture(c, rgba)).toArray(ITexture[]::new);
    }

    public static ITexture[] loadTextures(TextureConfiguration configuration, String path) {
        return asGtRendered(configuration.loadAll(path));
    }
}
