package idealindustrial.tile.gui.base;

import idealindustrial.tile.gui.base.component.II_Slot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

import static idealindustrial.tile.gui.base.component.II_GuiTextures.SLOTS;

public class II_GenericGuiContainer<ContainerType extends II_GenericContainer> extends GuiContainer {
    protected ContainerType container;
    protected ResourceLocation background;

    public II_GenericGuiContainer(ContainerType container, String background) {
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
            int id = slot.texture;
            int textureX = SLOTS.idToTextureX(id), textureY = SLOTS.idToTextureY(id);
            drawTexturedModalRect(slot.xDisplayPosition + x - 1, slot.yDisplayPosition + y - 1, textureX, textureY, 18, 18);
        }

    }
}
