package idealindustrial.api.textures;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.util.IIcon;

public interface ITexture {
    void renderXPos(RenderBlocks renderer, Block block, int x, int y, int z);
    void renderXNeg(RenderBlocks renderer, Block block, int x, int y, int z);
    void renderYPos(RenderBlocks renderer, Block block, int x, int y, int z);
    void renderYNeg(RenderBlocks renderer, Block block, int x, int y, int z);
    void renderZPos(RenderBlocks renderer, Block block, int x, int y, int z);
    void renderZNeg(RenderBlocks renderer, Block block, int x, int y, int z);
    boolean isValidTexture();

    IIcon getIcon();
}