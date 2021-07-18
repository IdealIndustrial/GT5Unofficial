package idealindustrial.teststuff;

import gregtech.api.interfaces.IIconContainer;
import gregtech.api.interfaces.ITexture;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.common.render.GT_Renderer_Block;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import opisapache.math3.analysis.function.Min;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.lang.reflect.Field;
import java.util.List;

public class RenderTest extends TileEntitySpecialRenderer {


    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float a) {


        GL11.glPushMatrix();
        //region: push1
        GL11.glTranslated(x, y, z);

        GL11.glPushMatrix();
        {

            long time = System.currentTimeMillis();
            int p = (int) (time % 1000_000);
            double rotate = p / Math.PI;

            GL11.glTranslated(0, .5, .5);
            GL11.glRotated(rotate, 1, 0, 0);
            GL11.glTranslated(0, -.5, -.5);
            GL11.glPushMatrix();
            GL11.glDisable(GL11.GL_LIGHTING);
            if (Keyboard.isKeyDown(Keyboard.KEY_R))
                GL11.glDisable(GL11.GL_DEPTH_TEST);

            Tessellator.instance.startDrawingQuads();
            IIconContainer icon = ((TestTile) tileEntity).getTexture();
            Minecraft.getMinecraft().renderEngine.bindTexture(icon.getTextureFile());
            GT_RenderedTexture texture = new GT_RenderedTexture(icon);
            GT_Renderer_Block r = GT_Renderer_Block.INSTANCE;
            World aWorld = null;
            RenderBlocks aRenderer = RenderBlocks.getInstance();
            aRenderer.setRenderBounds(0, 0, 0, 1, 1, 1);
            Block aBlock = Blocks.iron_block;
            {
                World world = tileEntity.getWorldObj();
                int aX = tileEntity.xCoord, aY = tileEntity.yCoord, aZ = tileEntity.zCoord;
                int b = world.getBlock(aX, aY, aZ).getMixedBrightnessForBlock(world, aX, aY - 1, aZ);
                Tessellator.instance.setBrightness(b);
            }

            int aX = 0, aY = 0, aZ = 0;
            ITexture[] aTextures = new ITexture[]{texture};
            r.renderNegativeYFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, aTextures, true);
            r.renderPositiveYFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, aTextures, true);
            r.renderNegativeZFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, aTextures, true);
            r.renderPositiveZFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, aTextures, true);
            r.renderNegativeXFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, aTextures, true);
            r.renderPositiveXFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, aTextures, true);
            Tessellator.instance.draw();

            GL11.glPopMatrix();

        }
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        {



            World world = tileEntity.getWorldObj();
            int tx = tileEntity.xCoord, ty = tileEntity.yCoord, tz = tileEntity.zCoord;
            Chunk chunk = world.getChunkFromChunkCoords(tx >> 4, tz >> 4);
            int cx = (tx >> 4) * 16, cz = (tz >> 4) * 16;
            RenderBlocks renderBlocks = new RenderBlocks(world);
            double coef = 9;
            int coefI = (int)(coef + 0.00001);
            int minOff = ((int)coef) / 2;
            double div = 1 / coef;

            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            MovingObjectPosition pos = player.rayTrace(20d, 1f);

            GL11.glPushMatrix();
            if (pos.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                GL11.glTranslated(pos.blockX - tx, pos.blockY - ty, pos.blockZ - tz);
            }

                GL11.glTranslated(minOff / coef, 2.5, minOff / coef);


            GL11.glScaled(div, div, div);
            GL11.glTranslated(-tx , -ty  , -tz );






            Tessellator.instance.startDrawingQuads();

            for (int xi = tx - minOff; xi < tx + coefI - minOff; xi++) {
                for (int zi = tz - minOff; zi < tz + coefI - minOff; zi++) {
                    for (int yi = ty - coefI - 1; yi < ty - 1; yi++) {
                        Block block = world.getBlock(xi, yi, zi);
                        if (block.getMaterial() == Material.air) {
                            continue;
                        }
                        renderBlocks.renderBlockByRenderType(block, xi, yi, zi);
                    }
                }
            }


            Tessellator.instance.draw();
            GL11.glPopMatrix();

        }
        GL11.glPopMatrix();
//endregion
        GL11.glPopMatrix();
    }
}
