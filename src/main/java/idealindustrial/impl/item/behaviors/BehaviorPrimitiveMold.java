package idealindustrial.impl.item.behaviors;

import idealindustrial.api.items.IItemBehavior;
import idealindustrial.api.reflection.II_EventListener;
import idealindustrial.api.textures.IconContainer;
import idealindustrial.impl.autogen.material.II_Material;
import idealindustrial.impl.autogen.material.II_Materials;
import idealindustrial.impl.autogen.material.Prefixes;
import idealindustrial.impl.autogen.material.submaterial.MatterState;
import idealindustrial.impl.item.stack.HashedStack;
import idealindustrial.impl.item.stack.II_ItemStack;
import idealindustrial.impl.oredict.OreDict;
import idealindustrial.impl.render.MetaItem_Renderer;
import idealindustrial.impl.render.MetaToolRenderer;
import idealindustrial.impl.textures.TextureManager;
import idealindustrial.util.lang.LocalizeEvent;
import idealindustrial.util.lang.materials.EngLocalizer;
import idealindustrial.util.lang.materials.MaterialLocalizer;
import idealindustrial.util.misc.II_StreamUtil;
import idealindustrial.util.nbt.NBTField;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

import java.util.*;

import static idealindustrial.impl.autogen.material.II_Materials.*;

@II_EventListener
public class BehaviorPrimitiveMold implements IItemBehavior {
    public static Map<Prefixes, ItemStack> prefixToMoldMap = new HashMap<>();

    public static Set<II_Material> enabledMaterials = II_StreamUtil.set(iron, copper, tin, bronze);
    public static Prefixes[] prefixes = new Prefixes[]{Prefixes.ingot, Prefixes.plate, Prefixes.toolHeadPickaxe};
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

    static NBTField<II_Material> materialField = new NBTField<>(null,
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

    public static II_Material getMaterial(ItemStack is) {
        return materialField.get(is);
    }

    public static II_ItemStack getMold(Prefixes prefix, II_Material material, int amount) {
        ItemStack item = prefixToMoldMap.get(prefix);
        assert item != null;
        item = item.copy();
        item.stackSize = amount;
        if (material != null) {
            assert enabledMaterials.contains(material);
            materialField.set(item, material);
        }
        return new II_ItemStack(item);
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
    public void onAddedTo(ItemStack is) {
        prefixToMoldMap.put(prefix, is);
    }

    @Override
    public boolean shouldNEICheckDirect() {
        return true;
    }


    @LocalizeEvent
    private static String lang_isEmpty = "Empty";
    @LocalizeEvent
    private static String lang_material = "Material: ";

    @Override
    public void addAdditionalToolTips(List<String> list, ItemStack stack, EntityPlayer player, boolean f3_H) {
        II_Material material = materialField.get(stack);
        if (material == null) {
            list.add(lang_isEmpty);
        }
        else {
            MaterialLocalizer localizer = EngLocalizer.getInstance();//todo: replace properly
            list.add(lang_material + localizer.get(material));
        }

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

    public Prefixes getPrefix() {
        return prefix;
    }
}
