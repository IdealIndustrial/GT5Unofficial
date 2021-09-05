package idealindustrial.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.interfaces.ITexture;
import idealindustrial.tile.meta.multi.struct.IGuideRenderer;
import idealindustrial.util.worldgen.Vector3;
import net.minecraft.block.Block;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class II_CubeRenderedParticle extends EntityFX {
    private final IIcon[][] icons = new IIcon[6][];
    private boolean texturesAreSet = false;

    public II_CubeRenderedParticle(World world, int x, int y, int z, IGuideRenderer renderer) {
        super(world, x + .25, y + .5, z + .25);
        particleGravity = 0;
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        noClip = true;

        if (renderer != null) {
            renderer.registerNewParticle(this);
            particleMaxAge = renderer.getMaxAge();
        } else {
            particleMaxAge = 40;
        }
    }

    public II_CubeRenderedParticle(World world, Vector3 position, IGuideRenderer renderer) {
        this(world, position.x, position.y, position.z, renderer);
    }

    //now idk how to do it better =(
    public II_CubeRenderedParticle setTextures(Block block, int meta) {
        for (int i = 0; i < 6; i++) {
            icons[i] = new IIcon[]{block.getIcon(i, meta)};
        }
        texturesAreSet = true;
        return this;
    }

    public II_CubeRenderedParticle setTextures(IIcon[] icons) {
        for (int i = 0; i < 6; i++) {
            this.icons[i] = new IIcon[]{icons[i]};
        }
        texturesAreSet = true;
        return this;
    }

    public II_CubeRenderedParticle setTextures(ITexture[][] textures) {
        for (int i = 0; i < 6; i++) {
            icons[i] = new IIcon[textures[i].length];
            for (int j = 0; j < textures[i].length; j++) {
                icons[i][j] = textures[i][j].getContainer().getIcon();
            }
        }
        texturesAreSet = true;
        return this;
    }

    @Override
    public void renderParticle(Tessellator tes, float subTickTime, float p_70539_3_, float p_70539_4_, float p_70539_5_, float p_70539_6_, float p_70539_7_) {
        assert texturesAreSet : "Invalid particle, textures are not set before rendering";
        float size = .5f;
        float X = (float) (prevPosX + (posX - prevPosX) * (double) subTickTime - EntityFX.interpPosX);
        float Y = (float) (prevPosY + (posY - prevPosY) * (double) subTickTime - EntityFX.interpPosY) - size / 2;
        float Z = (float) (prevPosZ + (posZ - prevPosZ) * (double) subTickTime - EntityFX.interpPosZ);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDepthMask(false);
        tes.setColorRGBA_F(.9F, .95F, 1F, .75f);

        //var8, var9 - X U
        //var 10, var 11 - Y V
        for (int i = 0; i < 6; i++) {
            if (icons[i] == null) {
                continue;
            }
            for (int textureI = 0; textureI < icons[i].length; textureI++) {
                IIcon toRender = icons[i][textureI];
                double u = toRender.getMinU();
                double U = toRender.getMaxU();
                double v = toRender.getMinV();
                double V = toRender.getMaxV();
                switch (i) {//{DOWN, UP, NORTH, SOUTH, WEST, EAST}
                    case 0:
                        tes.addVertexWithUV(X, Y, Z + size, u, V);
                        tes.addVertexWithUV(X, Y, Z, u, v);
                        tes.addVertexWithUV(X + size, Y, Z, U, v);
                        tes.addVertexWithUV(X + size, Y, Z + size, U, V);
                        break;
                    case 1:
                        tes.addVertexWithUV(X, Y + size, Z, u, v);
                        tes.addVertexWithUV(X, Y + size, Z + size, u, V);
                        tes.addVertexWithUV(X + size, Y + size, Z + size, U, V);
                        tes.addVertexWithUV(X + size, Y + size, Z, U, v);
                        break;
                    case 2:
                        tes.addVertexWithUV(X, Y, Z, U, V);
                        tes.addVertexWithUV(X, Y + size, Z, U, v);
                        tes.addVertexWithUV(X + size, Y + size, Z, u, v);
                        tes.addVertexWithUV(X + size, Y, Z, u, V);
                        break;
                    case 3:
                        tes.addVertexWithUV(X + size, Y, Z + size, U, V);
                        tes.addVertexWithUV(X + size, Y + size, Z + size, U, v);
                        tes.addVertexWithUV(X, Y + size, Z + size, u, v);
                        tes.addVertexWithUV(X, Y, Z + size, u, V);
                        break;
                    case 4:
                        tes.addVertexWithUV(X, Y, Z + size, U, V);
                        tes.addVertexWithUV(X, Y + size, Z + size, U, v);
                        tes.addVertexWithUV(X, Y + size, Z, u, v);
                        tes.addVertexWithUV(X, Y, Z, u, V);
                        break;
                    case 5:
                        tes.addVertexWithUV(X + size, Y, Z, U, V);
                        tes.addVertexWithUV(X + size, Y + size, Z, U, v);
                        tes.addVertexWithUV(X + size, Y + size, Z + size, u, v);
                        tes.addVertexWithUV(X + size, Y, Z + size, u, V);
                        break;
                }
            }
        }
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glDepthMask(true);
    }

    @Override
    public int getFXLayer() {
        return 1;
    }

    @Override
    public boolean shouldRenderInPass(int pass) {
        return pass == 2;
    }
}