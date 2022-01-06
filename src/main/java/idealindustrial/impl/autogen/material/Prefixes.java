package idealindustrial.impl.autogen.material;

import java.util.Arrays;

/*
 *
 */
public enum Prefixes {
    ingot( "", "Ingot"),
    plate("", "Plate"),
    dust("", "Dust"),
    dustSmall("Small Pile of", "Dust"),
    dustTiny("Tiny Pile of", "Dust"),
    nugget("", "Nugget"),
    nuggetBig("Big", "Nugget"),
    nuggetBigHot("Big", "Nugget (Hot)"),
    block("Block of", ""),
    cell("", "Cell"),
    gasCell("", "Cell"),
    plasmaCell("", "Cell"),
    cable01("1x", "Cable"),
    ore("", "Ore"),
    oreSmall("Small", "Ore"),
    gem("", "Gem"),
    toolHeadDrill("", "Drill Head"),
    pickaxe("", "Pickaxe"),
    toolHeadPickaxe("", "Pickaxe Head", pickaxe)

    ;

    //used for naming items eg: prefix + material_name + postfix
    public final String prefix, postfix;
    //used for prefixes like pick head and pick, pick is subPrefix (used for localization)
    public final Prefixes subPrefix;
    private boolean unifiable = false;
    private boolean isOreDicted = true;

    Prefixes(String prefix, String postfix, Prefixes subPrefix) {
        this.prefix = prefix;
        this.postfix = postfix;
        this.subPrefix = subPrefix;
    }

    Prefixes(String prefix, String postfix) {
        this(prefix, postfix, null);
    }

    public boolean isUnifiable() {
        return unifiable;
    }

    //only corresponds for adding this prefix to Forge Ore Dict,
    //prefix will be still added to my OreDict even if is not oreDicted
    public boolean isOreDicted() {
        return isOreDicted;
    }

    private static void setUnifiable(Prefixes... prefixes) {
        for (Prefixes prefix : prefixes) {
            prefix.unifiable = true;
        }
    }

    private static void setNonOreDicted(Prefixes... prefixes) {
        for (Prefixes prefix : prefixes) {
            prefix.isOreDicted = false;
        }
    }

    protected static void postInit() { //called after cl-init due to enum value init after static{  } fields
        setUnifiable(ingot, plate, dust, dustSmall, dustTiny);
        setNonOreDicted(cell, gasCell, plasmaCell);//no ores for cells, use fluid registry xD
        setNonOreDicted(Arrays.stream(values()).filter(pr -> pr.name().toLowerCase().contains("toolhead")).toArray(Prefixes[]::new));//no ordicts for tool heads, they are
    }

}
