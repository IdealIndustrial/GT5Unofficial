package idealindustrial.render;

import cpw.mods.fml.relauncher.SideOnly;
import idealindustrial.II_Core;
import idealindustrial.II_Values;
import idealindustrial.itemgen.blocks.II_Blocks;
import idealindustrial.tile.II_Item_Machines;
import idealindustrial.tile.interfaces.base.II_BaseTile;
import idealindustrial.tile.interfaces.meta.II_MetaTile;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import org.lwjgl.opengl.GL11;

import static gregtech.common.render.GT_Renderer_Block.renderInventory;

@SideOnly(cpw.mods.fml.relauncher.Side.CLIENT)
public final class II_MachineItemRenderer implements IItemRenderer {
    public II_MachineItemRenderer() {
        MinecraftForgeClient.registerItemRenderer(II_Item_Machines.INSTANCE, this);
    }

    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return type != ItemRenderType.FIRST_PERSON_MAP;
    }


    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return type == ItemRenderType.EQUIPPED || type == ItemRenderType.INVENTORY || type == ItemRenderType.ENTITY || type == ItemRenderType.EQUIPPED_FIRST_PERSON;
    }

    public void renderItem(ItemRenderType type, ItemStack is, Object... data) {

        if (type == ItemRenderType.INVENTORY) {
            GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        } else if (type == ItemRenderType.ENTITY) {
            GL11.glTranslatef(-.5f, -.5f, -.5f);
            //  GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
        }
        GL11.glEnable(GL11.GL_ALPHA_TEST);

        if (is.getItem() instanceof II_Item_Machines) {
            int meta = Items.feather.getDamage(is);
            II_MetaTile tile = II_Values.metaTiles[meta];
            II_BaseTile base = tile == null ? null : tile.getBase();
            if (base == null) {
                return;
            }
            Block block = II_Blocks.INSTANCE.blockMachines;
            block.setBlockBoundsForItemRender();
            RenderBlocks renderer = RenderBlocks.getInstance();
            renderer.setRenderBoundsFromBlock(block);
            if (base.getCustomRenderer() != null) {
                base.getCustomRenderer().renderItem(type, is, II_Blocks.INSTANCE.blockMachines, RenderBlocks.getInstance(), meta);
            } else {
                renderInventory(block, renderer, base.getTextures(is, (byte) 4, true, false, true));
            }
        }

    }
}