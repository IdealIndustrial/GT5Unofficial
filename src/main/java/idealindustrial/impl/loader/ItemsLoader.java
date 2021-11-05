package idealindustrial.impl.loader;

import idealindustrial.II_Core;
import idealindustrial.impl.item.MetaGeneratedItem_1;
import idealindustrial.impl.item.behaviors.BehaviorClock;
import idealindustrial.impl.item.behaviors.BehaviorGuideRenderer;
import idealindustrial.impl.item.GT_FluidDisplayItem;
import idealindustrial.impl.item.MetaBehaviorItem;
import idealindustrial.impl.item.MetaToolItem;
import idealindustrial.impl.render.GT_FluidDisplayStackRenderer;
import net.minecraft.item.ItemStack;

public class ItemsLoader {

    static MetaBehaviorItem behaviorItem1;
    public static ItemStack wrench, mallet, cell, clock;

    public void preLoad() {
        new MetaGeneratedItem_1();
        registerMetaItems();
        new GT_FluidDisplayItem();
        new GT_FluidDisplayStackRenderer();
        new MetaToolItem();

    }

    protected void registerMetaItems() {
        behaviorItem1 = new MetaBehaviorItem("item1");
        behaviorItem1.setCreativeTab(II_Core.II_MAIN_TAB);
        behaviorItem1.registerItem(1, "Test");
        behaviorItem1.registerItem(2, "Guide Renderer").setBehavior(new BehaviorGuideRenderer());
        wrench = behaviorItem1.registerItem(3, "Wrench").toIS();
        mallet = behaviorItem1.registerItem(4, "Soft Mallet").toIS();
        cell = behaviorItem1.registerItem(5, "Empty Cell").toIS();
        clock = behaviorItem1.registerItem(6, "Clock").setBehavior(new BehaviorClock()).toIS();
    }

}
