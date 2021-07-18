package idealindustrial.itemgen.blocks.base;

import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;

public class II_MetaBlock extends II_Base_Block {
    private int enabledBlocks;

    protected II_MetaBlock(Class<? extends ItemBlock> itemClass, String unlocalizedName, Material material) {
        super(itemClass, unlocalizedName, material);
    }


    public boolean isEnabled(int i) {
        return (enabledBlocks & (1 << i)) != 0;
    }

    protected void enable(int i) {
        enabledBlocks |= 1 << i;
    }

}
