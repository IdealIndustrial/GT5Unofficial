package idealindustrial.tile.meta.connected;

import gregtech.api.interfaces.IFastRenderedTileEntity;
import gregtech.api.interfaces.ITexture;
import idealindustrial.render.II_CustomRenderer;
import idealindustrial.tile.base.II_BaseTile;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

import static gregtech.common.render.GT_Renderer_Block.*;

public class II_MetaConnectedRenderer implements II_CustomRenderer {
    public static final II_MetaConnectedRenderer INSTANCE = new II_MetaConnectedRenderer();


    @Override
    public boolean renderWorldBlock(IBlockAccess world, IFastRenderedTileEntity tileEntity, int x, int y, int z, Block block, RenderBlocks renderBlocks) {
        II_BaseMetaConnected tileConnected = (II_BaseMetaConnected) ((II_BaseTile) tileEntity).getMetaTile();
        int connections = tileConnected.connections;
        {
            boolean[] connected = new boolean[6];
            for (int i = 0; i < 6; i++) {
                if ((connections & (1 << i)) != 0) {
                    connected[i] = true;
                }
            }
           int newConnections = 0;
           newConnections |= connected[4] ? 1 : 0;
           newConnections |= connected[5] ? 2 : 0;
           newConnections |= connected[0] ? 4 : 0;
           newConnections |= connected[1] ? 8 : 0;
           newConnections |= connected[2] ? 16 : 0;
           newConnections |= connected[3] ? 32 : 0;
//           connections = newConnections;
            connections = (connections | (connections << 6)) >> 4;
            connections &= 0x3F;
        }//convert II connections to GT connections
        float tThickness = tileConnected.getThickness();
        if (tThickness >= 0.99F) {
            return renderStandardBlock(world, x, y, z, block, renderBlocks);
        }
        float sp = (1.0F - tThickness) / 2.0F;
        boolean[] tIsCovered = new boolean[6];
//        for (byte i = 0; i < 6; i = (byte) (i + 1)) {
//            tIsCovered[i] = (aTileEntity.getCoverIDAtSide(i) != 0);
//        }
        if ((tIsCovered[0]) && (tIsCovered[1]) && (tIsCovered[2]) && (tIsCovered[3]) && (tIsCovered[4]) && (tIsCovered[5])) {
            return renderStandardBlock(world, x, y, z, block, renderBlocks);
        }
        ITexture[][] tIcons;
        ITexture[][] tCovers;

        tIcons = tileEntity.getTextures(false);
        tCovers = tileEntity.getTextures(true);
        if (tIcons == null || tCovers == null) {
            return true;
        }


        if (connections == 0) {
            block.setBlockBounds(sp, sp, sp, sp + tThickness, sp + tThickness, sp + tThickness);
            renderBlocks.setRenderBoundsFromBlock(block);
            renderNegativeYFacing(world, renderBlocks, block, x, y, z, tIcons[0], false);
            renderPositiveYFacing(world, renderBlocks, block, x, y, z, tIcons[1], false);
            renderNegativeZFacing(world, renderBlocks, block, x, y, z, tIcons[2], false);
            renderPositiveZFacing(world, renderBlocks, block, x, y, z, tIcons[3], false);
            renderNegativeXFacing(world, renderBlocks, block, x, y, z, tIcons[4], false);
            renderPositiveXFacing(world, renderBlocks, block, x, y, z, tIcons[5], false);
        } else if (connections == 3) {
            block.setBlockBounds(0.0F, sp, sp, 1.0F, sp + tThickness, sp + tThickness);
            renderBlocks.setRenderBoundsFromBlock(block);
            renderNegativeYFacing(world, renderBlocks, block, x, y, z, tIcons[0], false);
            renderPositiveYFacing(world, renderBlocks, block, x, y, z, tIcons[1], false);
            renderNegativeZFacing(world, renderBlocks, block, x, y, z, tIcons[2], false);
            renderPositiveZFacing(world, renderBlocks, block, x, y, z, tIcons[3], false);
            if (!tIsCovered[4]) {
                renderNegativeXFacing(world, renderBlocks, block, x, y, z, tIcons[4], false);
            }
            if (!tIsCovered[5]) {
                renderPositiveXFacing(world, renderBlocks, block, x, y, z, tIcons[5], false);
            }
        } else if (connections == 12) {
            block.setBlockBounds(sp, 0.0F, sp, sp + tThickness, 1.0F, sp + tThickness);
            renderBlocks.setRenderBoundsFromBlock(block);
            renderNegativeZFacing(world, renderBlocks, block, x, y, z, tIcons[2], false);
            renderPositiveZFacing(world, renderBlocks, block, x, y, z, tIcons[3], false);
            renderNegativeXFacing(world, renderBlocks, block, x, y, z, tIcons[4], false);
            renderPositiveXFacing(world, renderBlocks, block, x, y, z, tIcons[5], false);
            if (!tIsCovered[0]) {
                renderNegativeYFacing(world, renderBlocks, block, x, y, z, tIcons[0], false);
            }
            if (!tIsCovered[1]) {
                renderPositiveYFacing(world, renderBlocks, block, x, y, z, tIcons[1], false);
            }
        } else if (connections == 48) {
            block.setBlockBounds(sp, sp, 0.0F, sp + tThickness, sp + tThickness, 1.0F);
            renderBlocks.setRenderBoundsFromBlock(block);
            renderNegativeYFacing(world, renderBlocks, block, x, y, z, tIcons[0], false);
            renderPositiveYFacing(world, renderBlocks, block, x, y, z, tIcons[1], false);
            renderNegativeXFacing(world, renderBlocks, block, x, y, z, tIcons[4], false);
            renderPositiveXFacing(world, renderBlocks, block, x, y, z, tIcons[5], false);
            if (!tIsCovered[2]) {
                renderNegativeZFacing(world, renderBlocks, block, x, y, z, tIcons[2], false);
            }
            if (!tIsCovered[3]) {
                renderPositiveZFacing(world, renderBlocks, block, x, y, z, tIcons[3], false);
            }
        } else {
            if ((connections & 0x1) == 0) {
                block.setBlockBounds(sp, sp, sp, sp + tThickness, sp + tThickness, sp + tThickness);
                renderBlocks.setRenderBoundsFromBlock(block);
                renderNegativeXFacing(world, renderBlocks, block, x, y, z, tIcons[4], false);
            } else {
                block.setBlockBounds(0.0F, sp, sp, sp, sp + tThickness, sp + tThickness);
                renderBlocks.setRenderBoundsFromBlock(block);
                renderNegativeYFacing(world, renderBlocks, block, x, y, z, tIcons[0], false);
                renderPositiveYFacing(world, renderBlocks, block, x, y, z, tIcons[1], false);
                renderNegativeZFacing(world, renderBlocks, block, x, y, z, tIcons[2], false);
                renderPositiveZFacing(world, renderBlocks, block, x, y, z, tIcons[3], false);
                if (!tIsCovered[4]) {
                    renderNegativeXFacing(world, renderBlocks, block, x, y, z, tIcons[4], false);
                }
            }
            if ((connections & 0x2) == 0) {
                block.setBlockBounds(sp, sp, sp, sp + tThickness, sp + tThickness, sp + tThickness);
                renderBlocks.setRenderBoundsFromBlock(block);
                renderPositiveXFacing(world, renderBlocks, block, x, y, z, tIcons[5], false);
            } else {
                block.setBlockBounds(sp + tThickness, sp, sp, 1.0F, sp + tThickness, sp + tThickness);
                renderBlocks.setRenderBoundsFromBlock(block);
                renderNegativeYFacing(world, renderBlocks, block, x, y, z, tIcons[0], false);
                renderPositiveYFacing(world, renderBlocks, block, x, y, z, tIcons[1], false);
                renderNegativeZFacing(world, renderBlocks, block, x, y, z, tIcons[2], false);
                renderPositiveZFacing(world, renderBlocks, block, x, y, z, tIcons[3], false);
                if (!tIsCovered[5]) {
                    renderPositiveXFacing(world, renderBlocks, block, x, y, z, tIcons[5], false);
                }
            }
            if ((connections & 0x4) == 0) {
                block.setBlockBounds(sp, sp, sp, sp + tThickness, sp + tThickness, sp + tThickness);
                renderBlocks.setRenderBoundsFromBlock(block);
                renderNegativeYFacing(world, renderBlocks, block, x, y, z, tIcons[0], false);
            } else {
                block.setBlockBounds(sp, 0.0F, sp, sp + tThickness, sp, sp + tThickness);
                renderBlocks.setRenderBoundsFromBlock(block);
                renderNegativeZFacing(world, renderBlocks, block, x, y, z, tIcons[2], false);
                renderPositiveZFacing(world, renderBlocks, block, x, y, z, tIcons[3], false);
                renderNegativeXFacing(world, renderBlocks, block, x, y, z, tIcons[4], false);
                renderPositiveXFacing(world, renderBlocks, block, x, y, z, tIcons[5], false);
                if (!tIsCovered[0]) {
                    renderNegativeYFacing(world, renderBlocks, block, x, y, z, tIcons[0], false);
                }
            }
            if ((connections & 0x8) == 0) {
                block.setBlockBounds(sp, sp, sp, sp + tThickness, sp + tThickness, sp + tThickness);
                renderBlocks.setRenderBoundsFromBlock(block);
                renderPositiveYFacing(world, renderBlocks, block, x, y, z, tIcons[1], false);
            } else {
                block.setBlockBounds(sp, sp + tThickness, sp, sp + tThickness, 1.0F, sp + tThickness);
                renderBlocks.setRenderBoundsFromBlock(block);
                renderNegativeZFacing(world, renderBlocks, block, x, y, z, tIcons[2], false);
                renderPositiveZFacing(world, renderBlocks, block, x, y, z, tIcons[3], false);
                renderNegativeXFacing(world, renderBlocks, block, x, y, z, tIcons[4], false);
                renderPositiveXFacing(world, renderBlocks, block, x, y, z, tIcons[5], false);
                if (!tIsCovered[1]) {
                    renderPositiveYFacing(world, renderBlocks, block, x, y, z, tIcons[1], false);
                }
            }
            if ((connections & 0x10) == 0) {
                block.setBlockBounds(sp, sp, sp, sp + tThickness, sp + tThickness, sp + tThickness);
                renderBlocks.setRenderBoundsFromBlock(block);
                renderNegativeZFacing(world, renderBlocks, block, x, y, z, tIcons[2], false);
            } else {
                block.setBlockBounds(sp, sp, 0.0F, sp + tThickness, sp + tThickness, sp);
                renderBlocks.setRenderBoundsFromBlock(block);
                renderNegativeYFacing(world, renderBlocks, block, x, y, z, tIcons[0], false);
                renderPositiveYFacing(world, renderBlocks, block, x, y, z, tIcons[1], false);
                renderNegativeXFacing(world, renderBlocks, block, x, y, z, tIcons[4], false);
                renderPositiveXFacing(world, renderBlocks, block, x, y, z, tIcons[5], false);
                if (!tIsCovered[2]) {
                    renderNegativeZFacing(world, renderBlocks, block, x, y, z, tIcons[2], false);
                }
            }
            if ((connections & 0x20) == 0) {
                block.setBlockBounds(sp, sp, sp, sp + tThickness, sp + tThickness, sp + tThickness);
                renderBlocks.setRenderBoundsFromBlock(block);
                renderPositiveZFacing(world, renderBlocks, block, x, y, z, tIcons[3], false);
            } else {
                block.setBlockBounds(sp, sp, sp + tThickness, sp + tThickness, sp + tThickness, 1.0F);
                renderBlocks.setRenderBoundsFromBlock(block);
                renderNegativeYFacing(world, renderBlocks, block, x, y, z, tIcons[0], false);
                renderPositiveYFacing(world, renderBlocks, block, x, y, z, tIcons[1], false);
                renderNegativeXFacing(world, renderBlocks, block, x, y, z, tIcons[4], false);
                renderPositiveXFacing(world, renderBlocks, block, x, y, z, tIcons[5], false);
                if (!tIsCovered[3]) {
                    renderPositiveZFacing(world, renderBlocks, block, x, y, z, tIcons[3], false);
                }
            }
        }
        if (tIsCovered[0]) {
            block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
            renderBlocks.setRenderBoundsFromBlock(block);
            renderNegativeYFacing(world, renderBlocks, block, x, y, z, tCovers[0], false);
            renderPositiveYFacing(world, renderBlocks, block, x, y, z, tCovers[0], false);
            if (!tIsCovered[2]) {
                renderNegativeZFacing(world, renderBlocks, block, x, y, z, tCovers[0], false);
            }
            if (!tIsCovered[3]) {
                renderPositiveZFacing(world, renderBlocks, block, x, y, z, tCovers[0], false);
            }
            if (!tIsCovered[4]) {
                renderNegativeXFacing(world, renderBlocks, block, x, y, z, tCovers[0], false);
            }
            if (!tIsCovered[5]) {
                renderPositiveXFacing(world, renderBlocks, block, x, y, z, tCovers[0], false);
            }
        }
        if (tIsCovered[1]) {
            block.setBlockBounds(0.0F, 0.875F, 0.0F, 1.0F, 1.0F, 1.0F);
            renderBlocks.setRenderBoundsFromBlock(block);
            renderNegativeYFacing(world, renderBlocks, block, x, y, z, tCovers[1], false);
            renderPositiveYFacing(world, renderBlocks, block, x, y, z, tCovers[1], false);
            if (!tIsCovered[2]) {
                renderNegativeZFacing(world, renderBlocks, block, x, y, z, tCovers[1], false);
            }
            if (!tIsCovered[3]) {
                renderPositiveZFacing(world, renderBlocks, block, x, y, z, tCovers[1], false);
            }
            if (!tIsCovered[4]) {
                renderNegativeXFacing(world, renderBlocks, block, x, y, z, tCovers[1], false);
            }
            if (!tIsCovered[5]) {
                renderPositiveXFacing(world, renderBlocks, block, x, y, z, tCovers[1], false);
            }
        }
        if (tIsCovered[2]) {
            block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.125F);
            renderBlocks.setRenderBoundsFromBlock(block);
            if (!tIsCovered[0]) {
                renderNegativeYFacing(world, renderBlocks, block, x, y, z, tCovers[2], false);
            }
            if (!tIsCovered[1]) {
                renderPositiveYFacing(world, renderBlocks, block, x, y, z, tCovers[2], false);
            }
            renderNegativeZFacing(world, renderBlocks, block, x, y, z, tCovers[2], false);
            renderPositiveZFacing(world, renderBlocks, block, x, y, z, tCovers[2], false);
            if (!tIsCovered[4]) {
                renderNegativeXFacing(world, renderBlocks, block, x, y, z, tCovers[2], false);
            }
            if (!tIsCovered[5]) {
                renderPositiveXFacing(world, renderBlocks, block, x, y, z, tCovers[2], false);
            }
        }
        if (tIsCovered[3]) {
            block.setBlockBounds(0.0F, 0.0F, 0.875F, 1.0F, 1.0F, 1.0F);
            renderBlocks.setRenderBoundsFromBlock(block);
            if (!tIsCovered[0]) {
                renderNegativeYFacing(world, renderBlocks, block, x, y, z, tCovers[3], false);
            }
            if (!tIsCovered[1]) {
                renderPositiveYFacing(world, renderBlocks, block, x, y, z, tCovers[3], false);
            }
            renderNegativeZFacing(world, renderBlocks, block, x, y, z, tCovers[3], false);
            renderPositiveZFacing(world, renderBlocks, block, x, y, z, tCovers[3], false);
            if (!tIsCovered[4]) {
                renderNegativeXFacing(world, renderBlocks, block, x, y, z, tCovers[3], false);
            }
            if (!tIsCovered[5]) {
                renderPositiveXFacing(world, renderBlocks, block, x, y, z, tCovers[3], false);
            }
        }
        if (tIsCovered[4]) {
            block.setBlockBounds(0.0F, 0.0F, 0.0F, 0.125F, 1.0F, 1.0F);
            renderBlocks.setRenderBoundsFromBlock(block);
            if (!tIsCovered[0]) {
                renderNegativeYFacing(world, renderBlocks, block, x, y, z, tCovers[4], false);
            }
            if (!tIsCovered[1]) {
                renderPositiveYFacing(world, renderBlocks, block, x, y, z, tCovers[4], false);
            }
            if (!tIsCovered[2]) {
                renderNegativeZFacing(world, renderBlocks, block, x, y, z, tCovers[4], false);
            }
            if (!tIsCovered[3]) {
                renderPositiveZFacing(world, renderBlocks, block, x, y, z, tCovers[4], false);
            }
            renderNegativeXFacing(world, renderBlocks, block, x, y, z, tCovers[4], false);
            renderPositiveXFacing(world, renderBlocks, block, x, y, z, tCovers[4], false);
        }
        if (tIsCovered[5]) {
            block.setBlockBounds(0.875F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            renderBlocks.setRenderBoundsFromBlock(block);
            if (!tIsCovered[0]) {
                renderNegativeYFacing(world, renderBlocks, block, x, y, z, tCovers[5], false);
            }
            if (!tIsCovered[1]) {
                renderPositiveYFacing(world, renderBlocks, block, x, y, z, tCovers[5], false);
            }
            if (!tIsCovered[2]) {
                renderNegativeZFacing(world, renderBlocks, block, x, y, z, tCovers[5], false);
            }
            if (!tIsCovered[3]) {
                renderPositiveZFacing(world, renderBlocks, block, x, y, z, tCovers[5], false);
            }
            renderNegativeXFacing(world, renderBlocks, block, x, y, z, tCovers[5], false);
            renderPositiveXFacing(world, renderBlocks, block, x, y, z, tCovers[5], false);
        }
        block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        renderBlocks.setRenderBoundsFromBlock(block);
        return true;
    }
}
