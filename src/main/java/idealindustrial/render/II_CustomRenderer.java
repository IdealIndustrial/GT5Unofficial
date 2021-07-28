package idealindustrial.render;

import gregtech.api.interfaces.IFastRenderedTileEntity;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

public interface II_CustomRenderer {

    boolean renderWorldBlock(IBlockAccess world, IFastRenderedTileEntity tileEntity, int x, int y, int z, Block block, RenderBlocks renderBlocks);
}
