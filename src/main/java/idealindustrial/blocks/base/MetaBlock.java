package idealindustrial.blocks.base;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.List;

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

    @SideOnly(Side.CLIENT)
    @SuppressWarnings("unchecked")
    public void getSubBlocks(Item item, CreativeTabs par2CreativeTabs, List list) {
        for (int i = 0; i < 16; i++) {
            if (isEnabled(i)) {
                list.add(new ItemStack(item, 1, i));
            }
        }
    }
}
