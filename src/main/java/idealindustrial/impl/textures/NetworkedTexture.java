package idealindustrial.impl.textures;

import idealindustrial.api.textures.INetworkedTexture;
import net.minecraft.block.Block;

public class NetworkedTexture extends RenderedTexture implements INetworkedTexture {
    //all positive for custom, all negative for non-registered
    //if neg: 1,0x8, 3 bit for side, 4 bit for meta, 16 bit for block id
    private int id;

    public NetworkedTexture(Block block, int meta, int side) {
        super(new CopiedIconContainer(block, side, meta));
        id = Block.getIdFromBlock(block) | (meta << 16) | (side << 20);
        id = -id;
    }


    public static NetworkedTexture load(int id) {
        id = -id;
        int blockId = id & 0xFFFF;
        int meta = (id >> 16) & 0xF;
        int side = (id >> 20) & 0b111;
        Block b = Block.getBlockById(blockId);
        return new NetworkedTexture(b, meta, side);
    }

    @Override
    public int getID() {
        return id;
    }
}
