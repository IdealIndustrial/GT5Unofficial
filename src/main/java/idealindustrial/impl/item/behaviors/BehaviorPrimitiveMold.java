package idealindustrial.impl.item.behaviors;

import idealindustrial.api.items.IItemBehavior;
import idealindustrial.api.textures.IconContainer;
import idealindustrial.impl.autogen.material.II_Material;
import idealindustrial.impl.autogen.material.II_Materials;
import idealindustrial.impl.autogen.material.Prefixes;
import idealindustrial.impl.autogen.material.submaterial.MatterState;
import idealindustrial.impl.item.stack.HashedStack;
import idealindustrial.impl.oredict.OreDict;
import idealindustrial.impl.render.MetaItem_Renderer;
import idealindustrial.impl.render.MetaToolRenderer;
import idealindustrial.impl.textures.TextureManager;
import idealindustrial.util.misc.II_StreamUtil;
import idealindustrial.util.nbt.NBTField;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static idealindustrial.impl.autogen.material.II_Materials.*;

public class BehaviorPrimitiveMold implements IItemBehavior {
    static Set<II_Material> enabledMaterials = II_StreamUtil.set(iron, copper, tin);
    static Prefixes[] prefixes = new Prefixes[]{Prefixes.ingot, Prefixes.plate};
    static String texturePath = "meta/item1/";
    static IconContainer[] icons = Arrays.stream(prefixes)
            .map(p -> texturePath + "mold_" + p.name())
            .map(TextureManager.INSTANCE::itemTexture)
            .toArray(IconContainer[]::new);
    static IconContainer[] iconOverlays = Arrays.stream(prefixes)
            .map(p -> texturePath + "mold_" + p.name() + "_o")
            .map(TextureManager.INSTANCE::itemTexture)
            .toArray(IconContainer[]::new);
    static IconContainer[] baseIcons = Arrays.stream(prefixes)
            .map(p -> texturePath + "mold_" + p.name() + "_b")
            .map(TextureManager.INSTANCE::itemTexture)
            .toArray(IconContainer[]::new);

    NBTField<II_Material> materialField = new NBTField<>(null,
            (nbt, mat) -> nbt.setInteger("mID", mat.getID()),
            nbt  -> II_Materials.materialForID(nbt.getInteger("mID")),
            "mID");

    Prefixes prefix;
    int prefixID;

    public BehaviorPrimitiveMold(Prefixes prefix) {
        this.prefix = prefix;
        prefixID = II_StreamUtil.indexOf(prefixes, prefix);
        assert prefixID != -1;
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (world.isRemote) {
            return false;
        }
        II_Material material = materialField.get(stack);
        if (material == null) {
            return false;
        }
        else if (player.isSneaking()) {
            HashedStack hs = OreDict.get(prefix, material).getMain();
            ItemStack drop = hs.toItemStack(stack.stackSize);
            player.entityDropItem(drop, 0.5f).delayBeforeCanPickup = 0;
            materialField.set(stack, null);
        }
        return true;
    }

    @Override
    public boolean loadIcon() {
        return false;
    }

    @Override
    public boolean addSubItems(Item item, int damage, CreativeTabs tab, List<ItemStack> list) {
        list.add(new ItemStack(item, 1, damage));
        for (II_Material material : enabledMaterials) {
            ItemStack out = new ItemStack(item, 1, damage);
            materialField.set(out, material);
            list.add(out);
        }
        return true;
    }

    @Override
    public boolean renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object... data) {
        MetaItem_Renderer.doRender(type, icons[prefixID].getIcon());
        II_Material material = materialField.get(item);
        if (material != null) {
            GL11.glPushMatrix();
            IconContainer overlay = material.getRenderInfo().getTextureSet().forPrefix(prefix);
//            MetaItem_Renderer.renderBackSide(type, material.getRenderInfo(), overlay, MatterState.Solid);
            MetaToolRenderer.doRender(iconOverlays[prefixID].getIcon(), type, material.getRenderInfo().getColor(MatterState.Solid));
            GL11.glPopMatrix();
        }
        else {
            MetaItem_Renderer.doRender(type, baseIcons[prefixID].getIcon());
        }
        return true;
    }
}
