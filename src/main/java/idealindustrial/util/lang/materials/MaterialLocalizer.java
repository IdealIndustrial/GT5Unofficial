package idealindustrial.util.lang.materials;

import idealindustrial.autogen.material.II_Material;
import idealindustrial.autogen.material.Prefixes;
import idealindustrial.autogen.material.submaterial.MatterState;

public interface MaterialLocalizer {

    String get(II_Material material, Prefixes prefix);

    String get(II_Material material, MatterState liquidState);
}
