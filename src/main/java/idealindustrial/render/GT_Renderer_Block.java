package idealindustrial.render;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import idealindustrial.textures.ITexture;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

public class GT_Renderer_Block
        implements ISimpleBlockRenderingHandler {
    public static GT_Renderer_Block INSTANCE;
    public final int mRenderID;

    public GT_Renderer_Block() {
        this.mRenderID = RenderingRegistry.getNextAvailableRenderId();
        INSTANCE = this;
        RenderingRegistry.registerBlockHandler(this);
    }

    public static void renderInventory(Block aBlock, RenderBlocks aRenderer, ITexture[][] textures) {
        Tessellator.instance.startDrawingQuads();
        Tessellator.instance.setNormal(0.0F, -1.0F, 0.0F);
        renderNegativeYFacing(null, aRenderer, aBlock, 0, 0, 0, textures[0], true);
        Tessellator.instance.draw();

        Tessellator.instance.startDrawingQuads();
        Tessellator.instance.setNormal(0.0F, 1.0F, 0.0F);
        renderPositiveYFacing(null, aRenderer, aBlock, 0, 0, 0, textures[1], true);
        Tessellator.instance.draw();

        Tessellator.instance.startDrawingQuads();
        Tessellator.instance.setNormal(0.0F, 0.0F, -1.0F);
        renderNegativeZFacing(null, aRenderer, aBlock, 0, 0, 0, textures[2], true);
        Tessellator.instance.draw();

        Tessellator.instance.startDrawingQuads();
        Tessellator.instance.setNormal(0.0F, 0.0F, 1.0F);
        renderPositiveZFacing(null, aRenderer, aBlock, 0, 0, 0, textures[3], true);
        Tessellator.instance.draw();

        Tessellator.instance.startDrawingQuads();
        Tessellator.instance.setNormal(-1.0F, 0.0F, 0.0F);
        renderNegativeXFacing(null, aRenderer, aBlock, 0, 0, 0, textures[4], true);
        Tessellator.instance.draw();

        Tessellator.instance.startDrawingQuads();
        Tessellator.instance.setNormal(1.0F, 0.0F, 0.0F);
        renderPositiveXFacing(null, aRenderer, aBlock, 0, 0, 0, textures[5], true);
        Tessellator.instance.draw();
    }

    public static boolean renderStandardBlock(IBlockAccess aWorld, int aX, int aY, int aZ, Block aBlock, RenderBlocks aRenderer) {
        TileEntity tTileEntity = aWorld.getTileEntity(aX, aY, aZ);
        if (tTileEntity instanceof IFastRenderedTileEntity){
            IFastRenderedTileEntity fastRenderedTileEntity = ((IFastRenderedTileEntity) tTileEntity);
            if (fastRenderedTileEntity.getCustomRenderer() != null) {
                if (!fastRenderedTileEntity.getCustomRenderer().shouldRender(fastRenderedTileEntity)) {
                    return true;
                }
                return fastRenderedTileEntity.getCustomRenderer().renderWorldBlock(aWorld, fastRenderedTileEntity, aX, aY, aZ, aBlock, aRenderer);
            }
            return renderStandardBlock(aWorld, aX, aY, aZ, aBlock, aRenderer, ((IFastRenderedTileEntity)tTileEntity).getTextures());
        }
        return false;
    }

    public static boolean renderStandardBlock(IBlockAccess aWorld, int aX, int aY, int aZ, Block aBlock, RenderBlocks aRenderer, ITexture[][] aTextures) {
        aBlock.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        aRenderer.setRenderBoundsFromBlock(aBlock);

        renderNegativeYFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, aTextures[0], true);
        renderPositiveYFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, aTextures[1], true);
        renderNegativeZFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, aTextures[2], true);
        renderPositiveZFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, aTextures[3], true);
        renderNegativeXFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, aTextures[4], true);
        renderPositiveXFacing(aWorld, aRenderer, aBlock, aX, aY, aZ, aTextures[5], true);
        return true;
    }

    public static void renderNegativeYFacing(IBlockAccess aWorld, RenderBlocks aRenderer, Block aBlock, int aX, int aY, int aZ, ITexture[] aIcon, boolean aFullBlock) {
        if (aWorld != null) {
            if ((aFullBlock) && (!aBlock.shouldSideBeRendered(aWorld, aX, aY - 1, aZ, 0))) {
                return;
            }
            Tessellator.instance.setBrightness(aBlock.getMixedBrightnessForBlock(aWorld, aX, aFullBlock ? aY - 1 : aY, aZ));
        }
        if (aIcon != null) {
            for (int i = 0; i < aIcon.length; i++) {
                if (aIcon[i] != null) {
                    aIcon[i].renderYNeg(aRenderer, aBlock, aX, aY, aZ);
                }
            }
        }
        aRenderer.flipTexture = false;
    }

    public static void renderPositiveYFacing(IBlockAccess aWorld, RenderBlocks aRenderer, Block aBlock, int aX, int aY, int aZ, ITexture[] aIcon, boolean aFullBlock) {
        if (aWorld != null) {
            if ((aFullBlock) && (!aBlock.shouldSideBeRendered(aWorld, aX, aY + 1, aZ, 1))) {
                return;
            }
            Tessellator.instance.setBrightness(aBlock.getMixedBrightnessForBlock(aWorld, aX, aFullBlock ? aY + 1 : aY, aZ));
        }
        if (aIcon != null) {
            for (int i = 0; i < aIcon.length; i++) {
                if (aIcon[i] != null) {
                    aIcon[i].renderYPos(aRenderer, aBlock, aX, aY, aZ);
                }
            }
        }
        aRenderer.flipTexture = false;
    }

    public static void renderNegativeZFacing(IBlockAccess aWorld, RenderBlocks aRenderer, Block aBlock, int aX, int aY, int aZ, ITexture[] aIcon, boolean aFullBlock) {
        if (aWorld != null) {
            if ((aFullBlock) && (!aBlock.shouldSideBeRendered(aWorld, aX, aY, aZ - 1, 2))) {
                return;
            }
            Tessellator.instance.setBrightness(aBlock.getMixedBrightnessForBlock(aWorld, aX, aY, aFullBlock ? aZ - 1 : aZ));
        }
        aRenderer.flipTexture = (!aFullBlock);
        if (aIcon != null) {
            for (int i = 0; i < aIcon.length; i++) {
                if (aIcon[i] != null) {
                    aIcon[i].renderZNeg(aRenderer, aBlock, aX, aY, aZ);
                }
            }
        }
        aRenderer.flipTexture = false;
    }

    public static void renderPositiveZFacing(IBlockAccess aWorld, RenderBlocks aRenderer, Block aBlock, int aX, int aY, int aZ, ITexture[] aIcon, boolean aFullBlock) {
        if (aWorld != null) {
            if ((aFullBlock) && (!aBlock.shouldSideBeRendered(aWorld, aX, aY, aZ + 1, 3))) {
                return;
            }
            Tessellator.instance.setBrightness(aBlock.getMixedBrightnessForBlock(aWorld, aX, aY, aFullBlock ? aZ + 1 : aZ));
        }
        if (aIcon != null) {
            for (int i = 0; i < aIcon.length; i++) {
                if (aIcon[i] != null) {
                    aIcon[i].renderZPos(aRenderer, aBlock, aX, aY, aZ);
                }
            }
        }
        aRenderer.flipTexture = false;
    }

    public static void renderNegativeXFacing(IBlockAccess aWorld, RenderBlocks aRenderer, Block aBlock, int aX, int aY, int aZ, ITexture[] aIcon, boolean aFullBlock) {
        if (aWorld != null) {
            if ((aFullBlock) && (!aBlock.shouldSideBeRendered(aWorld, aX - 1, aY, aZ, 4))) {
                return;
            }
            Tessellator.instance.setBrightness(aBlock.getMixedBrightnessForBlock(aWorld, aFullBlock ? aX - 1 : aX, aY, aZ));
        }
        if (aIcon != null) {
            for (int i = 0; i < aIcon.length; i++) {
                if (aIcon[i] != null) {
                    aIcon[i].renderXNeg(aRenderer, aBlock, aX, aY, aZ);
                }
            }
        }
        aRenderer.flipTexture = false;
    }

    public static void renderPositiveXFacing(IBlockAccess aWorld, RenderBlocks aRenderer, Block aBlock, int aX, int aY, int aZ, ITexture[] aIcon, boolean aFullBlock) {
        if (aWorld != null) {
            if ((aFullBlock) && (!aBlock.shouldSideBeRendered(aWorld, aX + 1, aY, aZ, 5))) {
                return;
            }
            Tessellator.instance.setBrightness(aBlock.getMixedBrightnessForBlock(aWorld, aFullBlock ? aX + 1 : aX, aY, aZ));
        }
        aRenderer.flipTexture = (!aFullBlock);
        if (aIcon != null) {
            for (int i = 0; i < aIcon.length; i++) {
                if (aIcon[i] != null) {
                    aIcon[i].renderXPos(aRenderer, aBlock, aX, aY, aZ);
                }
            }
        }
        aRenderer.flipTexture = false;
    }


    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {

    }

    public boolean renderWorldBlock(IBlockAccess aWorld, int aX, int aY, int aZ, Block aBlock, int aModelID, RenderBlocks aRenderer) {
        TileEntity aTileEntity = aWorld.getTileEntity(aX, aY, aZ);
        if (aTileEntity == null) {
            return false;
        }
        return renderStandardBlock(aWorld, aX, aY, aZ, aBlock, aRenderer);
    }

    public boolean shouldRender3DInInventory(int aModel) {
        return true;
    }

    public int getRenderId() {
        return this.mRenderID;
    }
}
