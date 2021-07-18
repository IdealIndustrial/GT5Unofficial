package idealindustrial.itemgen.blocks;

import gregtech.api.enums.Materials;
import gregtech.common.blocks.GT_Block_Metal;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class II_MetaBlock_Item extends ItemBlock {
    public II_MetaBlock_Item(Block block) {
        super(block);
        setHasSubtypes(true);
    }

    public String getUnlocalizedName(ItemStack aStack) {
        return this.field_150939_a.getUnlocalizedName() + "." + getDamage(aStack);
    }
}
