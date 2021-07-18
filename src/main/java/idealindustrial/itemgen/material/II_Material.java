package idealindustrial.itemgen.material;

import idealindustrial.itemgen.material.submaterial.*;
import idealindustrial.itemgen.material.submaterial.render.RenderInfo;

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
    protected RenderInfo[] renderInfo; // 0 - solid, 1 - fluid, 2 - gas, 3 - plasma
    protected MaterialAutogenInfo autogenInfo;
    protected FuelInfo fuelInfo;
    protected ResearchInfo researchInfo;

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

    public RenderInfo getRenderInfo(MatterState state) {
        return renderInfo[state.ordinal()];
    }

    public RenderInfo getSolidRenderInfo() {
        return renderInfo[0];
    }

    public FluidInfo getLiquidInfo() {
        return fluidInfo;
    }

    public BlockInfo getBlockInfo() {
        return blockInfo;
    }

    public String name() {
        return name;
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
}
