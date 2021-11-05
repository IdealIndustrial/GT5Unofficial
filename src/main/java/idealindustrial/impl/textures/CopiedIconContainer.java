package idealindustrial.impl.textures;

import idealindustrial.api.textures.IconContainer;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

public class CopiedIconContainer implements IconContainer {

    Block block;
    int side, meta;

    public CopiedIconContainer(Block block, int side, int meta) {
        this.block = block;
        this.side = side;
        this.meta = meta;
    }



    @Override
    public IIcon getIcon() {
        return block.getIcon(side, meta);
    }


    @Override
    public ResourceLocation getFile() {
        return TextureMap.locationBlocksTexture;
    }
}
