package idealindustrial.impl.item;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import idealindustrial.util.misc.II_Paths;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

import java.util.List;

import static idealindustrial.II_Core.MOD_ID;

public abstract class BaseItem extends Item {

    private final String unlocalizeName;
    protected IIcon icon;

    public BaseItem(String unlocalized) {
        super();
        unlocalizeName = "ii." + unlocalized;

        //setCreativeTab(GregTech_API.TAB_GREGTECH);
        GameRegistry.registerItem(this, unlocalizeName, MOD_ID);
    }

    @Override
    public final Item setUnlocalizedName(String name) {
        return this;
    }

    @Override
    public final String getUnlocalizedName() {
        return unlocalizeName;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return getHasSubtypes() ? getUnlocalizedName(getDamage(stack)) : unlocalizeName ;
    }

    public String getUnlocalizedName(int damage) {
        return unlocalizeName + "." + damage;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        icon = iconRegister.registerIcon(II_Paths.PATH_ITEM_ICONS + unlocalizeName);
    }


    @Override
    public boolean doesSneakBypassUse(World world, int x, int y, int z, EntityPlayer player) {
        return true;
    }

    @Override
    public IIcon getIconFromDamage(int par1) {
        return icon;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean f3_H) {
        if (getMaxDamage() > 0 && !getHasSubtypes())
            list.add((stack.getMaxDamage() - getDamage(stack)) + " / " + stack.getMaxDamage());
        addAdditionalToolTips(list, stack, player, f3_H);
    }

    protected void addAdditionalToolTips(List<String> list, ItemStack stack, EntityPlayer player, boolean f3_H) {
        if (Keyboard.isKeyDown(Keyboard.KEY_I)) {
            list.add("gg");
        }
        //todo: add more info on I
    }

    protected void addSubItems(Item item, CreativeTabs tab, List<ItemStack> list) {

    }

    @Override
    @SuppressWarnings("unchecked")
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        addSubItems(item, tab, list);
    }

    public boolean isItemStackUsable(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getContainerItem(ItemStack stack) {
        return null;
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return getContainerItem(stack) != null;
    }

}
