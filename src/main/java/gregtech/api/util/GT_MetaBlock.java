package gregtech.api.util;

import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.block.Block;

/**
 *
 * @author Wise
 */
public class GT_MetaBlock {

    private final int metaId;
    private Block block;
    private IGregTechTileEntity tile;

    public GT_MetaBlock(IGregTechTileEntity tile) {
        this.tile = tile;
        this.metaId = -1;
    }

    public GT_MetaBlock(int metaId, Block block) {
        this.metaId = metaId;
        this.block = block;
    }

    public int getMetaId() {
        return metaId;
    }

    public Block getBlock() {
        return block;
    }

    public IGregTechTileEntity getTile() {
        return tile;
    }

    public boolean isTile() {
        return tile != null;
    }
    
    public boolean hasMeta(){
        return metaId != -1;
    }
}
