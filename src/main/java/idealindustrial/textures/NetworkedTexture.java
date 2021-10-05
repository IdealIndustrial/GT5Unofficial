package idealindustrial.textures;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.api.enums.Dyes;
import gregtech.api.interfaces.IIconContainer;
import gregtech.api.objects.GT_RenderedTexture;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

public class NetworkedTexture implements INetworkedTexture {
    private IIcon icon;
    private boolean mAllowAlpha = true;
    public short[] mRGBa = new short[]{255, 255, 255, 0};;
    //all positive for custom, all negative for non-registered
    //if neg: 1,0x8, 3 bit for side, 4 bit for meta, 16 bit for block id
    private int id;

    public NetworkedTexture(Block block, int meta, int side) {
        assert FMLCommonHandler.instance().getEffectiveSide().isServer(); // server only method
//        icon = block.getIcon(side, meta);
        id = Block.getIdFromBlock(block) | (meta << 16) | (side << 20);
        id = -id;
    }

    private NetworkedTexture(IIcon icon, int id) {
        this.icon = icon;
        this.id = id;
    }

    public static NetworkedTexture load(int id) {
        int tID = id;
        id = -id;
        int blockId = id & 0xFFFF;
        int meta = (id >> 16) & 0xF;
        int side = (id >> 20) & 0b111;
        Block b = Block.getBlockById(blockId);
        return new NetworkedTexture(b.getIcon(side, meta), tID);
    }

    @Override
    public void renderXPos(RenderBlocks aRenderer, Block aBlock, int aX, int aY, int aZ) {
        Tessellator.instance.setColorRGBA((int) (mRGBa[0] * 0.6F), (int) (mRGBa[1] * 0.6F), (int) (mRGBa[2] * 0.6F), mAllowAlpha ? 255 - mRGBa[3] : 255);
        aRenderer.renderFaceXPos(aBlock, aX, aY, aZ, icon);
    }

    @Override
    public void renderXNeg(RenderBlocks aRenderer, Block aBlock, int aX, int aY, int aZ) {
        Tessellator.instance.setColorRGBA((int) (mRGBa[0] * 0.6F), (int) (mRGBa[1] * 0.6F), (int) (mRGBa[2] * 0.6F), mAllowAlpha ? 255 - mRGBa[3] : 255);
        aRenderer.renderFaceXNeg(aBlock, aX, aY, aZ, icon);
    }

    @Override
    public void renderYPos(RenderBlocks aRenderer, Block aBlock, int aX, int aY, int aZ) {
        Tessellator.instance.setColorRGBA((int) (mRGBa[0] * 1.0F), (int) (mRGBa[1] * 1.0F), (int) (mRGBa[2] * 1.0F), mAllowAlpha ? 255 - mRGBa[3] : 255);
        aRenderer.renderFaceYPos(aBlock, aX, aY, aZ, icon);
    }

    @Override
    public void renderYNeg(RenderBlocks aRenderer, Block aBlock, int aX, int aY, int aZ) {
        Tessellator.instance.setColorRGBA((int) (mRGBa[0] * 0.5F), (int) (mRGBa[1] * 0.5F), (int) (mRGBa[2] * 0.5F), mAllowAlpha ? 255 - mRGBa[3] : 255);
        IIcon aIcon = icon;

        float d_16 = 16.0F;
        float d3 = (float)aIcon.getInterpolatedU(aRenderer.renderMaxX * d_16);
        float d4 = (float)aIcon.getInterpolatedU(aRenderer.renderMinX * d_16);
        float d5 = (float)aIcon.getInterpolatedV(aRenderer.renderMinZ * d_16);
        float d6 = (float)aIcon.getInterpolatedV(aRenderer.renderMaxZ * d_16);

        if (aRenderer.renderMinX < 0.0D || aRenderer.renderMaxX > 1.0D) {
            d3 = aIcon.getMaxU();
            d4 = aIcon.getMinU();
        }

        if (aRenderer.renderMinZ < 0.0D || aRenderer.renderMaxZ > 1.0D) {
            d5 = aIcon.getMinV();
            d6 = aIcon.getMaxV();
        }

        float d11 = aX + (float)aRenderer.renderMinX;
        float d12 = aX + (float)aRenderer.renderMaxX;
        float d13 = aY + (float)aRenderer.renderMinY;
        float d14 = aZ + (float)aRenderer.renderMinZ;
        float d15 = aZ + (float)aRenderer.renderMaxZ;

        Tessellator.instance.addVertexWithUV((double)d11, (double)d13, (double)d15, (double)d3, (double)d6);
        Tessellator.instance.addVertexWithUV((double)d11, (double)d13, (double)d14, (double)d3, (double)d5);
        Tessellator.instance.addVertexWithUV((double)d12, (double)d13, (double)d14, (double)d4, (double)d5);
        Tessellator.instance.addVertexWithUV((double)d12, (double)d13, (double)d15, (double)d4, (double)d6);
    }

    @Override
    public void renderZPos(RenderBlocks aRenderer, Block aBlock, int aX, int aY, int aZ) {
        Tessellator.instance.setColorRGBA((int) (mRGBa[0] * 0.8F), (int) (mRGBa[1] * 0.8F), (int) (mRGBa[2] * 0.8F), mAllowAlpha ? 255 - mRGBa[3] : 255);
        aRenderer.renderFaceZPos(aBlock, aX, aY, aZ, icon);
    }

    @Override
    public void renderZNeg(RenderBlocks aRenderer, Block aBlock, int aX, int aY, int aZ) {
        Tessellator.instance.setColorRGBA((int) (mRGBa[0] * 0.8F), (int) (mRGBa[1] * 0.8F), (int) (mRGBa[2] * 0.8F), mAllowAlpha ? 255 - mRGBa[3] : 255);
        aRenderer.renderFaceZNeg(aBlock, aX, aY, aZ, icon);
    }


    @Override
    public boolean isValidTexture() {
        return icon != null;
    }

    @Override
    public IIconContainer getContainer() {
        return new IIconContainer() {
            @Override
            public IIcon getIcon() {
                return icon;
            }

            @Override
            public IIcon getOverlayIcon() {
                return null;
            }

            @Override
            public ResourceLocation getTextureFile() {
                return null;
            }
        };
    }

    @Override
    public int getID() {
        return id;
    }
}
