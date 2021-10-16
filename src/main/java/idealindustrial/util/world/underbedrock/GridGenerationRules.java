package idealindustrial.util.world.underbedrock;

import java.util.Random;

public interface GridGenerationRules<T> {

    int getPassCount();
    WeightedRandom<VeinProvider<T>> getProviderForPass(int pass);
    OrderGenerator getOrder(int size, Random random);
    int getGridSize();
    int getClusterSize();

}
