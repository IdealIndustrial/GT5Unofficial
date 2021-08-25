package idealindustrial.util.lang.materials;

import idealindustrial.autogen.material.II_Material;
import idealindustrial.autogen.material.Prefixes;
import idealindustrial.autogen.material.submaterial.MatterState;
import idealindustrial.util.misc.MiscValues;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static idealindustrial.util.misc.MiscValues.familiarPrefixMap;

public class EngLocalizer extends BaseLocalizer {

    private static EngLocalizer INSTANCE = null;


    Map<MaterialPrefix, String> globalOverrides = new HashMap<>();

    Map<MaterialPrefix, PrefixOverrides> prefixOverrides = new HashMap<>();

    private EngLocalizer() {
        initOverrides();
    }

    protected void initOverrides() {
        //add All here
    }

    @Override
    protected String getFormatFor(II_Material material, Prefixes prefix) {
        String global = globalOverrides.get(new MaterialPrefix(material, prefix));
        if (global != null) {
            return global;
        }

        Prefixes familiar = familiarPrefixMap.getOrDefault(prefix, prefix);
        PrefixOverrides overrides = prefixOverrides.get(new MaterialPrefix(material, familiar));
        if (overrides != null) {
            String namePrefix = overrides.getPrefix(prefix.prefix);
            String namePostfix = overrides.getPostfix(prefix.postfix);
            return namePrefix + " %s " + namePostfix;
        }
        return prefix.prefix + " %s " + prefix.postfix;
    }

    @Override
    protected String getNameFor(II_Material material, Prefixes prefix) {
        if (MiscValues.cellToStateMap.containsKey(prefix)) {
            return get(material, MiscValues.cellToStateMap.get(prefix));
        }
        return material.name();
    }

    @Override
    protected String getNameFor(II_Material material, MatterState state) {
        return material.name();
    }

    protected void addGlobal(String replacement, Prefixes prefix, II_Material... materials) {
        Arrays.stream(materials).forEach(material -> globalOverrides.put(new MaterialPrefix(material, prefix), replacement));
    }

    protected void addOverride(PrefixOverrides prefixOverride, Prefixes prefix, II_Material... materials) {
        Arrays.stream(materials).forEach(material -> prefixOverrides.put(new MaterialPrefix(material, prefix), prefixOverride));
    }

    public static EngLocalizer getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EngLocalizer();
        }
        return INSTANCE;
    }


    protected static class MaterialPrefix {
        II_Material material;
        Prefixes prefix;

        public MaterialPrefix(II_Material material, Prefixes prefix) {
            this.material = material;
            this.prefix = prefix;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MaterialPrefix that = (MaterialPrefix) o;
            return Objects.equals(material, that.material) &&
                    prefix == that.prefix;
        }

        @Override
        public int hashCode() {
            return Objects.hash(material, prefix);
        }
    }

    protected static class PrefixOverrides {
        String prefix, postfix;

        public PrefixOverrides(String prefix, String postfix) {
            this.prefix = prefix;
            this.postfix = postfix;
        }

        public String getPostfix(String deFault) {
            if (postfix == null) {
                return deFault;
            }
            return postfix;
        }

        public String getPrefix(String deFault) {
            if (prefix == null) {
                return deFault;
            }
            return prefix;
        }
    }


    @Override
    protected String getFormatFor(II_Material material, MatterState fluidState) {
        String out = "%s";
        MatterState normal = material.getNormalForm();
        if (normal.isSolid() && fluidState.isLiquid()) {
            out = "Molten %s";
        } else if (normal.isGaseous() && fluidState.isLiquid()) {
            out = "Liquefied %s";
        } else if (fluidState.isPlasma()) {
            out = "%s Plasma";
        } else if (fluidState.isGaseous()) {
            out = "%s Gas";//todo add blackList
        }
        return out;
    }
}
