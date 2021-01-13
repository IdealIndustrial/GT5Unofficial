package idealindustrial.hooks;

import gloomyfolken.hooklib.asm.Hook;
import jdk.nashorn.internal.runtime.regexp.joni.Config;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import org.lwjgl.opengl.Display;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class II_GameTitlePatch {

    @Hook
    public static void createDisplay(ForgeHooksClient mc) {
        Display.setTitle("ИИС");
        ResourceLocation icon = new ResourceLocation("gregtech", "textures/title.png");

        try {
            InputStream inputstream = Config.class.getResourceAsStream("/assets/" + icon.getResourceDomain() + "/" + icon.getResourcePath());
            Display.setIcon(new ByteBuffer[]{call(inputstream)});
        } catch (IOException ignore) {

        }
    }

    private static ByteBuffer call(InputStream is) throws IOException {
        BufferedImage bufferedimage = ImageIO.read(is);
        int[] rgb = bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), null, 0, bufferedimage.getWidth());
        ByteBuffer bytebuffer = ByteBuffer.allocate(4 * rgb.length);
        for (int k : rgb) {
            bytebuffer.putInt(k << 8 | k >> 24 & 255);
        }
        bytebuffer.flip();
        return bytebuffer;
    }
}
