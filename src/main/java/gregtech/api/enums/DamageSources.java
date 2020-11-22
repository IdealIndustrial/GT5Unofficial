package gregtech.api.enums;

import gregtech.api.util.GT_Utility;
import net.minecraft.entity.EntityLivingBase;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public enum DamageSources {
    ELECTRIC(null, GT_Utility::isWearingFullElectroHazmat, "track.electric"),
    GAS(null, GT_Utility::isWearingFullGasHazmat),
    BIO(tag -> tag.startsWith("bee."), GT_Utility::isWearingFullBioHazmat),
    HEAT(null, GT_Utility::isWearingFullHeatHazmat),
    FROST(null, GT_Utility::isWearingFullFrostHazmat),
    RADIO(null, GT_Utility::isWearingFullRadioHazmat),
    TRAINS(null, GT_Utility::isWearingFullQuantumArmor, "train"),
    DUMMY(null, null);//no protection

    static HashMap<String, DamageSources> sources = new HashMap<>();

    public Set<String> tags;
    DamageSourceInterpreter interpreter;
    DamageSourceApplier applier;

    DamageSources(DamageSourceInterpreter interpreter, DamageSourceApplier applier, String... tags) {
        if (interpreter == null)
            interpreter = tag -> false;
        if (applier == null)
            applier = entity -> false;
        this.tags = new HashSet<>();
        this.tags.addAll(Arrays.asList(tags));
        this.applier = applier;
        this.interpreter = interpreter;
    }

    public boolean corresponds(String tag) {
        return tags.contains(tag) || interpreter.isSource(tag);
    }

    public boolean isProtected(EntityLivingBase entity) {
        return applier.isProtected(entity);
    }


    public static DamageSources determineSource(String tag) {
        DamageSources source = sources.get(tag);
        if (source == null || source == DUMMY) {
            for (DamageSources s : DamageSources.values()) {
                if (s.corresponds(tag)) {
                    sources.put(tag, s);
                    return s;
                }
            }
            sources.put(tag, DUMMY);
            return DUMMY;
        }
        else
            return source;

    }

    public static boolean isProtected(String source, EntityLivingBase entity) {
        return determineSource(source).isProtected(entity);
    }

    protected interface DamageSourceInterpreter {
        boolean isSource(String tag);
    }

    protected interface DamageSourceApplier {
        boolean isProtected(EntityLivingBase entity);
    }
}
