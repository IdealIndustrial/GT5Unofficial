package idealindustrial.impl.textures;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

public class GuiTexture {
    ResourceLocation location;
    int xPos, yPos;
    int width, height;

    public GuiTexture(String path, int xPos, int yPos, int width, int height) {
        this.location = new ResourceLocation(path);
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
    }

    public void draw(int x, int y, int z) {
        Minecraft.getMinecraft().renderEngine.bindTexture(location);
        draw(x, y, xPos, yPos, z, width, height);
    }

    public void draw(int x, int y, int u, int v, int z, int width, int height) {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x, y + height, (double) z, (float) (u) * f, (float) (v + height) * f1);
        tessellator.addVertexWithUV(x + width, y + height, (double) z, (double) ((float) (u + width) * f), (double) ((float) (v + height) * f1));
        tessellator.addVertexWithUV((double) (x + width), (double) (y + 0), (double) z, (double) ((float) (u + width) * f), (double) ((float) (v + 0) * f1));
        tessellator.addVertexWithUV((double) (x + 0), (double) (y + 0), (double) z, (double) ((float) (u + 0) * f), (double) ((float) (v + 0) * f1));
        tessellator.draw();
    }
}
