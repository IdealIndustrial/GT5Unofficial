package idealindustrial.blocks.plants;

import java.util.HashMap;
import java.util.Map;

public class Plants {
    public static final int TICK_RATE = 50;
    private static final PlantDef[] definitions = new PlantDef[1000];
    private static Map<PlantDef, Integer> plant2id = new HashMap<>();

    public static PlantDef get(int id) {
        if (id >= 0 && id < definitions.length) {
            return definitions[id];
        }
        return null;
    }

    public static int getID(PlantDef def) {
        if (plant2id.containsKey(def)) {
            return plant2id.get(def);
        }
        return -1;
    }


}
