package idealindustrial.hooks;

import gloomyfolken.hooklib.asm.Hook;
import gregtech.GT_Mod;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import javax.imageio.ImageIO;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import org.lwjgl.opengl.Display;

public class II_GameTitlePatch {

    @Hook
    public static void createDisplay(ForgeHooksClient mc) {
        Display.setTitle(new String(new byte[]{-48, -104, -48, -104, -48, -95}, StandardCharsets.UTF_8)); // just "ИИС" encoded in utf-8, Im to lazy to fix gradle build encodings
        ResourceLocation icon = new ResourceLocation("gregtech", "textures/title.png");

        try {
            InputStream inputstream = GT_Mod.class.getResourceAsStream("/assets/" + icon.getResourceDomain() + "/" + icon.getResourcePath());
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
