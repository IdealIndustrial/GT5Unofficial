package idealindustrial.impl.tile.gui.scheme;

import idealindustrial.impl.textures.GuiTexture;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static idealindustrial.util.misc.II_Paths.PATH_GUI;

public class GuiNetScheme extends WidgetBase {

    GuiTexture background = new GuiTexture(PATH_GUI + "testSchema.png", 0, 0, 176, 166);
    GuiTexture slot = new GuiTexture(PATH_GUI + "testSchema.png", 176, 0, 16, 16);
    List<SchemeNode> independentNodes = new ArrayList<>();

    public GuiNetScheme() {

    }

    @Override
    public void drawBackground(float tmp, int mx, int my) {
        GL11.glDepthFunc(GL11.GL_GEQUAL);
        drawFrame();
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        Arrays.stream(DrawPhase.values()).forEach(this::drawNodesRecursively);
    }

    private void drawNodesRecursively(DrawPhase dp) {
        independentNodes.forEach(n -> n.draw(dp));
    }

    private void drawFrame() {
        background.draw(getRect().guiOffset(), 0);
    }


}
