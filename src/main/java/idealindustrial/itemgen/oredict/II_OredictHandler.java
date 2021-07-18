package idealindustrial.itemgen.oredict;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import idealindustrial.itemgen.material.II_Material;
import idealindustrial.itemgen.material.Prefixes;
import idealindustrial.reflection.events.EventManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

public class II_OredictHandler {
    II_OreDict oreDict = II_OreDict.getInstance();
    List<II_OreInfo> expectedOres = new ArrayList<>();
    List<OrePair> delayedOres = new ArrayList<>();

    public void init() {
        for (OrePair pair : delayedOres) {
            oreDict.register(pair.name, pair.stack);
        }
        checkExpected();
        delayedOres = null;
        expectedOres = null;
    }

    private void checkExpected() {
        boolean failed = false;
        for (II_OreInfo info : expectedOres) {
            if (info.size() == 0) {
                failed = true;
                System.out.println("There is no ore, that was expected");
                System.out.println(info);
            }
        }
        assert !failed;
    }

    public void fireRegisterEvent() {
        EventManager.INSTANCE.callAll(RegisterOresEvent.class, this);
    }

    @SubscribeEvent
    public void onRegisterOre(OreDictionary.OreRegisterEvent event) {
        if (!isAddingOre) {
            delayedOres.add(new OrePair(event.Name, event.Ore));
        }
    }

    boolean isAddingOre = false;
    public void registerOre(Prefixes prefix, II_Material material, ItemStack stack) {
        isAddingOre = true;
        String oreName;
        if (prefix.isUnifiable()) {
            oreName = oreDict.registerMain(prefix, material, stack);
        }
        else {
            oreName = oreDict.register(prefix, material, stack);
        }
        OreDictionary.registerOre(oreName, stack);
        isAddingOre = false;
    }

    public void registerExpected(Prefixes prefix, II_Material material) {
        II_OreInfo info = new II_OreInfo(prefix, material);
        expectedOres.add(info);
        oreDict.add(info);
    }

    private static class OrePair {
        String name;
        ItemStack stack;

        public OrePair(String name, ItemStack stack) {
            this.name = name;
            this.stack = stack;
        }
    }
}
