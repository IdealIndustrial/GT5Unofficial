package idealindustrial.impl.world;

import idealindustrial.api.world.underbedrock.GridGenerationRules;

public interface UnderbedrockLayerProvider {

    GridGenerationRules<UndergroundOre> provide(String dimName, int layer);
}
