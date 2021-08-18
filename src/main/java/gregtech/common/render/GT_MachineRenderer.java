package gregtech.common.render;

import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.GregTech_API;
import gregtech.api.enums.ItemList;
import gregtech.common.blocks.GT_Item_Machines;
import gregtech.common.items.GT_VolumetricFlask;
import ic2.core.util.DrawUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;

@SideOnly(cpw.mods.fml.relauncher.Side.CLIENT)
public final class GT_MachineRenderer implements IItemRenderer {
    public GT_MachineRenderer() {
        MinecraftForgeClient.registerItemRenderer(GT_Item_Machines.INSTANCE, this);
    }

    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return type != ItemRenderType.FIRST_PERSON_MAP;
    }


    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return type == ItemRenderType.EQUIPPED || type == ItemRenderType.INVENTORY || type == ItemRenderType.ENTITY || type == ItemRenderType.EQUIPPED_FIRST_PERSON;
    }

    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {

        if(type == ItemRenderType.INVENTORY){
            GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        }
        else if(type == ItemRenderType.ENTITY) {
            GL11.glTranslatef(-.5f,-.5f,-.5f);
          //  GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
        }
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GT_Renderer_Block.renderNormalInventoryMetaTileEntity(item, GregTech_API.sBlockMachines, item.getItemDamage(), RenderBlocks.getInstance(), type == ItemRenderType.ENTITY);


    }
}