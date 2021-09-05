package idealindustrial.autogen.blocks.base;

import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;

public class MetaBlock extends BaseBlock {
    private int enabledBlocks;

    protected MetaBlock(Class<? extends ItemBlock> itemClass, String unlocalizedName, Material material) {
        super(itemClass, unlocalizedName, material);
    }


    public boolean isEnabled(int i) {
        return (enabledBlocks & (1 << i)) != 0;
    }

    protected void enable(int i) {
        enabledBlocks |= 1 << i;
    }

}
