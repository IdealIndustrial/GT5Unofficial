package idealindustrial.impl.loader;

import idealindustrial.II_Core;
import idealindustrial.impl.autogen.material.II_Material;
import idealindustrial.impl.autogen.material.II_Materials;
import idealindustrial.impl.autogen.material.Prefixes;
import idealindustrial.impl.item.*;
import idealindustrial.impl.item.behaviors.BehaviorClock;
import idealindustrial.impl.item.behaviors.BehaviorGuideRenderer;
import idealindustrial.impl.render.GT_FluidDisplayStackRenderer;
import net.minecraft.item.ItemStack;

public class ItemsLoader {

    static MetaBehaviorItem behaviorItem1;
    public static ItemStack wrench, mallet, cell, clock;

    public void preLoad() {
        registerMetaItems();
        initMetaGeneratedItems();
        new GT_FluidDisplayItem();
        new GT_FluidDisplayStackRenderer();
        new MetaToolItem();

    }

    protected void initMetaGeneratedItems() {
        II_Material[][] materials = new II_Material[][]{II_Materials.materialsK1};
        String[] namePostfixes = new String[]{".01"};
        for (int i = 0; i < materials.length; i++) {
            II_Material[] mat = materials[i];
            String postfix = namePostfixes[i];

            new MetaGeneratedItem("metagenerated.heads" + postfix, mat,
                    Prefixes.toolHeadDrill);
            new MetaGeneratedItem("metagenerated1" + postfix, mat,
                    Prefixes.ingot, Prefixes.dust, Prefixes.dustSmall, Prefixes.dustTiny, Prefixes.plate, Prefixes.nugget, Prefixes.nuggetBig);
        }

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
