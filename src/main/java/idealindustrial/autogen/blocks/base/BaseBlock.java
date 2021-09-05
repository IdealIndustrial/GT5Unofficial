package idealindustrial.autogen.blocks.base;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;

public class BaseBlock extends Block {
    protected final String mUnlocalizedName;

    protected BaseBlock(Class<? extends ItemBlock> itemClass, String unlocalizedName, Material material) {
        super(material);
        setBlockName(mUnlocalizedName = ("ii." + unlocalizedName));
        GameRegistry.registerBlock(this, itemClass, getUnlocalizedName());
    }

}