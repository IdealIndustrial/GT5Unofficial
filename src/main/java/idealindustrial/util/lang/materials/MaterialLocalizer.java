package idealindustrial.util.lang.materials;

import idealindustrial.impl.autogen.material.II_Material;
import idealindustrial.impl.autogen.material.Prefixes;
import idealindustrial.impl.autogen.material.submaterial.MatterState;

public interface MaterialLocalizer {

    String get(II_Material material, Prefixes prefix);

    default String get(II_Material material) {
        return get(material, (Prefixes) null);
    }

    String get(II_Material material, MatterState liquidState);
}
