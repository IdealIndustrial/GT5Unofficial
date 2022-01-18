package idealindustrial.impl.tile.gui.scheme;

import idealindustrial.impl.textures.GuiTexture;
import idealindustrial.impl.world.util.Vector2;
import idealindustrial.impl.world.util.Vector2d;
import idealindustrial.impl.world.util.Vector3;
import idealindustrial.util.misc.II_Paths;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiNetScheme implements GuiWidget {
   Vector2d offset;
   GuiTexture background = new GuiTexture(II_Paths.PATH_GUI + "testSchema.png", 0, 0, 256, 256);


    public GuiNetScheme() {

    }

    @Override
    public void drawBackground(float tmp, int mx, int my) {
        GL11.glDepthFunc(GL11.GL_GEQUAL);
        drawFrame();
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        for (DrawPhase dp : DrawPhase.values()) {
            drawNodesRecursively(dp);
        }
    }

    private void drawNodesRecursively(DrawPhase dp) {

    }

    private void drawFrame() {
        background.draw(0, 0, 0);
    }



}
