package idealindustrial.impl.tile.gui.base.component;

import idealindustrial.impl.tile.gui.base.GenericGuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

import java.awt.*;

import static idealindustrial.impl.tile.gui.base.component.GuiTextures.SLOTS;

public class II_Slot extends Slot {

    public final int texture;

    public II_Slot(IInventory inventory, int id, int x, int y, int texture) {
        super(inventory, id, x, y);
        this.texture = texture;
    }

    public void draw(int x, int y, GenericGuiContainer<?> container) {
        int id = texture;
        int textureX = SLOTS.idToTextureX(id), textureY = SLOTS.idToTextureY(id);
        drawTexturedModalRect(x, y, container.getZLevel(), textureX, textureY, 18, 18, Color.WHITE);
    }

    public void drawTexturedModalRect(int x, int y, float zLevel, int u, int v, int width, int height, Color color)
    {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA(color.getRed(), color.getGreen(), color.getBlue(), 255);
        tessellator.addVertexWithUV(x, y + height, zLevel, (float)(u) * f, (float)(v + height) * f1);
        tessellator.addVertexWithUV(x + width, y + height, zLevel, (float)(u + width) * f, (float)(v + height) * f1);
        tessellator.addVertexWithUV(x + width, y, zLevel, (float)(u + width) * f, (float)(v) * f1);
        tessellator.addVertexWithUV(x, y, zLevel, (float)(u) * f, (float)(v) * f1);
        tessellator.draw();
    }
}
