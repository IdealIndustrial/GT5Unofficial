package idealindustrial.autogen.oredict;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import idealindustrial.autogen.material.II_Material;
import idealindustrial.autogen.material.Prefixes;
import idealindustrial.reflection.events.EventManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

public class OredictHandler {
    List<OreInfo> expectedOres = new ArrayList<>();
    List<OrePair> delayedOres = new ArrayList<>();

    public void init() {
        for (OrePair pair : delayedOres) {
            OreDict.register(pair.name, pair.stack);
        }
        checkExpected();
        delayedOres = null;
        expectedOres = null;
    }

    private void checkExpected() {
        boolean failed = false;
        for (OreInfo info : expectedOres) {
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
            oreName = OreDict.registerMain(prefix, material, stack);
        }
        else {
            oreName = OreDict.register(prefix, material, stack);
        }
        OreDictionary.registerOre(oreName, stack);
        isAddingOre = false;
    }

    public void registerExpected(Prefixes prefix, II_Material material) {
        OreInfo info = new OreInfo(prefix, material);
        expectedOres.add(info);
        OreDict.add(info);
    }

    public void loadAlreadyNicelyLoadedByForgeOreDictsWithoutFuckingEvents() {
        for (String name : OreDictionary.getOreNames()) {
            for (ItemStack is : OreDictionary.getOres(name)) {
                delayedOres.add(new OrePair(name, is));
            }
        }
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
