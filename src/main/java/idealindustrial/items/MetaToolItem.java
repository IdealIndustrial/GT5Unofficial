package idealindustrial.items;

import idealindustrial.autogen.material.II_Material;
import idealindustrial.autogen.material.II_Materials;
import idealindustrial.autogen.material.Prefixes;
import idealindustrial.textures.OverlayIconContainer;
import idealindustrial.textures.TextureManager;
import mods.railcraft.client.util.textures.Texture;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

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
        toolIcons = new OverlayIconContainer[engNames.length];
        toolColors = new Color[engNames.length];
        behaviors = new ToolBehavior[engNames.length];
        headPrefixes = new Prefixes[engNames.length];
        INSTANCE = this;
        init();
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
        registerTool(0, "Drill zbs").setHeadPrefix(Prefixes.toolHeadDrill).setRender("drill", Color.WHITE);
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
}
