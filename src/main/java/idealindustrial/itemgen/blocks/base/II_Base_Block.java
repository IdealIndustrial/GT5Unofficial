package idealindustrial.itemgen.blocks.base;

import cpw.mods.fml.common.registry.GameRegistry;
import gregtech.api.util.GT_LanguageManager;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemBlock;

import static gregtech.api.enums.GT_Values.W;

public class II_Base_Block extends Block {
    protected final String mUnlocalizedName;

    protected II_Base_Block(Class<? extends ItemBlock> itemClass, String unlocalizedName, Material material) {
        super(material);
        setBlockName(mUnlocalizedName = ("ii." + unlocalizedName));
        GameRegistry.registerBlock(this, itemClass, getUnlocalizedName());
    }

}