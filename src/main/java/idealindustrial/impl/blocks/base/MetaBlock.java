package idealindustrial.impl.blocks.base;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import idealindustrial.api.reflection.II_EventListener;
import idealindustrial.util.lang.LangHandler;
import idealindustrial.util.lang.LocalizeEvent;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntConsumer;

@II_EventListener
public class MetaBlock extends BaseBlock {
    private int enabledBlocks;
    protected String[] locale;
    private static List<MetaBlock> instancesToLocalize = new ArrayList<>();

    protected MetaBlock(Class<? extends MetaBlock_Item> itemClass, String unlocalizedName, Material material) {
        super(itemClass, unlocalizedName, material);
    }

    protected MetaBlock(Class<? extends MetaBlock_Item> itemClass, String unlocalizedName, Material material, String... localNames) {
        super(itemClass, unlocalizedName, material);
        locale = localNames;
        instancesToLocalize.add(this);
    }

    @LocalizeEvent
    public static void localize() {
        instancesToLocalize.forEach(block ->
                block.foreachEnabled(meta ->
                        LangHandler.add(MetaBlock_Item.getUnlocalizedName(block.getUnlocalizedName(), meta), block.locale[meta])
                )
        );
    }


    public boolean isEnabled(int i) {
        return (enabledBlocks & (1 << i)) != 0;
    }

    protected void enable(int i) {
        enabledBlocks |= 1 << i;
    }

    public void foreachEnabled(IntConsumer consumer) {
        for (int i = 0; i < 16; i++) {
            if (isEnabled(i)) {
                consumer.accept(i);
            }
        }
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
