package idealindustrial.util.lang.materials;

import idealindustrial.itemgen.material.II_Material;
import idealindustrial.itemgen.material.Prefixes;
import idealindustrial.itemgen.material.submaterial.MatterState;

public interface MaterialLocalizer {

    String get(II_Material material, Prefixes prefix);

    String get(II_Material material, MatterState liquidState);
}
