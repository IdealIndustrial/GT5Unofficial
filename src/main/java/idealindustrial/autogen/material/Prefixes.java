package idealindustrial.autogen.material;
/*
 todo: import prefixes from TextureSet.java
 *
 */
public enum Prefixes {
    ingot( "", "Ingot"),
    plate("", "Plate"),
    dust("", "Dust"),
    dustSmall("Small Pile of", "Dust"),
    dustTiny("Tiny Pile of", "Dust"),
    block("Block of", ""),
    cell("", "Cell"),
    gasCell("", "Cell"),
    plasmaCell("", "Cell"),
    cable01("1x", "Cable"),
    ore("", "Ore"),
    oreSmall("Small", "Ore"),
    toolHeadDrill("", "Drill Head");

    public final String prefix, postfix;
    protected boolean unifiable = false;
    protected boolean isOreDicted = true;

    Prefixes(String prefix, String postfix) {
        this.prefix = prefix;
        this.postfix = postfix;
    }

    public boolean isUnifiable() {
        return unifiable;
    }

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
    }

}
