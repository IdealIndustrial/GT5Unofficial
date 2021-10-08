package idealindustrial.autogen.material;

import gregtech.api.enums.OrePrefixes;

/*
 todo: import prefixes from TextureSet.java
 *
 */
public enum Prefixes {
    ingot( "", "Ingot", 1),
    plate("", "Plate", 17),
    dust("", "Dust",2),
    dustSmall("Small Pile of", "Dust",1),
    dustTiny("Tiny Pile of", "Dust",0),
    block("Block of", "", 71),
    cell("", "Cell", 30),
    gasCell("", "Cell", 30),
    plasmaCell("", "Cell", 30),
    cable01("1x", "Cable", 69);

    public final String prefix, postfix;
    public final int textureIndex;
    protected boolean unifiable = false;
    protected boolean isOreDicted = true;

    Prefixes(String prefix, String postfix, int textureIndex) {
        this.prefix = prefix;
        this.postfix = postfix;
        this.textureIndex = textureIndex;
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
