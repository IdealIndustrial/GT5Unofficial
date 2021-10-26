package idealindustrial.items;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import idealindustrial.autogen.material.II_Material;
import idealindustrial.autogen.material.II_Materials;
import idealindustrial.autogen.material.Prefixes;
import idealindustrial.items.tools.ToolDrill;
import idealindustrial.textures.OverlayIconContainer;
import idealindustrial.textures.TextureManager;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;

import java.awt.*;
import java.util.List;

public class MetaToolItem extends MetaItem32k {
    OverlayIconContainer[] toolIcons;
    Color[] toolColors;
    ToolBehavior[] behaviors;
    Prefixes[] headPrefixes;

    public static MetaToolItem INSTANCE;

    public MetaToolItem() {
        super("metatool", 1000);
        assert INSTANCE == null;
        toolIcons = new OverlayIconContainer[engNames.length];
        toolColors = new Color[engNames.length];
        behaviors = new ToolBehavior[engNames.length];
        headPrefixes = new Prefixes[engNames.length];
        INSTANCE = this;
        init();
        MinecraftForge.EVENT_BUS.register(this);
    }

    public II_Material getMaterial(ItemStack is) {
        NBTTagCompound nbt = is.getTagCompound();
        if (nbt == null || !nbt.hasKey("mat")) {
            return null;
        }
        int id = nbt.getInteger("mat");
        return II_Materials.materialForID(id);
    }

    public Prefixes getToolHeadPrefix(int damage) {
        return headPrefixes[damage];
    }

    public OverlayIconContainer getBaseIcon(int damage) {
        return toolIcons[damage];
    }

    public Color getHandleColor(int damage) {
        return toolColors[damage];
    }

    protected ToolBehavior getBehavior(ItemStack itemStack) {
        return behaviors[Items.feather.getDamage(itemStack)];
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        forEachEnabled(id -> list.add(getTool(id, II_Materials.iron)));
    }

    public ItemStack getTool(int id, II_Material material) {
        ItemStack is = new ItemStack(MetaToolItem.this, 1, id);
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("mat", material.getID());
        is.setTagCompound(nbt);
        return is;
    }

    public Builder registerTool(int id, String name) {
        addItem(id, name);
        return new Builder(id);
    }

    private void init() {
        registerTool(0, "Drill zbs").setHeadPrefix(Prefixes.toolHeadDrill).setToolBehavior(new ToolDrill()).setRender("drill", Color.WHITE);
    }

    public class Builder {
        int id;

        public Builder(int id) {
            this.id = id;
        }

        public Builder setToolBehavior(ToolBehavior behavior) {
            behaviors[id] = behavior;
            return this;
        }

        public Builder setHeadPrefix(Prefixes prefix) {
            headPrefixes[id] = prefix;
            return this;
        }

        public Builder setRender(String iconName, Color color) {
            toolColors[id] = color;
            toolIcons[id] = (OverlayIconContainer) TextureManager.INSTANCE.itemTextureWithOverlay("meta/tool/" + iconName);
            return this;
        }

        public ItemStack toISWithMaterial(II_Material material) {
            return getTool(id, material);
        }
    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {
        TextureManager.INSTANCE.initItems(iconRegister);
    }


    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    @Override
    public boolean isRepairable() {
        return false;
    }

    @Override
    public boolean canHarvestBlock(Block par1Block, ItemStack itemStack) {
        return super.canHarvestBlock(par1Block, itemStack);
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (isEnabled(stack)) {
            return getBehavior(stack).onItemUseFirst(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
        }
        return super.onItemUseFirst(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
    }

    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        return super.onItemUse(itemStack, player, world, x, y, z, side, hitX, hitY, hitZ);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        return super.onLeftClickEntity(stack, player, entity);
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, int X, int Y, int Z, EntityPlayer player) {
        if (isEnabled(itemstack)) {
            return getBehavior(itemstack).onBlockStartBreak(itemstack, X, Y, Z, player);
        }
        return super.onBlockStartBreak(itemstack, X, Y, Z, player);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        if (isEnabled(itemStack)) {
            return getBehavior(itemStack).onItemRightClick(itemStack, world, player);
        }
        return super.onItemRightClick(itemStack, world, player);
    }

    @Override
    public boolean onBlockDestroyed(ItemStack itemStack, World world, Block block, int x, int y, int z, EntityLivingBase player) {
        if (isEnabled(itemStack)) {
            return getBehavior(itemStack).onBlockDestroyed(itemStack, world, block, x, y, z, player);
        }
        return super.onBlockDestroyed(itemStack, world, block, x, y, z, player);
    }


    @Override
    public float getDigSpeed(ItemStack itemstack, Block block, int metadata) {
        return super.getDigSpeed(itemstack, block, metadata);
    }

    @Override
    public int getHarvestLevel(ItemStack stack, String toolClass) {
        if (isEnabled(stack)) {
            return getBehavior(stack).getHarvestLevel(stack, toolClass);
        }
        return super.getHarvestLevel(stack, toolClass);
    }

    @SubscribeEvent
    public void onBlockHarvestingEvent(BlockEvent.HarvestDropsEvent event) {

    }

    @SubscribeEvent
    public void onBlockBreakSpeedEvent(PlayerEvent.BreakSpeed event) {
        ItemStack is = event.entityPlayer.getHeldItem();
        if (is != null && is.getItem() == this && isEnabled(is)) {
            event.newSpeed = getBehavior(is).getBreakSpeed(event);
        }
    }
}
