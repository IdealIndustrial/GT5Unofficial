package idealindustrial.autogen.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregtech.api.interfaces.IIconContainer;
import idealindustrial.reflection.events.II_EventListener;
import idealindustrial.textures.TextureManager;
import idealindustrial.util.lang.LangHandler;
import idealindustrial.util.lang.LocalizeEvent;
import idealindustrial.util.misc.II_Paths;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

@II_EventListener
public class MetaBehaviorItem extends MetaItem {

    protected static List<MetaBehaviorItem> instances = new ArrayList<>();
    protected IItemBehavior[] behaviors = new IItemBehavior[Short.MAX_VALUE];
    protected String[] engNames = new String[Short.MAX_VALUE];
    protected IIconContainer[] icons = new IIconContainer[Short.MAX_VALUE];

    public MetaBehaviorItem(String unlocalized) {
        super(unlocalized);
        instances.add(this);
    }

    public void registerItem(int damage, String localName, IItemBehavior behavior) {
        engNames[damage] = localName;
        behaviors[damage] = behavior;
        icons[damage] = TextureManager.INSTANCE.itemTexture("meta/" + getUnlocalizedName().substring(3) + "/" + damage);
    }

    @Override
    public IIcon getIconFromDamage(int par1) {
        return icons[par1] == null ? null : icons[par1].getIcon();
    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {
        super.registerIcons(iconRegister);
        TextureManager.INSTANCE.initItems(iconRegister);
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("unchecked")
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i < engNames.length; i++) {
            if (engNames[i] == null) {
                continue;
            }
            list.add(new ItemStack(item, 1, i));
        }
    }

    @LocalizeEvent
    public static void localize() {
        for (MetaBehaviorItem item : instances) {
            for (int i = 0; i < Short.MAX_VALUE; i++) {
                if (item.engNames[i] == null) {
                    continue;
                }
                LangHandler.add(item.getUnlocalizedName(i) + ".name", item.engNames[i]);
            }
        }
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
}
