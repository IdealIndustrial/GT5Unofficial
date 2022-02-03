package idealindustrial.impl.render;

import idealindustrial.api.tile.render.IFastRenderedTileEntity;
import idealindustrial.impl.blocks.II_Blocks;
import idealindustrial.impl.tile.host.HostPipeTileRotatingImpl;
import idealindustrial.impl.tile.impl.connected.ConnectedRotor;
import idealindustrial.impl.tile.impl.connected.MetaConnectedRenderer;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class RotatingTileRenderer extends TileEntitySpecialRenderer {
    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float a) {

        try {
            ConnectedRotor rotor = ((ConnectedRotor) ((HostPipeTileRotatingImpl) tile).getMetaTile());
            if (rotor == null) {
                return;
            }
            GL11.glPushMatrix();
            //region: push1
            GL11.glTranslated(x, y, z);
            long time = System.currentTimeMillis();
            double p = (int) (time % 1000_000);
            p *= rotor.getRotationSpeed() / 5d;
            double rotate = p / Math.PI;

            int tx = 0, ty = 0, tz = 0;
            switch (rotor.getDirection()) {
                case 0:
                    ty = 1;
                    break;
                case 1:
                    tz = 1;
                    break;
                default:
                    tx = 1;
            }
            GL11.glTranslated(tx / -2f + 0.5, ty / -2f + 0.5, tz / -2f + 0.5);
            GL11.glRotated(rotate, tx, ty, tz);
            GL11.glTranslated(tx / 2f - 0.5, ty / 2f - 0.5, tz / 2f - 0.5);


            GL11.glPushMatrix();
            GL11.glDisable(GL11.GL_LIGHTING);
            Tessellator.instance.startDrawingQuads();
            Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
            World aWorld = null;
            RenderBlocks aRenderer = RenderBlocks.getInstance();
            aRenderer.enableAO = false;
            aRenderer.setRenderBounds(0, 0, 0, 1, 1, 1);
            Block aBlock = II_Blocks.INSTANCE.blockMachines;
            {
                World world = tile.getWorldObj();
                int aX = tile.xCoord, aY = tile.yCoord, aZ = tile.zCoord;
                int b = world.getBlock(aX, aY, aZ).getMixedBrightnessForBlock(world, aX, aY, aZ);
                Tessellator.instance.setBrightness(b);
            }
            int aX = 0, aY = 0, aZ = 0;
            MetaConnectedRenderer.INSTANCE.renderWorldBlock(aWorld, (IFastRenderedTileEntity) tile, aX, aY, aZ, aBlock, aRenderer);
            Tessellator.instance.draw();
            GL11.glPopMatrix();
            GL11.glPopMatrix();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
