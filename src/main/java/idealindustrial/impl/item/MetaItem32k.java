package idealindustrial.impl.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import idealindustrial.api.items.ItemElectricStats;
import idealindustrial.api.reflection.II_EventListener;
import idealindustrial.util.lang.LangHandler;
import idealindustrial.util.lang.LocalizeEvent;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntConsumer;

@II_EventListener
public abstract class MetaItem32k extends MetaItem {

    protected static List<MetaItem32k> instances = new ArrayList<>();

    protected String[] engNames;
    protected ItemElectricStats[] electricStats;

    public MetaItem32k(String unlocalized, int quantity) {
        super(unlocalized);
        instances.add(this);
        engNames = new String[quantity];
        electricStats = new ItemElectricStats[quantity];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addSubItems(Item item, CreativeTabs tab, List<ItemStack> list) {
        forEachEnabled(i -> list.add(new ItemStack(item, 1, i)));
    }

    @LocalizeEvent
    public static void localize() {
        instances.stream().filter(MetaItem32k::doLocalize).forEach(item ->
                    item.forEachEnabled(i -> LangHandler.add(item.getUnlocalizedName(i) + ".name", item.engNames[i]))

        );
    }

    protected boolean doLocalize() {
        return true;
    }

    public boolean isEnabled(int i) {
        if (i >= engNames.length) {
            return false;
        }
        return engNames[i] != null;
    }

    public boolean isEnabled(ItemStack is) {
        return isEnabled(is.getItemDamage());
    }


    public void forEachEnabled(IntConsumer function) {
        for (int i = 0; i < engNames.length; i++) {
            if (isEnabled(i)) {
                function.accept(i);
            }
        }
    }

    protected void addItem(int id, String engName) {
        engNames[id] = engName;
    }

    protected void setElectricStats(int id, ItemElectricStats stats) {
        electricStats[id] = stats;
    }
}
