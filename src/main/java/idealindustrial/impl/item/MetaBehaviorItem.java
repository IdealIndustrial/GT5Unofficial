package idealindustrial.impl.item;

import cpw.mods.fml.common.FMLCommonHandler;
import idealindustrial.api.items.II_ItemRenderer;
import idealindustrial.api.items.IItemBehavior;
import idealindustrial.api.reflection.II_EventListener;
import idealindustrial.api.textures.IconContainer;
import idealindustrial.impl.textures.TextureManager;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@II_EventListener
public class MetaBehaviorItem extends MetaItem32k {

    protected static List<MetaBehaviorItem> instances = new ArrayList<>();
    protected IItemBehavior[] behaviors = new IItemBehavior[Short.MAX_VALUE];
    protected IconContainer[] icons = new IconContainer[Short.MAX_VALUE];

    public MetaBehaviorItem(String unlocalized) {
        super(unlocalized, Short.MAX_VALUE);
        instances.add(this);
    }

    public static List<MetaBehaviorItem> getInstances() {
        return instances;
    }

    public IItemBehavior getBehavior(int damage) {
        if (!isEnabled(damage)) {
            return null;
        }
        return behaviors[damage];
    }

    public static IItemBehavior getBehavior(ItemStack is) {
        MetaBehaviorItem behaviorItem = (MetaBehaviorItem) is.getItem();
        return behaviorItem.getBehavior(is.getItemDamage());
    }
    @Override
    public IIcon getIconFromDamage(int par1) {
        return icons[par1] == null ? null : icons[par1].getIcon();
    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {
        super.registerIcons(iconRegister);
    }

    @Override
    public void addSubItems(Item item, CreativeTabs tab, List<ItemStack> list) {
        forEachEnabled(i -> {
            if (behaviors[i] == null || !behaviors[i].addSubItems(item, i, tab, list)) {
                list.add(new ItemStack(item, 1, i));
            }
        });
    }

    @Override
    public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player) {
        int damage = getDamage(is);
        if (behaviors[damage] == null) {
            return super.onItemRightClick(is, world, player);
        }
        return behaviors[damage].onItemRightClick(is, world, player);
    }

    @Override
    public boolean onItemUse(ItemStack is, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        int damage = getDamage(is);
        if (behaviors[damage] == null) {
            return super.onItemUse(is, player, world, x, y, z, side, hitX, hitY, hitZ);
        }
        return behaviors[damage].onItemUse(is, player, world, x, y, z, side, hitX, hitY, hitZ);
    }

    @Override
    public boolean onItemUseFirst(ItemStack is, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        int damage = getDamage(is);
        if (behaviors[damage] == null) {
            return super.onItemUseFirst(is, player, world, x, y, z, side, hitX, hitY, hitZ);
        }
        return behaviors[damage].onItemUseFirst(is, player, world, x, y, z, side, hitX, hitY, hitZ);
    }

    @Override
    protected void addAdditionalToolTips(List<String> list, ItemStack stack, EntityPlayer player, boolean f3_H) {
        super.addAdditionalToolTips(list, stack, player, f3_H);
        int damage = getDamage(stack);
        if (behaviors[damage] != null) {
            behaviors[damage].addAdditionalToolTips(list, stack, player, f3_H);
        }
    }

    public Builder registerItem(int id, String localName) {
        addItem(id, localName);
        return new Builder(id);
    }

    public class Builder {

        int damage;
        boolean overlay;

        public Builder(int damage) {
            this.damage = damage;
            this.overlay = false;
        }

        public Builder setBehavior(IItemBehavior behavior) {
            behaviors[damage] = behavior;
            return this;
        }

        public void added() {
            if (behaviors[damage] == null || behaviors[damage].loadIcon()) {
                icons[damage] = TextureManager.INSTANCE.itemTexture("meta/" + getUnlocalizedName().substring(3) + "/" + damage);
            }
            if (behaviors[damage] != null) {
                behaviors[damage].onAddedTo(new ItemStack(MetaBehaviorItem.this, 1, damage));
            }
        }

//        public Builder setRender(Class<II_ItemRenderer> render) {
//            if (FMLCommonHandler.instance().getEffectiveSide().isClient()) {
//                try {
//                    renderers[damage] = render.newInstance();
//                } catch (InstantiationException | IllegalAccessException e) {
//                    e.printStackTrace();
//                    throw new IllegalStateException("Cannot create renderer for id: " + damage, e);
//                }
//            }
//            return this;
//        }
//
//        public Builder setRender(Supplier<II_ItemRenderer> render) {
//            if (FMLCommonHandler.instance().getEffectiveSide().isClient()) {
//                renderers[damage] = render.get();
//            }
//            return this;
//        }
//
//        public Builder setRender(II_ItemRenderer render) {
//            renderers[damage] = render;
//            return this;
//        }


        public ItemStack toIS() {
            added();
            return new ItemStack(MetaBehaviorItem.this, 1, damage);
        }

    }
}
