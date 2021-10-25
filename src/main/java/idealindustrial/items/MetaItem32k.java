package idealindustrial.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import idealindustrial.reflection.events.II_EventListener;
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

    protected String[] engNames = new String[Short.MAX_VALUE];
    protected ItemElectricStats[] electricStats = new ItemElectricStats[Short.MAX_VALUE];

    public MetaItem32k(String unlocalized, int quantity) {
        super(unlocalized);
        instances.add(this);
        engNames = new String[quantity];
        electricStats = new ItemElectricStats[quantity];
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("unchecked")
    public void getSubItems(Item item, CreativeTabs tab, List list) {
//        for (int i = 0; i < engNames.length; i++) {
//            if (engNames[i] == null) {
//                continue;
//            }
//            list.add(new ItemStack(item, 1, i));
//        }
        forEachEnabled(i -> list.add(new ItemStack(item, 1, i)));
    }

    @LocalizeEvent
    public static void localize() {
//        for (MetaItem32k item : instances) {
//            for (int i = 0; i < Short.MAX_VALUE; i++) {
//                if (item.engNames[i] == null) {
//                    continue;
//                }
//                LangHandler.add(item.getUnlocalizedName(i) + ".name", item.engNames[i]);
//            }
        instances.forEach(item ->
                item.forEachEnabled(i -> LangHandler.add(item.getUnlocalizedName(i) + ".name", item.engNames[i]))
        );
//        }
    }

    public boolean isEnabled(int i) {
        if (i >= engNames.length) {
            return false;
        }
        return engNames[i] != null;
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
