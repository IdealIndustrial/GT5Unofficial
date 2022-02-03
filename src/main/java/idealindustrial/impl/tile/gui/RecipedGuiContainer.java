package idealindustrial.impl.tile.gui;

import idealindustrial.impl.recipe.GuiArrowDefinition;
import idealindustrial.impl.tile.gui.base.GenericGuiContainer;
import net.minecraft.client.Minecraft;

import static idealindustrial.impl.tile.gui.base.component.GuiTextures.PROCESSING_ARROWS;

public class RecipedGuiContainer extends GenericGuiContainer<RecipedContainer> {

    public RecipedGuiContainer(RecipedContainer container, String background) {
        super(container, background);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float tmp, int mx, int my) {
        super.drawGuiContainerBackgroundLayer(tmp, mx, my);
        int guiX = (width - xSize) / 2 - 1;
        int guiY = (height - ySize) / 2 - 1;
        GuiArrowDefinition arrow = container.params.getArrow();
        drawProgressBar(guiX + arrow.x, guiY + arrow.y, arrow);
    }

    protected void drawProgressBar(int x, int y, GuiArrowDefinition arrow) {
        Minecraft.getMinecraft().renderEngine.bindTexture(PROCESSING_ARROWS.location());
        int textureX = PROCESSING_ARROWS.idToTextureX(arrow.textureID), textureY = PROCESSING_ARROWS.idToTextureY(arrow.textureID);
        drawTexturedModalRect(x, y, textureX, textureY, 20, 17);
        drawTexturedModalRect(x, y, textureX + 20, textureY, container.progress.get(), 17);
    }


}
