package idealindustrial.tile.gui;

import idealindustrial.recipe.II_GuiArrowDefinition;
import idealindustrial.tile.gui.base.II_GenericGuiContainer;
import net.minecraft.client.Minecraft;

import static idealindustrial.tile.gui.base.component.II_GuiTextures.PROCESSING_ARROWS;

public class II_RecipedGuiContainer extends II_GenericGuiContainer<II_RecipedContainer> {

    public II_RecipedGuiContainer(II_RecipedContainer container, String background) {
        super(container, background);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
        super.drawGuiContainerBackgroundLayer(p_146976_1_, p_146976_2_, p_146976_3_);
        int guiX = (width - xSize) / 2 - 1;
        int guiY = (height - ySize) / 2 - 1;
        II_GuiArrowDefinition arrow = container.params.getArrow();
        drawProgressBar(guiX + arrow.x, guiY + arrow.y, arrow);
    }

    protected void drawProgressBar(int x, int y, II_GuiArrowDefinition arrow) {
        Minecraft.getMinecraft().renderEngine.bindTexture(PROCESSING_ARROWS.location());
        int textureX = PROCESSING_ARROWS.idToTextureX(arrow.textureID), textureY = PROCESSING_ARROWS.idToTextureY(arrow.textureID);
        drawTexturedModalRect(x, y, textureX, textureY, 20, 17);
        drawTexturedModalRect(x, y, textureX + 20, textureY, container.progress.get(), 17);
    }


}
