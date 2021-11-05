package idealindustrial.impl.item;

import idealindustrial.api.items.IItemBehavior;
import idealindustrial.api.reflection.II_EventListener;
import idealindustrial.api.textures.IconContainer;
import idealindustrial.impl.textures.TextureManager;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

@II_EventListener
public class MetaBehaviorItem extends MetaItem32k {

    protected static List<MetaBehaviorItem> instances = new ArrayList<>();
    protected IItemBehavior[] behaviors = new IItemBehavior[Short.MAX_VALUE];
    protected IconContainer[] icons = new IconContainer[Short.MAX_VALUE];

    public MetaBehaviorItem(String unlocalized) {
        super(unlocalized, Short.MAX_VALUE);
        instances.add(this);
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

//    public ItemStack registerItem(int damage, String localName, IItemBehavior behavior) {
//        engNames[damage] = localName;
//        behaviors[damage] = behavior;
//        icons[damage] = TextureManager.INSTANCE.itemTexture("meta/" + getUnlocalizedName().substring(3) + "/" + damage);
//        return new ItemStack(this, 1, damage);
//    }


    public Builder registerItem(int id, String localName) {
        addItem(id, localName);
        icons[id] = TextureManager.INSTANCE.itemTexture("meta/" + getUnlocalizedName().substring(3) + "/" + id);
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


        public ItemStack toIS() {
            return new ItemStack(MetaBehaviorItem.this, 1, damage);
        }

    }
}
