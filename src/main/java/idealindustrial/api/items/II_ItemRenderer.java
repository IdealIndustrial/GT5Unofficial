package idealindustrial.api.items;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;

public interface II_ItemRenderer {

    void renderItem(ItemRenderType type, ItemStack item, Object... data);
}
