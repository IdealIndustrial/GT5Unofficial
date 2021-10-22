package idealindustrial.util.misc;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import idealindustrial.autogen.material.Prefixes;
import idealindustrial.autogen.material.submaterial.MatterState;

import java.util.HashMap;
import java.util.Map;

public class MiscValues {


    public static Map<Prefixes, Prefixes> familiarPrefixMap = new HashMap<Prefixes, Prefixes>() {{
        put(Prefixes.dust, Prefixes.dust);
        put(Prefixes.dustSmall, Prefixes.dust);
        put(Prefixes.dustTiny, Prefixes.dust);

        put(Prefixes.cell, Prefixes.cell);
        put(Prefixes.gasCell, Prefixes.cell);
        put(Prefixes.plasmaCell, Prefixes.cell);
    }};

    public static BiMap<Prefixes, MatterState> cellToStateMap = HashBiMap.create();
    static {
        cellToStateMap.put(Prefixes.cell, MatterState.Liquid);
        cellToStateMap.put(Prefixes.gasCell, MatterState.Gas);
        cellToStateMap.put(Prefixes.plasmaCell, MatterState.Plasma);
    }





}