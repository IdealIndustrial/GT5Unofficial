package idealindustrial.impl.render;

import idealindustrial.api.textures.ITexture;
import idealindustrial.api.textures.IconContainer;
import idealindustrial.impl.autogen.material.II_Material;
import idealindustrial.impl.autogen.material.II_Materials;
import idealindustrial.impl.autogen.material.Prefixes;
import idealindustrial.impl.autogen.material.submaterial.MatterState;
import idealindustrial.impl.item.MetaToolItem;
import idealindustrial.impl.textures.OverlayIconContainer;
import idealindustrial.impl.textures.TextureManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class MetaToolRenderer implements IItemRenderer {

    static IconContainer durability = TextureManager.INSTANCE.itemTexture("misc/durability");

    public MetaToolRenderer() {
        MinecraftForgeClient.registerItemRenderer(MetaToolItem.INSTANCE, this);
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return (type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON) || (type == IItemRenderer.ItemRenderType.INVENTORY) || (type == IItemRenderer.ItemRenderType.EQUIPPED) || (type == IItemRenderer.ItemRenderType.ENTITY);
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return type == IItemRenderer.ItemRenderType.ENTITY;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        if (!(item.getItem() instanceof MetaToolItem)) {
            return;
        }
        MetaToolItem metaTool = (MetaToolItem) item.getItem();
        int meta = item.getItemDamage();
        if (!metaTool.isEnabled(meta)) {
            return;
        }

        GL11.glEnable(GL11.GL_BLEND);

        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        if (type == IItemRenderer.ItemRenderType.ENTITY) {
            if (RenderItem.renderInFrame) {
                GL11.glScalef(0.85F, 0.85F, 0.85F);
                GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
            }
            GL11.glTranslated(-0.5D, -0.42D, 0.0D);
        }
        GL11.glColor3f(1.0F, 1.0F, 1.0F);


        II_Material material = metaTool.getMaterial(item);
        Prefixes prefix = metaTool.getToolHeadPrefix(meta);
        material = material == null ? II_Materials.iron : material;
        OverlayIconContainer baseIcon = metaTool.getBaseIcon(meta);
        OverlayIconContainer headIcon = (OverlayIconContainer) material.getRenderInfo().getTextureSet().forPrefix(prefix);
        Color headColor = material.getRenderInfo().getColor(MatterState.Solid);
        Color baseColor = metaTool.getHandleColor(meta);

        Minecraft.getMinecraft().renderEngine.bindTexture(baseIcon.getFile());
        doRender(baseIcon.getIcon(), type, baseColor);
        doRender(headIcon.getIcon(), type, headColor);
        doRender(headIcon.getOverlay(), type, Color.WHITE);
        doRender(baseIcon.getOverlay(), type, Color.WHITE);
        GL11.glDisable(GL11.GL_BLEND);
        if (type == ItemRenderType.INVENTORY) {
            double damageFactor = 1 - MetaToolItem.INSTANCE.getDamageD(item);
            damageFactor = Math.max(0, damageFactor);
            MetaItem_Renderer.renderItem(durability.getIcon(), 0d, 0d, 16d * damageFactor, 16.0d, 0.02, 0.0f, 0.0f, -1.0F);
        }

    }

    public static void doRender(IIcon icon, ItemRenderType type, Color color) {
        GL11.glColor3f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f);
        if (type == ItemRenderType.INVENTORY) {
            renderItemIcon(icon);
        } else {
            ItemRenderer.renderItemIn2D(Tessellator.instance, icon.getMaxU(), icon.getMinV(), icon.getMinU(), icon.getMaxV(), icon.getIconWidth(), icon.getIconHeight(), 0.0625F);
        }
    }

    private static void renderItemIcon(IIcon icon) {
        renderItemIcon(icon, 16.0D, 0.001D, 0.0F, 0.0F, -1.0F);
    }


    public static void renderItemIcon(IIcon icon, double size, double z, float nx, float ny, float nz) {
        MetaItem_Renderer.renderItem(icon, 0.0D, 0.0D, size, size, z, nx, ny, nz);
    }

}
