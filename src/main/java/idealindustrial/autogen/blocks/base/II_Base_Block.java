package idealindustrial.autogen.blocks.base;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;

public class II_Base_Block extends Block {
    protected final String mUnlocalizedName;

    protected II_Base_Block(Class<? extends ItemBlock> itemClass, String unlocalizedName, Material material) {
        super(material);
        setBlockName(mUnlocalizedName = ("ii." + unlocalizedName));
        GameRegistry.registerBlock(this, itemClass, getUnlocalizedName());
    }

}