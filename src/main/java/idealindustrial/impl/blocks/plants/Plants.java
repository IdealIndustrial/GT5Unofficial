package idealindustrial.impl.blocks.plants;

import idealindustrial.impl.blocks.plants.PlantDef.RenderType;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.HashMap;
import java.util.Map;

public class Plants {
    public static final int TICK_RATE = 100;
    public static final Map<BiomeGenBase, Integer> mineralsPerBiome = new HashMap<>();

    private static final PlantDef[] definitions = new PlantDef[1000];

    public static PlantDef get(int id) {
        if (id >= 0 && id < definitions.length) {
            return definitions[id];
        }
        return null;
    }

    public static int getID(PlantDef def) {
        if (def == null) {
            return 0;
        }
        return def.id;
    }

    protected static PlantDef register(int id, String name) {
        definitions[id] = new PlantDef(id, name);
        return definitions[id];
    }

    public static PlantDef copperPlant, tinPlant;

    public static void init() {
        register(0, "argentia").setLevels(3, new int[]{10, 10, 10, 10}, 2).setTextures(RenderType.Sharp);
        copperPlant = register(1, "copperplant").setLevels(0, new int[]{25}, 2).setTextures(RenderType.Cross);
        tinPlant = register(2, "tinPlant").setLevels(0, new int[]{25}, 2).setTextures(RenderType.Cross);
    }

}
