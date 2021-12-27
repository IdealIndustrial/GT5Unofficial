package idealindustrial.impl.render;

import idealindustrial.api.items.II_ItemRenderer;
import idealindustrial.impl.item.MetaBehaviorItem;
import idealindustrial.impl.item.MetaGeneratedCellItem;
import idealindustrial.impl.item.MetaGeneratedItem;
import idealindustrial.impl.autogen.material.submaterial.MatterState;
import idealindustrial.impl.autogen.material.submaterial.render.RenderInfo;
import idealindustrial.api.textures.IconContainer;
import idealindustrial.impl.item.MetaItem32k;
import idealindustrial.impl.textures.OverlayIconContainer;
import idealindustrial.impl.tile.fluid.II_FluidHelper;
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

public class MetaItem_Renderer
        implements IItemRenderer {
    public MetaItem_Renderer() {
        for (MetaGeneratedItem item : MetaGeneratedItem.getInstances()) {
            MinecraftForgeClient.registerItemRenderer(item, this);
        }
        for (MetaBehaviorItem item : MetaBehaviorItem.getInstances()) {
            MinecraftForgeClient.registerItemRenderer(item, this);
        }
    }

    public boolean handleRenderType(ItemStack aStack, ItemRenderType aType) {
        if (aStack == null || aStack.getItem() == null || (aStack.getItemDamage() < 0)) {
            return false;
        }
        return (aType == ItemRenderType.EQUIPPED_FIRST_PERSON) || (aType == ItemRenderType.INVENTORY) || (aType == ItemRenderType.EQUIPPED) || (aType == ItemRenderType.ENTITY);
    }

    public boolean shouldUseRenderHelper(ItemRenderType aType, ItemStack aStack, ItemRendererHelper aHelper) {
        if (aStack == null || aStack.getItem() == null) {
            return false;
        }
        return aType == ItemRenderType.ENTITY;
    }

    public void renderItem(ItemRenderType type, ItemStack stack, Object... data) {
        GL11.glEnable(3042);
        if (type == ItemRenderType.ENTITY) {
            if (RenderItem.renderInFrame) {
                GL11.glScalef(0.85F, 0.85F, 0.85F);
                GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
            }
            GL11.glTranslated(-0.5D, -0.42D, 0.0D);
        }

        if (stack.getItem() instanceof MetaGeneratedItem) {
            MetaGeneratedItem item = (MetaGeneratedItem) stack.getItem();
            int damage = Items.feather.getDamage(stack); // as AlgorithmX2 said "hackery!!"
            RenderInfo renderInfo = item.getRenderInfo(damage);
            IconContainer container = renderInfo.getTextureSet().forPrefix(item.prefix(damage));

            if (item instanceof MetaGeneratedCellItem) {
                renderFluid(type, stack, container, renderInfo);
                GL11.glDisable(3042);
                return;
            }

            renderBackSide(type, renderInfo, container, MatterState.Solid);
            renderOverlay(type, container);
        }
        else if (stack.getItem() instanceof MetaBehaviorItem) {
            MetaBehaviorItem item = (MetaBehaviorItem) stack.getItem();
            int damage = stack.getItemDamage();
            if (item.getBehavior(damage) == null || !item.getBehavior(damage).renderItem(type, stack, data)) {
                doRender(type, item.getIconFromDamage(damage));
            }
        }
        GL11.glDisable(3042);
    }

    private void renderOverlay(ItemRenderType type, IconContainer container) {
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        if (container instanceof OverlayIconContainer) {
            doRender(type, ((OverlayIconContainer) container).getOverlay());
        }
    }

    public static void renderBackSide(ItemRenderType type, RenderInfo renderInfo, IconContainer container, MatterState state) {
        if (container != null) {
            IIcon icon = container.getIcon();
            setColor(renderInfo, state);
            doRender(type, icon);
        }
    }

    public static void doRender(ItemRenderType type, IIcon icon) {
        Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationItemsTexture);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        if (type.equals(ItemRenderType.INVENTORY)) {
            renderItemIcon(icon, 16.0D, 0.001D, 0.0F, 0.0F, -1.0F);
        } else {
            ItemRenderer.renderItemIn2D(Tessellator.instance, icon.getMaxU(), icon.getMinV(), icon.getMinU(), icon.getMaxV(), icon.getIconWidth(), icon.getIconHeight(), 0.0625F);
        }
    }

    private void renderFluid(ItemRenderType type, ItemStack stack, IconContainer cellIcon, RenderInfo cellRenderInfo) {
        FluidStack fluid = II_FluidHelper.getFluidForContainer(stack);
        if (cellIcon == null) {
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
        GL11.glEnable(GL11.GL_ALPHA_TEST);

        renderBackSide(type, cellRenderInfo, cellIcon, MatterState.Liquid);
        int tColor = fluid.getFluid().getColor(fluid);
        GL11.glColor3f((tColor >> 16 & 0xFF) / 255.0F, (tColor >> 8 & 0xFF) / 255.0F, (tColor & 0xFF) / 255.0F);

        Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDepthFunc(GL11.GL_EQUAL);
        if (type.equals(ItemRenderType.INVENTORY)) {
           renderItemIcon(fluidIcon, 16.0D, 0.001D, 0.0F, 0.0F, -1.0F);
        } else {
            ItemRenderer.renderItemIn2D(Tessellator.instance, fluidIcon.getMaxU(), fluidIcon.getMinV(), fluidIcon.getMinU(), fluidIcon.getMaxV(), fluidIcon.getIconWidth(), fluidIcon.getIconHeight(), 0.0625F);
        }
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        renderOverlay(type, cellIcon);
    }

    public static void setColor(RenderInfo renderInfo, MatterState state) {
        Color color = renderInfo.getColor(state);
        GL11.glColor3f(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F);
    }

    public static void renderItemIcon(IIcon icon, double size, double z, float nx, float ny, float nz) {
        renderItem(icon, 0.0D, 0.0D, size, size, z, nx, ny, nz);
    }

    public static void renderItem(IIcon icon, double xStart, double yStart, double xEnd, double yEnd, double z, float nx, float ny, float nz) {
        if (icon == null) {
            return;
        }
        Tessellator.instance.startDrawingQuads();
        Tessellator.instance.setNormal(nx, ny, nz);
        if (nz > 0.0F) {
            Tessellator.instance.addVertexWithUV(xStart, yStart, z, icon.getMinU(), icon.getMinV());
            Tessellator.instance.addVertexWithUV(xEnd, yStart, z, icon.getMaxU(), icon.getMinV());
            Tessellator.instance.addVertexWithUV(xEnd, yEnd, z, icon.getMaxU(), icon.getMaxV());
            Tessellator.instance.addVertexWithUV(xStart, yEnd, z, icon.getMinU(), icon.getMaxV());
        } else {
            Tessellator.instance.addVertexWithUV(xStart, yEnd, z, icon.getMinU(), icon.getMaxV());
            Tessellator.instance.addVertexWithUV(xEnd, yEnd, z, icon.getMaxU(), icon.getMaxV());
            Tessellator.instance.addVertexWithUV(xEnd, yStart, z, icon.getMaxU(), icon.getMinV());
            Tessellator.instance.addVertexWithUV(xStart, yStart, z, icon.getMinU(), icon.getMinV());
        }
        Tessellator.instance.draw();
    }
}
