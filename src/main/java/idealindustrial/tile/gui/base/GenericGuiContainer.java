package idealindustrial.tile.gui.base;

import idealindustrial.tile.gui.base.component.II_Slot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static idealindustrial.tile.gui.base.component.GuiTextures.SLOTS;

public class GenericGuiContainer<ContainerType extends GenericContainer> extends GuiContainer {
    protected ContainerType container;
    protected ResourceLocation background;
    protected List<Gui> elements = new ArrayList<>();

    public GenericGuiContainer(ContainerType container, String background) {
        super(container);
        this.container = container;
        this.background = new ResourceLocation(background);
    }


    @Override
    protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
        super.drawGuiContainerForegroundLayer(p_146979_1_, p_146979_2_);
        fontRendererObj.drawString(container.tile.getInventoryName(), 10, 10, 4210752, false);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
        Minecraft.getMinecraft().renderEngine.bindTexture(background);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        Minecraft.getMinecraft().renderEngine.bindTexture(SLOTS.location());
        for (Object o : container.inventorySlots) {
            if (!(o instanceof II_Slot)) {
                continue;
            }
            II_Slot slot = (II_Slot) o;
            slot.draw(slot.xDisplayPosition + x - 1, slot.yDisplayPosition + y - 1, this);
        }

    }

    public void drawTexturedModalRect(int x, int y, int u, int v, int width, int height, Color color) {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA(color.getRed(), color.getGreen(), color.getBlue(), 255);
        tessellator.addVertexWithUV(x, y + height, this.zLevel, (float) (u) * f, (float) (v + height) * f1);
        tessellator.addVertexWithUV(x + width, y + height, this.zLevel, (float) (u + width) * f, (float) (v + height) * f1);
        tessellator.addVertexWithUV(x + width, y, this.zLevel, (float) (u + width) * f, (float) (v) * f1);
        tessellator.addVertexWithUV(x, y, this.zLevel, (float) (u) * f, (float) (v) * f1);
        tessellator.draw();
    }

    public float getZLevel() {
        return zLevel;
    }
}
