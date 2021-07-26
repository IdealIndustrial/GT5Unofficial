package idealindustrial.tile.gui.base;

import idealindustrial.tile.gui.base.component.II_Slot;
import idealindustrial.tile.gui.base.component.II_Slots;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

public class II_GenericGuiContainer extends GuiContainer {
    II_GenericContainer container;
    ResourceLocation background;

    public II_GenericGuiContainer(II_GenericContainer container, String background) {
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
        Minecraft.getMinecraft().renderEngine.bindTexture(II_Slots.LOCATION);
        for (Object o : container.inventorySlots) {
            if (!(o instanceof II_Slot)) {
                continue;
            }
            II_Slot slot = (II_Slot) o;
            int id = slot.texture;
            int textureX = II_Slots.idToX(id), textureY = II_Slots.idToY(id);
            drawTexturedModalRect(slot.xDisplayPosition + x - 1, slot.yDisplayPosition + y - 1, textureX, textureY, 18, 18);
        }

    }
}
