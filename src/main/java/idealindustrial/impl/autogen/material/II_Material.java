package idealindustrial.impl.autogen.material;

import idealindustrial.impl.autogen.material.submaterial.*;
import idealindustrial.impl.autogen.material.submaterial.chem.ChemicalInfo;
import idealindustrial.impl.autogen.material.submaterial.render.RenderInfo;

import java.util.Set;

/**
 * Main class of Materials in mod, contains all info about generating items, blocks, tiles and recipes
 * for each instance.
 */
public class II_Material {
    protected int id;
    protected String name;
    protected Set<Prefixes> enabledPrefixes;
    protected Set<Prefixes> expectedPrefixes; // prefixes that are expected from other mods and not generated
    protected MatterState normalForm;
    protected BlockInfo blockInfo;
    protected FluidInfo fluidInfo; // fluid, gas, plasma...
    protected RenderInfo renderInfo;
    protected MaterialAutogenInfo autogenInfo;
    protected ChemicalInfo chemicalInfo;
    protected FuelInfo fuelInfo;
    protected ResearchInfo researchInfo;
    protected WorldOreInfo oreInfo;

    public II_Material(int id,  String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * checks if
     * @param prefix is enabled for this material
     * @return is the value
     * if prefix is not enabled for this material, new item will not be generated and registered
     */
    public boolean isEnabled(Prefixes prefix) {
        return enabledPrefixes.contains(prefix);
    }

    public RenderInfo getRenderInfo() {
        return renderInfo;
    }

    public FluidInfo getLiquidInfo() {
        return fluidInfo;
    }

    public BlockInfo getBlockInfo() {
        return blockInfo;
    }

    public MaterialAutogenInfo getAutogenInfo() {
        return autogenInfo;
    }

    public String name() {
        return name;
    }

    public String oreDictName() {
        return name.replace(" ", "");
    }

    public MatterState getNormalForm() {
        return normalForm;
    }

    public Set<Prefixes> getEnabledPrefixes() {
        return enabledPrefixes;
    }

    public Set<Prefixes> getExpectedPrefixes() {
        return expectedPrefixes;
    }

    @Override
    public String toString() {
        return name.toLowerCase();
    }

    public int getID() {
        return id;
    }

    public ChemicalInfo getChemicalInfo() {
        return chemicalInfo;
    }

    public WorldOreInfo getOreInfo() {
        return oreInfo;
    }
}
