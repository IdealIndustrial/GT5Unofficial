package idealindustrial.impl.registries;

import idealindustrial.impl.autogen.material.II_Materials;
import idealindustrial.impl.autogen.material.Prefixes;
import idealindustrial.impl.item.stack.HashedStack;
import idealindustrial.impl.item.stack.II_ItemStack;
import idealindustrial.impl.oredict.OreDict;
import idealindustrial.impl.oredict.OreInfo;
import idealindustrial.util.misc.ItemHelper;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

import static idealindustrial.impl.autogen.material.II_Materials.coal;
import static idealindustrial.impl.autogen.material.Prefixes.block;
import static idealindustrial.impl.autogen.material.Prefixes.gem;
import static idealindustrial.impl.oredict.OreDict.get;

public class FuelRegistry {

    static Map<HashedStack, FuelEntry> map = ItemHelper.queryMap(new HashMap<>());

    public static int getFuelValue(II_ItemStack is) {
        FuelEntry entry = map.get(is.toHashedStack());
        return entry == null ? 0 : entry.value;
    }

    public static int getFuelValue(OreInfo info) {
        FuelEntry entry = map.get(info.getMain());
        return entry == null ? 0 : entry.value;
    }

    public static void put(II_ItemStack is, int value) {
        map.put(is.toHashedStack(), new FuelEntry(value));
    }

    public static void put(HashedStack is, int value) {
        map.put(is, new FuelEntry(value));
    }

    public static void put(OreInfo info, int value) {
        info.getSubItems().forEach(i -> put(i, value));
    }

    public static void initMap() {
        put(get(gem, coal), 1600);
        put(get(block, coal), 16000);
    }

    static class FuelEntry {
        int value;

        public FuelEntry(int value) {
            this.value = value;
        }

        int getBurnValue() {
            return value;
        }
    }

}
