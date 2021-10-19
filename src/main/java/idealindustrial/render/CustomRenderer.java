package idealindustrial.render;

import idealindustrial.tile.host.HostPipeTileRotatingImpl;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.IItemRenderer;

public interface CustomRenderer {

    boolean renderWorldBlock(IBlockAccess world, IFastRenderedTileEntity tileEntity, int x, int y, int z, Block block, RenderBlocks renderBlocks);

    public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Block block, RenderBlocks renderBlocks, int meta);

    default boolean shouldRender(IFastRenderedTileEntity tileEntity) {
        return !(tileEntity instanceof HostPipeTileRotatingImpl);
    }
}
