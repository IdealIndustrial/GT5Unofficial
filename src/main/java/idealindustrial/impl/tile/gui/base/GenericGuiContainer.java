package idealindustrial.impl.tile.gui.base;

import idealindustrial.impl.tile.gui.GuiRect;
import idealindustrial.impl.tile.gui.base.component.II_Slot;
import idealindustrial.impl.tile.gui.scheme.GuiWidget;
import idealindustrial.impl.world.util.Vector2;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static idealindustrial.impl.tile.gui.base.component.GuiTextures.SLOTS;

public class GenericGuiContainer<ContainerType extends GenericContainer> extends GuiContainer implements GuiRect {
    protected ContainerType container;
    protected ResourceLocation background;
    protected List<GuiWidget> widgets = new ArrayList<>();

    public GenericGuiContainer(ContainerType container, String background) {
        super(container);
        this.container = container;
        this.background = new ResourceLocation(background);
    }

    protected void addWidget(GuiWidget widget) {
        widget.setRect(this);
        widgets.add(widget);
    }


    @Override
    protected void drawGuiContainerForegroundLayer(int mx, int my) {
        super.drawGuiContainerForegroundLayer(mx, my);
        fontRendererObj.drawString(container.tile.getInventoryName(), 10, 10, 4210752, false);//todo: move to widget
        widgets.forEach(w -> w.drawGuiContainerForegroundLayer(mx, my));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float tmp, int mx, int my) {
        Minecraft.getMinecraft().renderEngine.bindTexture(background);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);//todo: move to widget
        Minecraft.getMinecraft().renderEngine.bindTexture(SLOTS.location());
        for (Object o : container.inventorySlots) {
            if (!(o instanceof II_Slot)) {
                continue;
            }
            II_Slot slot = (II_Slot) o;
            slot.draw(slot.xDisplayPosition + x - 1, slot.yDisplayPosition + y - 1, this);
        }

        widgets.forEach(w -> w.drawBackground(tmp, mx, my));

    }

    @Override
    protected void mouseClicked(int mx, int my, int buttons) {
        if (widgets.stream().anyMatch(w -> w.mouseClicked(mx, my, buttons))) {
            return;
        }
        super.mouseClicked(mx, my, buttons);
    }

    @Override
    protected void mouseClickMove(int mx, int my, int button, long timeSince) {
        if (widgets.stream().anyMatch(w -> w.mouseClickMove(mx, my, button, timeSince))) {
            return;
        }
        super.mouseClickMove(mx, my, button, timeSince);
    }

    @Override
    protected void mouseMovedOrUp(int mx, int my, int button) {
        if (widgets.stream().anyMatch(w -> w.mouseMovedOrUp(mx, my, button))) {
            return;
        }
        super.mouseMovedOrUp(mx, my, button);
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

    @Override
    public Vector2 screenSize() {
        return new Vector2(width, height);
    }

    @Override
    public Vector2 guiSize() {
        return new Vector2(xSize, ySize);
    }

    @Override
    public Vector2 guiOffset() {
        return new Vector2(guiLeft, guiTop);
    }
}
