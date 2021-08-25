package idealindustrial.render;

import gregtech.api.interfaces.IIconContainer;
import gregtech.api.util.GT_Utility;
import gregtech.common.render.GT_RenderUtil;
import idealindustrial.autogen.implementation.II_MetaGeneratedCellItem;
import idealindustrial.autogen.items.II_MetaGeneratedItem;
import idealindustrial.autogen.material.submaterial.render.RenderInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class II_MetaGeneratedItem_Renderer
        implements IItemRenderer {
    public II_MetaGeneratedItem_Renderer() {
        for (II_MetaGeneratedItem item : II_MetaGeneratedItem.getInstances()) {
            MinecraftForgeClient.registerItemRenderer(item, this);
        }
    }

    public boolean handleRenderType(ItemStack aStack, ItemRenderType aType) {
        if ((GT_Utility.isStackInvalid(aStack)) || (aStack.getItemDamage() < 0)) {
            return false;
        }
        return (aType == ItemRenderType.EQUIPPED_FIRST_PERSON) || (aType == ItemRenderType.INVENTORY) || (aType == ItemRenderType.EQUIPPED) || (aType == ItemRenderType.ENTITY);
    }

    public boolean shouldUseRenderHelper(ItemRenderType aType, ItemStack aStack, ItemRendererHelper aHelper) {
        if (GT_Utility.isStackInvalid(aStack)) {
            return false;
        }
        return aType == ItemRenderType.ENTITY;
    }

    public void renderItem(ItemRenderType type, ItemStack stack, Object... data) {
        II_MetaGeneratedItem item = (II_MetaGeneratedItem) stack.getItem();
        int damage = Items.feather.getDamage(stack); // as AlgorithmX2 said "hackery!!"
        RenderInfo renderInfo = item.getRenderInfo(damage);
        IIconContainer container = renderInfo.getTextureSet().mTextures[item.prefix(damage).textureIndex];
        GL11.glEnable(3042);
        if (type == ItemRenderType.ENTITY) {
            if (RenderItem.renderInFrame) {
                GL11.glScalef(0.85F, 0.85F, 0.85F);
                GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
                GL11.glTranslated(-0.5D, -0.42D, 0.0D);
            } else {
                GL11.glTranslated(-0.5D, -0.42D, 0.0D);
            }
        }
        if (item instanceof II_MetaGeneratedCellItem) {
            renderFluid(type, stack, container, renderInfo);
            return;
        }

        renderBackSide(type, renderInfo, container);
        renderOverlay(type, container);
        GL11.glDisable(3042);
    }

    private void renderOverlay(ItemRenderType type, IIconContainer container) {
        IIcon overlay;
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        overlay = container.getOverlayIcon();
        if (overlay != null) {
            doRender(type, overlay);
        }
    }

    private void renderBackSide(ItemRenderType type, RenderInfo renderInfo, IIconContainer container) {
        IIcon icon = container.getIcon();
        setColor(renderInfo);
        doRender(type, icon);
    }

    private void doRender(ItemRenderType type, IIcon icon) {
        Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationItemsTexture);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        if (type.equals(ItemRenderType.INVENTORY)) {
            GT_RenderUtil.renderItemIcon(icon, 16.0D, 0.001D, 0.0F, 0.0F, -1.0F);
        } else {
            ItemRenderer.renderItemIn2D(Tessellator.instance, icon.getMaxU(), icon.getMinV(), icon.getMinU(), icon.getMaxV(), icon.getIconWidth(), icon.getIconHeight(), 0.0625F);
        }
    }

    private void renderFluid(ItemRenderType type, ItemStack stack, IIconContainer cellIcon, RenderInfo cellRenderInfo) {
        FluidStack fluid = GT_Utility.getFluidForFilledItem(stack, true);
        if (cellIcon.getOverlayIcon() == null) {
            return;
        }
        IIcon fluidIcon = null;
        if ((fluid != null) && (fluid.getFluid() != null)) {
            fluidIcon = fluid.getFluid().getIcon(fluid);
        }
        if (fluidIcon == null) {
            return;
        }
        Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationItemsTexture);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);


        renderBackSide(type, cellRenderInfo, cellIcon);
        int tColor = fluid.getFluid().getColor(fluid);
        GL11.glColor3f((tColor >> 16 & 0xFF) / 255.0F, (tColor >> 8 & 0xFF) / 255.0F, (tColor & 0xFF) / 255.0F);

        Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDepthFunc(GL11.GL_EQUAL);
        if (type.equals(ItemRenderType.INVENTORY)) {
            GT_RenderUtil.renderItemIcon(fluidIcon, 16.0D, 0.001D, 0.0F, 0.0F, -1.0F);
        } else {
            ItemRenderer.renderItemIn2D(Tessellator.instance, fluidIcon.getMaxU(), fluidIcon.getMinV(), fluidIcon.getMinU(), fluidIcon.getMaxV(), fluidIcon.getIconWidth(), fluidIcon.getIconHeight(), 0.0625F);
        }
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        renderOverlay(type, cellIcon);
    }

    private void setColor(RenderInfo renderInfo) {
        Color color = renderInfo.getColor();
        GL11.glColor3f(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F);
    }
}
