package idealindustrial.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import idealindustrial.II_Core;
import idealindustrial.autogen.material.II_Material;
import idealindustrial.autogen.material.Prefixes;
import idealindustrial.autogen.material.submaterial.chem.ChemicalInfo;
import idealindustrial.autogen.material.submaterial.render.RenderInfo;
import idealindustrial.autogen.oredict.OredictHandler;
import idealindustrial.autogen.oredict.RegisterOresEvent;
import idealindustrial.reflection.events.II_EventListener;
import idealindustrial.util.lang.LangHandler;
import idealindustrial.util.lang.LocalizeEvent;
import idealindustrial.util.lang.materials.EngLocalizer;
import idealindustrial.util.lang.materials.MaterialLocalizer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.function.IntConsumer;

@II_EventListener
public class MetaGeneratedItem extends MetaItem {

    Prefixes[] prefixes;
    II_Material[] materials;
    protected BitSet generatedItems;
    boolean hasLocal;
    protected static final List<MetaGeneratedItem> instances = new ArrayList<>();

    public MetaGeneratedItem(String unlocalized, II_Material[] materials, Prefixes[] prefixes) {
        super(unlocalized);
        instances.add(this);
        assert materials != null && materials.length == 1000;
        assert prefixes != null && prefixes.length <= 32;
        this.materials = materials;
        this.prefixes = prefixes;
        this.generatedItems = new BitSet(Short.MAX_VALUE);
        for (int i = 0; i < materials.length * prefixes.length; i++) {
            if (material(i) != null && prefix(i) != null && material(i).isEnabled(prefix(i))) {
                generatedItems.set(i);
            }
        }

        setCreativeTab(II_Core.II_MATERIAL_TAB);
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
        return material(damage).getRenderInfo();
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("unchecked")
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        foreachEnabled(i -> list.add(new ItemStack(item, 1, i)));
    }

    @Override
    public String getItemStackDisplayName(ItemStack p_77653_1_) {
        return super.getItemStackDisplayName(p_77653_1_);
    }

    /**
     * applies function for all damage values according enabled items
     *
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
        for (MetaGeneratedItem item : instances) {
            if (item.hasLocal) {
                continue;
            }
            for (int i = 0; i < item.generatedItems.size(); i++) {
                if (item.generatedItems.get(i)) {
                    LangHandler.add(item.getUnlocalizedName(i) + ".name", localizer.get(item.material(i), item.prefix(i)));
                }
            }
        }
    }

    @RegisterOresEvent
    public static void registerOres(OredictHandler handler) {
        for (MetaGeneratedItem item : instances) {
            item.foreachEnabled(i -> {
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

    public static List<MetaGeneratedItem> getInstances() {
        return instances;
    }

    public boolean isEnabled(int damage) {
        return generatedItems.get(damage);
    }

    public boolean isEnabled(ItemStack is) {
        return generatedItems.get(is.getItemDamage());
    }

    @Override
    protected void addAdditionalToolTips(List<String> list, ItemStack stack, EntityPlayer player, boolean f3_H) {
        if (isEnabled(stack)) {
            II_Material material = material(stack.getItemDamage());
            ChemicalInfo info = material.getChemicalInfo();
            if (info != null) {
                list.add(info.getFormula());
            }
        }
    }
}
