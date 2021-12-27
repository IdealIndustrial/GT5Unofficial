package idealindustrial.impl.oredict;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import idealindustrial.impl.autogen.material.II_Material;
import idealindustrial.impl.autogen.material.Prefixes;
import idealindustrial.impl.reflection.events.EventManager;
import idealindustrial.api.reflection.events.RegisterOresEvent;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

public class OredictHandler {
    List<OreInfo> expectedOres = new ArrayList<>();
    List<OrePair> delayedOres = new ArrayList<>();
    List<Func> callbacks = new ArrayList<>();

    public void init() {
        for (OrePair pair : delayedOres) {
            OreDict.register(pair.name, pair.stack);
        }
        checkExpected();
        callbacks.forEach(Func::call);
        delayedOres = null;
        expectedOres = null;
        callbacks = null;
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

    public interface Func {
        void call();
    }

    public void onPostRegistered(Func func) {
        callbacks.add(func);
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
        if (prefix.isOreDicted()) {
            OreDictionary.registerOre(oreName, stack);
        }
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
