package idealindustrial.itemgen.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import idealindustrial.itemgen.material.II_Material;
import idealindustrial.itemgen.material.Prefixes;
import idealindustrial.itemgen.material.submaterial.render.RenderInfo;
import idealindustrial.itemgen.oredict.RegisterOresEvent;
import idealindustrial.itemgen.oredict.II_OredictHandler;
import idealindustrial.reflection.events.II_EventListener;
import idealindustrial.util.lang.II_Lang;
import idealindustrial.util.lang.LocalizeEvent;
import idealindustrial.util.lang.materials.EngLocalizer;
import idealindustrial.util.lang.materials.MaterialLocalizer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.function.IntConsumer;

@II_EventListener
public abstract class II_MetaGeneratedItem extends II_MetaItem {

    Prefixes[] prefixes;
    II_Material[] materials;
    protected BitSet generatedItems;
    boolean hasLocal;
    protected static final List<II_MetaGeneratedItem> instances = new ArrayList<>();

    public II_MetaGeneratedItem(String unlocalized, II_Material[] materials, Prefixes[] prefixes) {
        super(unlocalized);
        instances.add(this);
        assert materials != null && materials.length == 1000;
        assert prefixes != null && prefixes.length <= 32;
        this.materials = materials;
        this.prefixes = prefixes;
        this.generatedItems = new BitSet(materials.length * prefixes.length);
        for (int i = 0; i < materials.length * prefixes.length; i++) {
            if (material(i) != null && prefix(i) != null && material(i).isEnabled(prefix(i))) {
                generatedItems.set(i);
            }
        }
    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {
        //everything is registered already
    }

    public II_Material material(int damage) {
        return materials[damage % 1000];
    }

    public Prefixes prefix(int damage) {
        return prefixes[damage / 1000];
    }

    public RenderInfo getRenderInfo(int damage) {
        return material(damage).getSolidRenderInfo();
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("unchecked")
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        foreachEnabled(i -> list.add(new ItemStack(item, 1 ,i)));
    }

    @Override
    public String getItemStackDisplayName(ItemStack p_77653_1_) {
        return super.getItemStackDisplayName(p_77653_1_);
    }

    /**
     * applies function for all damage values according enabled items
     * @param consumer - function to apply
     */
    protected void foreachEnabled(IntConsumer consumer) {
        for (int i = 0; i < generatedItems.length(); i++) {
            if (generatedItems.get(i)) {
                consumer.accept(i);
            }
        }
    }

    @LocalizeEvent
    public static void localize() {
        MaterialLocalizer localizer = EngLocalizer.getInstance();
        for (II_MetaGeneratedItem item : instances) {
            if (item.hasLocal) {
                continue;
            }
            for (int i = 0; i < item.generatedItems.size(); i++) {
                if (item.generatedItems.get(i)) {
                    II_Lang.add(item.getUnlocalizedName(i) + ".name", localizer.get(item.material(i), item.prefix(i)));
                }
            }
        }
    }

    @RegisterOresEvent
    public static void registerOres(II_OredictHandler handler) {
        for (II_MetaGeneratedItem item : instances) {
            item.foreachEnabled( i -> {
                Prefixes prefix = item.prefix(i);
                if (!prefix.isOreDicted()) {
                    return;
                }
                II_Material material = item.material(i);
                assert material != null;
                handler.registerOre(prefix, material, new ItemStack(item, 1, i));
            });
        }
    }

    public static List<II_MetaGeneratedItem> getInstances() {
        return instances;
    }
}
