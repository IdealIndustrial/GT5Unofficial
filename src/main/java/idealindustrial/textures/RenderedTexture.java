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
    public void renderXPos(RenderBlocks renderer, Block block, int x, int y, int z) {
        Tessellator tessellator = Tessellator.instance;
        IIcon tIcon = icon.getIcon();

        double d3 = tIcon.getInterpolatedU(renderer.renderMinZ * 16.0D);
        double d4 = tIcon.getInterpolatedU(renderer.renderMaxZ * 16.0D);

        if (renderer.field_152631_f)
        {
            d4 = tIcon.getInterpolatedU((1.0D - renderer.renderMinZ) * 16.0D);
            d3 = tIcon.getInterpolatedU((1.0D - renderer.renderMaxZ) * 16.0D);
        }

        double d5 = tIcon.getInterpolatedV(16.0D - renderer.renderMaxY * 16.0D);
        double d6 = tIcon.getInterpolatedV(16.0D - renderer.renderMinY * 16.0D);
        double d7;

        if (renderer.flipTexture)
        {
            d7 = d3;
            d3 = d4;
            d4 = d7;
        }

        if (renderer.renderMinZ < 0.0D || renderer.renderMaxZ > 1.0D)
        {
            d3 = tIcon.getMinU();
            d4 = tIcon.getMaxU();
        }

        if (renderer.renderMinY < 0.0D || renderer.renderMaxY > 1.0D)
        {
            d5 = tIcon.getMinV();
            d6 = tIcon.getMaxV();
        }

        d7 = d4;
        double d8 = d3;
        double d9 = d5;
        double d10 = d6;

        if (renderer.uvRotateSouth == 2)
        {
            d3 = tIcon.getInterpolatedU(renderer.renderMinY * 16.0D);
            d5 = tIcon.getInterpolatedV(16.0D - renderer.renderMinZ * 16.0D);
            d4 = tIcon.getInterpolatedU(renderer.renderMaxY * 16.0D);
            d6 = tIcon.getInterpolatedV(16.0D - renderer.renderMaxZ * 16.0D);
            d9 = d5;
            d10 = d6;
            d7 = d3;
            d8 = d4;
            d5 = d6;
            d6 = d9;
        }
        else if (renderer.uvRotateSouth == 1)
        {
            d3 = tIcon.getInterpolatedU(16.0D - renderer.renderMaxY * 16.0D);
            d5 = tIcon.getInterpolatedV(renderer.renderMaxZ * 16.0D);
            d4 = tIcon.getInterpolatedU(16.0D - renderer.renderMinY * 16.0D);
            d6 = tIcon.getInterpolatedV(renderer.renderMinZ * 16.0D);
            d7 = d4;
            d8 = d3;
            d3 = d4;
            d4 = d8;
            d9 = d6;
            d10 = d5;
        }
        else if (renderer.uvRotateSouth == 3)
        {
            d3 = tIcon.getInterpolatedU(16.0D - renderer.renderMinZ * 16.0D);
            d4 = tIcon.getInterpolatedU(16.0D - renderer.renderMaxZ * 16.0D);
            d5 = tIcon.getInterpolatedV(renderer.renderMaxY * 16.0D);
            d6 = tIcon.getInterpolatedV(renderer.renderMinY * 16.0D);
            d7 = d4;
            d8 = d3;
            d9 = d5;
            d10 = d6;
        }

        double d11 = x + renderer.renderMaxX;
        double d12 = y + renderer.renderMinY;
        double d13 = y + renderer.renderMaxY;
        double d14 = z + renderer.renderMinZ;
        double d15 = z + renderer.renderMaxZ;

        if (renderer.renderFromInside)
        {
            d14 = z + renderer.renderMaxZ;
            d15 = z + renderer.renderMinZ;
        }

        if (renderer.enableAO)
        {
            tessellator.setColorOpaque_F(renderer.colorRedTopLeft * rgba[0] / 255F, renderer.colorGreenTopLeft * rgba[1] / 255F, renderer.colorBlueTopLeft * rgba[2] / 255F);
            tessellator.setBrightness(renderer.brightnessTopLeft);
            tessellator.addVertexWithUV(d11, d12, d15, d8, d10);
            tessellator.setColorOpaque_F(renderer.colorRedBottomLeft * rgba[0] / 255F, renderer.colorGreenBottomLeft * rgba[1] / 255F, renderer.colorBlueBottomLeft * rgba[2] / 255F);
            tessellator.setBrightness(renderer.brightnessBottomLeft);
            tessellator.addVertexWithUV(d11, d12, d14, d4, d6);
            tessellator.setColorOpaque_F(renderer.colorRedBottomRight * rgba[0] / 255F, renderer.colorGreenBottomRight * rgba[1] / 255F, renderer.colorBlueBottomRight * rgba[2] / 255F);
            tessellator.setBrightness(renderer.brightnessBottomRight);
            tessellator.addVertexWithUV(d11, d13, d14, d7, d9);
            tessellator.setColorOpaque_F(renderer.colorRedTopRight * rgba[0] / 255F, renderer.colorGreenTopRight * rgba[1] / 255F, renderer.colorBlueTopRight * rgba[2] / 255F);
            tessellator.setBrightness(renderer.brightnessTopRight);
            tessellator.addVertexWithUV(d11, d13, d15, d3, d5);
        }
        else
        {
            tessellator.setColorRGBA((int)(0.6 * rgba[0]), (int)(0.6 * rgba[1]), (int) (0.6 * rgba[2]), 255);
            tessellator.addVertexWithUV(d11, d12, d15, d8, d10);
            tessellator.addVertexWithUV(d11, d12, d14, d4, d6);
            tessellator.addVertexWithUV(d11, d13, d14, d7, d9);
            tessellator.addVertexWithUV(d11, d13, d15, d3, d5);
        }
    }

    @Override
    public void renderXNeg(RenderBlocks renderer, Block block, int x, int y, int z) {
        Tessellator tessellator = Tessellator.instance;

        IIcon tIcon = icon.getIcon();
        
        double d3 = tIcon.getInterpolatedU(renderer.renderMinZ * 16.0D);
        double d4 = tIcon.getInterpolatedU(renderer.renderMaxZ * 16.0D);
        double d5 = tIcon.getInterpolatedV(16.0D - renderer.renderMaxY * 16.0D);
        double d6 = tIcon.getInterpolatedV(16.0D - renderer.renderMinY * 16.0D);
        double d7;

        if (renderer.flipTexture)
        {
            d7 = d3;
            d3 = d4;
            d4 = d7;
        }

        if (renderer.renderMinZ < 0.0D || renderer.renderMaxZ > 1.0D)
        {
            d3 = tIcon.getMinU();
            d4 = tIcon.getMaxU();
        }

        if (renderer.renderMinY < 0.0D || renderer.renderMaxY > 1.0D)
        {
            d5 = tIcon.getMinV();
            d6 = tIcon.getMaxV();
        }

        d7 = d4;
        double d8 = d3;
        double d9 = d5;
        double d10 = d6;

        if (renderer.uvRotateNorth == 1)
        {
            d3 = tIcon.getInterpolatedU(renderer.renderMinY * 16.0D);
            d5 = tIcon.getInterpolatedV(16.0D - renderer.renderMaxZ * 16.0D);
            d4 = tIcon.getInterpolatedU(renderer.renderMaxY * 16.0D);
            d6 = tIcon.getInterpolatedV(16.0D - renderer.renderMinZ * 16.0D);
            d9 = d5;
            d10 = d6;
            d7 = d3;
            d8 = d4;
            d5 = d6;
            d6 = d9;
        }
        else if (renderer.uvRotateNorth == 2)
        {
            d3 = tIcon.getInterpolatedU(16.0D - renderer.renderMaxY * 16.0D);
            d5 = tIcon.getInterpolatedV(renderer.renderMinZ * 16.0D);
            d4 = tIcon.getInterpolatedU(16.0D - renderer.renderMinY * 16.0D);
            d6 = tIcon.getInterpolatedV(renderer.renderMaxZ * 16.0D);
            d7 = d4;
            d8 = d3;
            d3 = d4;
            d4 = d8;
            d9 = d6;
            d10 = d5;
        }
        else if (renderer.uvRotateNorth == 3)
        {
            d3 = tIcon.getInterpolatedU(16.0D - renderer.renderMinZ * 16.0D);
            d4 = tIcon.getInterpolatedU(16.0D - renderer.renderMaxZ * 16.0D);
            d5 = tIcon.getInterpolatedV(renderer.renderMaxY * 16.0D);
            d6 = tIcon.getInterpolatedV(renderer.renderMinY * 16.0D);
            d7 = d4;
            d8 = d3;
            d9 = d5;
            d10 = d6;
        }

        double d11 = x + renderer.renderMinX;
        double d12 = y + renderer.renderMinY;
        double d13 = y + renderer.renderMaxY;
        double d14 = z + renderer.renderMinZ;
        double d15 = z + renderer.renderMaxZ;

        if (renderer.renderFromInside)
        {
            d14 = z + renderer.renderMaxZ;
            d15 = z + renderer.renderMinZ;
        }

        if (renderer.enableAO)
        {
            tessellator.setColorOpaque_F(renderer.colorRedTopLeft * rgba[0] / 255F, renderer.colorGreenTopLeft * rgba[1] / 255F, renderer.colorBlueTopLeft * rgba[2] / 255F);
            tessellator.setBrightness(renderer.brightnessTopLeft);
            tessellator.addVertexWithUV(d11, d13, d15, d7, d9);
            tessellator.setColorOpaque_F(renderer.colorRedBottomLeft * rgba[0] / 255F, renderer.colorGreenBottomLeft * rgba[1] / 255F, renderer.colorBlueBottomLeft * rgba[2] / 255F);
            tessellator.setBrightness(renderer.brightnessBottomLeft);
            tessellator.addVertexWithUV(d11, d13, d14, d3, d5);
            tessellator.setColorOpaque_F(renderer.colorRedBottomRight * rgba[0] / 255F, renderer.colorGreenBottomRight * rgba[1] / 255F, renderer.colorBlueBottomRight * rgba[2] / 255F);
            tessellator.setBrightness(renderer.brightnessBottomRight);
            tessellator.addVertexWithUV(d11, d12, d14, d8, d10);
            tessellator.setColorOpaque_F(renderer.colorRedTopRight * rgba[0] / 255F, renderer.colorGreenTopRight * rgba[1] / 255F, renderer.colorBlueTopRight * rgba[2] / 255F);
            tessellator.setBrightness(renderer.brightnessTopRight);
            tessellator.addVertexWithUV(d11, d12, d15, d4, d6);
        }
        else
        {
            tessellator.setColorRGBA((int)(0.6 * rgba[0]), (int)(0.6 * rgba[1]), (int) (0.6 * rgba[2]), 255);
            tessellator.addVertexWithUV(d11, d13, d15, d7, d9);
            tessellator.addVertexWithUV(d11, d13, d14, d3, d5);
            tessellator.addVertexWithUV(d11, d12, d14, d8, d10);
            tessellator.addVertexWithUV(d11, d12, d15, d4, d6);
        }
    }

    @Override
    public void renderYPos(RenderBlocks renderer, Block block, int x, int y, int z) {
        Tessellator tessellator = Tessellator.instance;
        IIcon tIcon = icon.getIcon();

        double d3 = tIcon.getInterpolatedU(renderer.renderMinX * 16.0D);
        double d4 = tIcon.getInterpolatedU(renderer.renderMaxX * 16.0D);
        double d5 = tIcon.getInterpolatedV(renderer.renderMinZ * 16.0D);
        double d6 = tIcon.getInterpolatedV(renderer.renderMaxZ * 16.0D);

        if (renderer.renderMinX < 0.0D || renderer.renderMaxX > 1.0D)
        {
            d3 = tIcon.getMinU();
            d4 = tIcon.getMaxU();
        }

        if (renderer.renderMinZ < 0.0D || renderer.renderMaxZ > 1.0D)
        {
            d5 = tIcon.getMinV();
            d6 = tIcon.getMaxV();
        }

        double d7 = d4;
        double d8 = d3;
        double d9 = d5;
        double d10 = d6;

        if (renderer.uvRotateTop == 1)
        {
            d3 = tIcon.getInterpolatedU(renderer.renderMinZ * 16.0D);
            d5 = tIcon.getInterpolatedV(16.0D - renderer.renderMaxX * 16.0D);
            d4 = tIcon.getInterpolatedU(renderer.renderMaxZ * 16.0D);
            d6 = tIcon.getInterpolatedV(16.0D - renderer.renderMinX * 16.0D);
            d9 = d5;
            d10 = d6;
            d7 = d3;
            d8 = d4;
            d5 = d6;
            d6 = d9;
        }
        else if (renderer.uvRotateTop == 2)
        {
            d3 = tIcon.getInterpolatedU(16.0D - renderer.renderMaxZ * 16.0D);
            d5 = tIcon.getInterpolatedV(renderer.renderMinX * 16.0D);
            d4 = tIcon.getInterpolatedU(16.0D - renderer.renderMinZ * 16.0D);
            d6 = tIcon.getInterpolatedV(renderer.renderMaxX * 16.0D);
            d7 = d4;
            d8 = d3;
            d3 = d4;
            d4 = d8;
            d9 = d6;
            d10 = d5;
        }
        else if (renderer.uvRotateTop == 3)
        {
            d3 = tIcon.getInterpolatedU(16.0D - renderer.renderMinX * 16.0D);
            d4 = tIcon.getInterpolatedU(16.0D - renderer.renderMaxX * 16.0D);
            d5 = tIcon.getInterpolatedV(16.0D - renderer.renderMinZ * 16.0D);
            d6 = tIcon.getInterpolatedV(16.0D - renderer.renderMaxZ * 16.0D);
            d7 = d4;
            d8 = d3;
            d9 = d5;
            d10 = d6;
        }

        double d11 = x + renderer.renderMinX;
        double d12 = x + renderer.renderMaxX;
        double d13 = y + renderer.renderMaxY;
        double d14 = z + renderer.renderMinZ;
        double d15 = z + renderer.renderMaxZ;

        if (renderer.renderFromInside)
        {
            d11 = x + renderer.renderMaxX;
            d12 = x + renderer.renderMinX;
        }

        if (renderer.enableAO)
        {
            tessellator.setColorOpaque_F(renderer.colorRedTopLeft * rgba[0] / 255f, renderer.colorGreenTopLeft * rgba[1] / 255f, renderer.colorBlueTopLeft * rgba[2] / 255f);
            tessellator.setBrightness(renderer.brightnessTopLeft);
            tessellator.addVertexWithUV(d12, d13, d15, d4, d6);
            tessellator.setColorOpaque_F(renderer.colorRedBottomLeft * rgba[0] / 255f, renderer.colorGreenBottomLeft * rgba[1] / 255f, renderer.colorBlueBottomLeft * rgba[2] / 255f);
            tessellator.setBrightness(renderer.brightnessBottomLeft);
            tessellator.addVertexWithUV(d12, d13, d14, d7, d9);
            tessellator.setColorOpaque_F(renderer.colorRedBottomRight * rgba[0] / 255f, renderer.colorGreenBottomRight * rgba[1] / 255f, renderer.colorBlueBottomRight * rgba[2] / 255f);
            tessellator.setBrightness(renderer.brightnessBottomRight);
            tessellator.addVertexWithUV(d11, d13, d14, d3, d5);
            tessellator.setColorOpaque_F(renderer.colorRedTopRight * rgba[0] / 255f, renderer.colorGreenTopRight * rgba[1] / 255f, renderer.colorBlueTopRight * rgba[2] / 255f);
            tessellator.setBrightness(renderer.brightnessTopRight);
            tessellator.addVertexWithUV(d11, d13, d15, d8, d10);
        }
        else
        {
            tessellator.setColorRGBA(rgba[0], rgba[1], rgba[2], 255);
            tessellator.addVertexWithUV(d12, d13, d15, d4, d6);
            tessellator.addVertexWithUV(d12, d13, d14, d7, d9);
            tessellator.addVertexWithUV(d11, d13, d14, d3, d5);
            tessellator.addVertexWithUV(d11, d13, d15, d8, d10);
        }
    }

    @Override
    public void renderYNeg(RenderBlocks renderer, Block block, int x, int y, int z) {
        Tessellator tessellator = Tessellator.instance;

        IIcon tIcon = icon.getIcon();

        double d3 = tIcon.getInterpolatedU(renderer.renderMinX * 16.0D);
        double d4 = tIcon.getInterpolatedU(renderer.renderMaxX * 16.0D);
        double d5 = tIcon.getInterpolatedV(renderer.renderMinZ * 16.0D);
        double d6 = tIcon.getInterpolatedV(renderer.renderMaxZ * 16.0D);

        if (renderer.renderMinX < 0.0D || renderer.renderMaxX > 1.0D)
        {
            d3 = tIcon.getMinU();
            d4 = tIcon.getMaxU();
        }

        if (renderer.renderMinZ < 0.0D || renderer.renderMaxZ > 1.0D)
        {
            d5 = tIcon.getMinV();
            d6 = tIcon.getMaxV();
        }

        double d7 = d4;
        double d8 = d3;
        double d9 = d5;
        double d10 = d6;

        if (renderer.uvRotateBottom == 2)
        {
            d3 = tIcon.getInterpolatedU(renderer.renderMinZ * 16.0D);
            d5 = tIcon.getInterpolatedV(16.0D - renderer.renderMaxX * 16.0D);
            d4 = tIcon.getInterpolatedU(renderer.renderMaxZ * 16.0D);
            d6 = tIcon.getInterpolatedV(16.0D - renderer.renderMinX * 16.0D);
            d9 = d5;
            d10 = d6;
            d7 = d3;
            d8 = d4;
            d5 = d6;
            d6 = d9;
        }
        else if (renderer.uvRotateBottom == 1)
        {
            d3 = tIcon.getInterpolatedU(16.0D - renderer.renderMaxZ * 16.0D);
            d5 = tIcon.getInterpolatedV(renderer.renderMinX * 16.0D);
            d4 = tIcon.getInterpolatedU(16.0D - renderer.renderMinZ * 16.0D);
            d6 = tIcon.getInterpolatedV(renderer.renderMaxX * 16.0D);
            d7 = d4;
            d8 = d3;
            d3 = d4;
            d4 = d8;
            d9 = d6;
            d10 = d5;
        }
        else if (renderer.uvRotateBottom == 3)
        {
            d3 = tIcon.getInterpolatedU(16.0D - renderer.renderMinX * 16.0D);
            d4 = tIcon.getInterpolatedU(16.0D - renderer.renderMaxX * 16.0D);
            d5 = tIcon.getInterpolatedV(16.0D - renderer.renderMinZ * 16.0D);
            d6 = tIcon.getInterpolatedV(16.0D - renderer.renderMaxZ * 16.0D);
            d7 = d4;
            d8 = d3;
            d9 = d5;
            d10 = d6;
        }

        double d11 = x + renderer.renderMinX;
        double d12 = x + renderer.renderMaxX;
        double d13 = y + renderer.renderMinY;
        double d14 = z + renderer.renderMinZ;
        double d15 = z + renderer.renderMaxZ;

        if (renderer.renderFromInside)
        {
            d11 = x + renderer.renderMaxX;
            d12 = x + renderer.renderMinX;
        }

        if (renderer.enableAO)
        {
            tessellator.setColorOpaque_F(renderer.colorRedTopLeft * rgba[0] / 255F, renderer.colorGreenTopLeft * rgba[1] / 255F, renderer.colorBlueTopLeft * rgba[2] / 255F);
            tessellator.setBrightness(renderer.brightnessTopLeft);
            tessellator.addVertexWithUV(d11, d13, d15, d8, d10);
            tessellator.setColorOpaque_F(renderer.colorRedBottomLeft * rgba[0] / 255F, renderer.colorGreenBottomLeft * rgba[1] / 255F, renderer.colorBlueBottomLeft * rgba[2] / 255F);
            tessellator.setBrightness(renderer.brightnessBottomLeft);
            tessellator.addVertexWithUV(d11, d13, d14, d3, d5);
            tessellator.setColorOpaque_F(renderer.colorRedBottomRight * rgba[0] / 255F, renderer.colorGreenBottomRight * rgba[1] / 255F, renderer.colorBlueBottomRight * rgba[2] / 255F);
            tessellator.setBrightness(renderer.brightnessBottomRight);
            tessellator.addVertexWithUV(d12, d13, d14, d7, d9);
            tessellator.setColorOpaque_F(renderer.colorRedTopRight * rgba[0] / 255F, renderer.colorGreenTopRight * rgba[1] / 255F, renderer.colorBlueTopRight * rgba[2] / 255F);
            tessellator.setBrightness(renderer.brightnessTopRight);
            tessellator.addVertexWithUV(d12, d13, d15, d4, d6);
        }
        else
        {
            tessellator.setColorRGBA((int)(0.5 * rgba[0]), (int)(0.5 * rgba[1]), (int) (0.5 * rgba[2]), 255);
            tessellator.addVertexWithUV(d11, d13, d15, d8, d10);
            tessellator.addVertexWithUV(d11, d13, d14, d3, d5);
            tessellator.addVertexWithUV(d12, d13, d14, d7, d9);
            tessellator.addVertexWithUV(d12, d13, d15, d4, d6);
        }
    }

    @Override
    public void renderZPos(RenderBlocks renderer, Block block, int x, int y, int z) {
        Tessellator tessellator = Tessellator.instance;

        IIcon tIcon = icon.getIcon();
        
        double d3 = tIcon.getInterpolatedU(renderer.renderMinX * 16.0D);
        double d4 = tIcon.getInterpolatedU(renderer.renderMaxX * 16.0D);
        double d5 = tIcon.getInterpolatedV(16.0D - renderer.renderMaxY * 16.0D);
        double d6 = tIcon.getInterpolatedV(16.0D - renderer.renderMinY * 16.0D);
        double d7;

        if (renderer.flipTexture)
        {
            d7 = d3;
            d3 = d4;
            d4 = d7;
        }

        if (renderer.renderMinX < 0.0D || renderer.renderMaxX > 1.0D)
        {
            d3 = tIcon.getMinU();
            d4 = tIcon.getMaxU();
        }

        if (renderer.renderMinY < 0.0D || renderer.renderMaxY > 1.0D)
        {
            d5 = tIcon.getMinV();
            d6 = tIcon.getMaxV();
        }

        d7 = d4;
        double d8 = d3;
        double d9 = d5;
        double d10 = d6;

        if (renderer.uvRotateWest == 1)
        {
            d3 = tIcon.getInterpolatedU(renderer.renderMinY * 16.0D);
            d6 = tIcon.getInterpolatedV(16.0D - renderer.renderMinX * 16.0D);
            d4 = tIcon.getInterpolatedU(renderer.renderMaxY * 16.0D);
            d5 = tIcon.getInterpolatedV(16.0D - renderer.renderMaxX * 16.0D);
            d9 = d5;
            d10 = d6;
            d7 = d3;
            d8 = d4;
            d5 = d6;
            d6 = d9;
        }
        else if (renderer.uvRotateWest == 2)
        {
            d3 = tIcon.getInterpolatedU(16.0D - renderer.renderMaxY * 16.0D);
            d5 = tIcon.getInterpolatedV(renderer.renderMinX * 16.0D);
            d4 = tIcon.getInterpolatedU(16.0D - renderer.renderMinY * 16.0D);
            d6 = tIcon.getInterpolatedV(renderer.renderMaxX * 16.0D);
            d7 = d4;
            d8 = d3;
            d3 = d4;
            d4 = d8;
            d9 = d6;
            d10 = d5;
        }
        else if (renderer.uvRotateWest == 3)
        {
            d3 = tIcon.getInterpolatedU(16.0D - renderer.renderMinX * 16.0D);
            d4 = tIcon.getInterpolatedU(16.0D - renderer.renderMaxX * 16.0D);
            d5 = tIcon.getInterpolatedV(renderer.renderMaxY * 16.0D);
            d6 = tIcon.getInterpolatedV(renderer.renderMinY * 16.0D);
            d7 = d4;
            d8 = d3;
            d9 = d5;
            d10 = d6;
        }

        double d11 = x + renderer.renderMinX;
        double d12 = x + renderer.renderMaxX;
        double d13 = y + renderer.renderMinY;
        double d14 = y + renderer.renderMaxY;
        double d15 = z + renderer.renderMaxZ;

        if (renderer.renderFromInside)
        {
            d11 = x + renderer.renderMaxX;
            d12 = x + renderer.renderMinX;
        }

        if (renderer.enableAO)
        {
            tessellator.setColorOpaque_F(renderer.colorRedTopLeft * rgba[0] / 255F, renderer.colorGreenTopLeft * rgba[1] / 255F, renderer.colorBlueTopLeft * rgba[2] / 255F);
            tessellator.setBrightness(renderer.brightnessTopLeft);
            tessellator.addVertexWithUV(d11, d14, d15, d3, d5);
            tessellator.setColorOpaque_F(renderer.colorRedBottomLeft * rgba[0] / 255F, renderer.colorGreenBottomLeft * rgba[1] / 255F, renderer.colorBlueBottomLeft * rgba[2] / 255F);
            tessellator.setBrightness(renderer.brightnessBottomLeft);
            tessellator.addVertexWithUV(d11, d13, d15, d8, d10);
            tessellator.setColorOpaque_F(renderer.colorRedBottomRight * rgba[0] / 255F, renderer.colorGreenBottomRight * rgba[1] / 255F, renderer.colorBlueBottomRight * rgba[2] / 255F);
            tessellator.setBrightness(renderer.brightnessBottomRight);
            tessellator.addVertexWithUV(d12, d13, d15, d4, d6);
            tessellator.setColorOpaque_F(renderer.colorRedTopRight * rgba[0] / 255F, renderer.colorGreenTopRight * rgba[1] / 255F, renderer.colorBlueTopRight * rgba[2] / 255F);
            tessellator.setBrightness(renderer.brightnessTopRight);
            tessellator.addVertexWithUV(d12, d14, d15, d7, d9);
        }
        else
        {
            tessellator.setColorRGBA((int)(0.6 * rgba[0]), (int)(0.6 * rgba[1]), (int) (0.6 * rgba[2]), 255);
            tessellator.addVertexWithUV(d11, d14, d15, d3, d5);
            tessellator.addVertexWithUV(d11, d13, d15, d8, d10);
            tessellator.addVertexWithUV(d12, d13, d15, d4, d6);
            tessellator.addVertexWithUV(d12, d14, d15, d7, d9);
        }
    }

    @Override
    public void renderZNeg(RenderBlocks renderer, Block block, int x, int y, int z) {
        Tessellator tessellator = Tessellator.instance;

        IIcon tIcon = icon.getIcon();
        
        double d3 = tIcon.getInterpolatedU(renderer.renderMinX * 16.0D);
        double d4 = tIcon.getInterpolatedU(renderer.renderMaxX * 16.0D);

        if (renderer.field_152631_f)
        {
            d4 = tIcon.getInterpolatedU((1.0D - renderer.renderMinX) * 16.0D);
            d3 = tIcon.getInterpolatedU((1.0D - renderer.renderMaxX) * 16.0D);
        }

        double d5 = tIcon.getInterpolatedV(16.0D - renderer.renderMaxY * 16.0D);
        double d6 = tIcon.getInterpolatedV(16.0D - renderer.renderMinY * 16.0D);
        double d7;

        if (renderer.flipTexture)
        {
            d7 = d3;
            d3 = d4;
            d4 = d7;
        }

        if (renderer.renderMinX < 0.0D || renderer.renderMaxX > 1.0D)
        {
            d3 = tIcon.getMinU();
            d4 = tIcon.getMaxU();
        }

        if (renderer.renderMinY < 0.0D || renderer.renderMaxY > 1.0D)
        {
            d5 = tIcon.getMinV();
            d6 = tIcon.getMaxV();
        }

        d7 = d4;
        double d8 = d3;
        double d9 = d5;
        double d10 = d6;

        if (renderer.uvRotateEast == 2)
        {
            d3 = tIcon.getInterpolatedU(renderer.renderMinY * 16.0D);
            d4 = tIcon.getInterpolatedU(renderer.renderMaxY * 16.0D);
            d5 = tIcon.getInterpolatedV(16.0D - renderer.renderMinX * 16.0D);
            d6 = tIcon.getInterpolatedV(16.0D - renderer.renderMaxX * 16.0D);
            d9 = d5;
            d10 = d6;
            d7 = d3;
            d8 = d4;
            d5 = d6;
            d6 = d9;
        }
        else if (renderer.uvRotateEast == 1)
        {
            d3 = tIcon.getInterpolatedU(16.0D - renderer.renderMaxY * 16.0D);
            d4 = tIcon.getInterpolatedU(16.0D - renderer.renderMinY * 16.0D);
            d5 = tIcon.getInterpolatedV(renderer.renderMaxX * 16.0D);
            d6 = tIcon.getInterpolatedV(renderer.renderMinX * 16.0D);
            d7 = d4;
            d8 = d3;
            d3 = d4;
            d4 = d8;
            d9 = d6;
            d10 = d5;
        }
        else if (renderer.uvRotateEast == 3)
        {
            d3 = tIcon.getInterpolatedU(16.0D - renderer.renderMinX * 16.0D);
            d4 = tIcon.getInterpolatedU(16.0D - renderer.renderMaxX * 16.0D);
            d5 = tIcon.getInterpolatedV(renderer.renderMaxY * 16.0D);
            d6 = tIcon.getInterpolatedV(renderer.renderMinY * 16.0D);
            d7 = d4;
            d8 = d3;
            d9 = d5;
            d10 = d6;
        }

        double d11 = x + renderer.renderMinX;
        double d12 = x + renderer.renderMaxX;
        double d13 = y + renderer.renderMinY;
        double d14 = y + renderer.renderMaxY;
        double d15 = z + renderer.renderMinZ;

        if (renderer.renderFromInside)
        {
            d11 = x + renderer.renderMaxX;
            d12 = x + renderer.renderMinX;
        }

        if (renderer.enableAO)
        {
            tessellator.setColorOpaque_F(renderer.colorRedTopLeft * rgba[0] / 255F, renderer.colorGreenTopLeft * rgba[1] / 255F, renderer.colorBlueTopLeft * rgba[2] / 255F);
            tessellator.setBrightness(renderer.brightnessTopLeft);
            tessellator.addVertexWithUV(d11, d14, d15, d7, d9);
            tessellator.setColorOpaque_F(renderer.colorRedBottomLeft * rgba[0] / 255F, renderer.colorGreenBottomLeft * rgba[1] / 255F, renderer.colorBlueBottomLeft * rgba[2] / 255F);
            tessellator.setBrightness(renderer.brightnessBottomLeft);
            tessellator.addVertexWithUV(d12, d14, d15, d3, d5);
            tessellator.setColorOpaque_F(renderer.colorRedBottomRight * rgba[0] / 255F, renderer.colorGreenBottomRight * rgba[1] / 255F, renderer.colorBlueBottomRight * rgba[2] / 255F);
            tessellator.setBrightness(renderer.brightnessBottomRight);
            tessellator.addVertexWithUV(d12, d13, d15, d8, d10);
            tessellator.setColorOpaque_F(renderer.colorRedTopRight * rgba[0] / 255F, renderer.colorGreenTopRight * rgba[1] / 255F, renderer.colorBlueTopRight * rgba[2] / 255F);
            tessellator.setBrightness(renderer.brightnessTopRight);
            tessellator.addVertexWithUV(d11, d13, d15, d4, d6);
        }
        else
        {
            tessellator.setColorRGBA((int)(0.6 * rgba[0]), (int)(0.6 * rgba[1]), (int) (0.6 * rgba[2]), 255);
            tessellator.addVertexWithUV(d11, d14, d15, d7, d9);
            tessellator.addVertexWithUV(d12, d14, d15, d3, d5);
            tessellator.addVertexWithUV(d12, d13, d15, d8, d10);
            tessellator.addVertexWithUV(d11, d13, d15, d4, d6);
        }
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