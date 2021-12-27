package idealindustrial.impl.autogen.recipes.generators;

import idealindustrial.impl.autogen.material.II_Materials;
import idealindustrial.impl.autogen.material.Prefixes;
import idealindustrial.impl.item.MetaToolItem;
import idealindustrial.impl.oredict.OreDict;
import idealindustrial.util.misc.II_Util;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Arrays;

public class ToolRecipeGenerator implements Runnable{
    @Override
    public void run() {
        Arrays.stream(Prefixes.values()).filter(p -> p.name().startsWith("toolHead")).filter(MetaToolItem.INSTANCE::hasPrefix).forEach(prefix -> {
            II_Materials.stream().filter(material -> OreDict.exists(material, prefix)).filter(material -> material.getAutogenInfo().toolLevel != 0).forEach( material -> {
                II_Util.addCraftingRecipe(MetaToolItem.INSTANCE.getTool(prefix, material),
                        "H","S",
                        'H', OreDict.get(prefix, material, 1).toMCStack(),
                        'S', new ItemStack(Items.stick, 1));//todo May be tired sticks
            });
        });
    }
}
