package idealindustrial.textures;

import idealindustrial.autogen.material.II_Material;
import idealindustrial.autogen.material.Prefixes;
import idealindustrial.autogen.material.submaterial.MatterState;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;

public class RenderedTexture implements ITexture {
    private IconContainer icon;
    private boolean maxBrightness;
    private int[] rgba;

    public RenderedTexture(IconContainer icon, int[] rgba) {
        assert icon != null && rgba != null && rgba.length == 4;
        this.icon = icon;
        this.rgba = rgba;
    }

    public RenderedTexture(IconContainer icon) {
        this(icon, new int[]{255, 255, 255, 0});
    }

    public RenderedTexture(II_Material material, Prefixes prefix) {
        this(material.getRenderInfo().getTextureSet().forPrefix(prefix), material.getRenderInfo().getColorAsArray(MatterState.Solid));
    }

    public RenderedTexture maxBrightness() {
        maxBrightness = true;
        return this;
    }

    @Override
    public void renderXPos(RenderBlocks aRenderer, Block aBlock, int aX, int aY, int aZ) {
        if (maxBrightness) {
            Tessellator.instance.setColorRGBA(rgba[0], rgba[1], rgba[2], 255 - rgba[3]);
        } else {
            Tessellator.instance.setColorRGBA((int) (rgba[0] * 0.6F), (int) (rgba[1] * 0.6F), (int) (rgba[2] * 0.6F), 255 - rgba[3]);
        }
        aRenderer.renderFaceXPos(aBlock, aX, aY, aZ, icon.getIcon());
    }

    @Override
    public void renderXNeg(RenderBlocks aRenderer, Block aBlock, int aX, int aY, int aZ) {
        if (maxBrightness) {
            Tessellator.instance.setColorRGBA(rgba[0], rgba[1], rgba[2], 255 - rgba[3]);
        } else {
            Tessellator.instance.setColorRGBA((int) (rgba[0] * 0.6F), (int) (rgba[1] * 0.6F), (int) (rgba[2] * 0.6F), 255 - rgba[3]);
        }
        aRenderer.renderFaceXNeg(aBlock, aX, aY, aZ, icon.getIcon());
    }

    @Override
    public void renderYPos(RenderBlocks aRenderer, Block aBlock, int aX, int aY, int aZ) {
        if (maxBrightness) {
            Tessellator.instance.setColorRGBA(rgba[0], rgba[1], rgba[2], 255 - rgba[3]);
        } else {
            Tessellator.instance.setColorRGBA((int) (rgba[0] * 1.0F), (int) (rgba[1] * 1.0F), (int) (rgba[2] * 1.0F), 255 - rgba[3]);
        }
        aRenderer.renderFaceYPos(aBlock, aX, aY, aZ, icon.getIcon());
    }

    @Override
    public void renderYNeg(RenderBlocks aRenderer, Block aBlock, int aX, int aY, int aZ) {
        if (maxBrightness) {
            Tessellator.instance.setColorRGBA(rgba[0], rgba[1], rgba[2], true ? 255 - rgba[3] : 255);
        } else {
            Tessellator.instance.setColorRGBA((int) (rgba[0] * 0.5F), (int) (rgba[1] * 0.5F), (int) (rgba[2] * 0.5F), true ? 255 - rgba[3] : 255);
        }
        IIcon aIcon = icon.getIcon();

        float d_16 = 16.0F;
        float d3 = (float) aIcon.getInterpolatedU(aRenderer.renderMaxX * d_16);
        float d4 = (float) aIcon.getInterpolatedU(aRenderer.renderMinX * d_16);
        float d5 = (float) aIcon.getInterpolatedV(aRenderer.renderMinZ * d_16);
        float d6 = (float) aIcon.getInterpolatedV(aRenderer.renderMaxZ * d_16);

        if (aRenderer.renderMinX < 0.0D || aRenderer.renderMaxX > 1.0D) {
            d3 = aIcon.getMaxU();
            d4 = aIcon.getMinU();
        }

        if (aRenderer.renderMinZ < 0.0D || aRenderer.renderMaxZ > 1.0D) {
            d5 = aIcon.getMinV();
            d6 = aIcon.getMaxV();
        }

        float d11 = aX + (float) aRenderer.renderMinX;
        float d12 = aX + (float) aRenderer.renderMaxX;
        float d13 = aY + (float) aRenderer.renderMinY;
        float d14 = aZ + (float) aRenderer.renderMinZ;
        float d15 = aZ + (float) aRenderer.renderMaxZ;

        Tessellator.instance.addVertexWithUV((double) d11, (double) d13, (double) d15, (double) d3, (double) d6);
        Tessellator.instance.addVertexWithUV((double) d11, (double) d13, (double) d14, (double) d3, (double) d5);
        Tessellator.instance.addVertexWithUV((double) d12, (double) d13, (double) d14, (double) d4, (double) d5);
        Tessellator.instance.addVertexWithUV((double) d12, (double) d13, (double) d15, (double) d4, (double) d6);
    }

    @Override
    public void renderZPos(RenderBlocks aRenderer, Block aBlock, int aX, int aY, int aZ) {
        if (maxBrightness) {
            Tessellator.instance.setColorRGBA(rgba[0], rgba[1], rgba[2], 255 - rgba[3]);
        } else {
            Tessellator.instance.setColorRGBA((int) (rgba[0] * 0.8F), (int) (rgba[1] * 0.8F), (int) (rgba[2] * 0.8F), 255 - rgba[3]);
        }
        aRenderer.renderFaceZPos(aBlock, aX, aY, aZ, icon.getIcon());
    }

    @Override
    public void renderZNeg(RenderBlocks aRenderer, Block aBlock, int aX, int aY, int aZ) {
        if (maxBrightness) {
            Tessellator.instance.setColorRGBA(rgba[0], rgba[1], rgba[2], 255 - rgba[3]);
        } else {
            Tessellator.instance.setColorRGBA((int) (rgba[0] * 0.8F), (int) (rgba[1] * 0.8F), (int) (rgba[2] * 0.8F), 255 - rgba[3]);
        }
        aRenderer.renderFaceZNeg(aBlock, aX, aY, aZ, icon.getIcon());
    }

    @Override
    public boolean isValidTexture() {
        return icon != null;
    }

    @Override
    public IIcon getIcon() {
        return icon.getIcon();
    }
}