package idealindustrial.api.world.underbedrock;

import java.util.Random;

public interface VeinProvider<T> {

    Vein<T> provide(Random random, GridGenerationRules<T> rules);
}
