package idealindustrial.impl.world.underbedrock;


import idealindustrial.util.misc.RandomCollection;
import idealindustrial.api.world.underbedrock.GridGenerationRules;
import idealindustrial.api.world.underbedrock.OrderGenerator;
import idealindustrial.api.world.underbedrock.VeinProvider;
import idealindustrial.api.world.underbedrock.WeightedRandom;

import java.util.Random;

public class GridGenerationRulesWeighted<T> implements GridGenerationRules<T> {

    private final RandomCollection<VeinProvider<T>>[] collection;
    private final int gridSize, clusterSize;
    private final boolean straitOrder;

    public GridGenerationRulesWeighted(int gridSize, int clusterSize, int passCount, boolean straitOrder) {
        this.collection = new RandomCollection[passCount];
        this.gridSize = gridSize;
        this.clusterSize = clusterSize;
        this.straitOrder = straitOrder;
    }

    @Override
    public int getPassCount() {
        return collection.length;
    }

    @Override
    public WeightedRandom<VeinProvider<T>> getProviderForPass(int pass) {
        return collection[pass];
    }

    @Override
    public OrderGenerator getOrder(int size, Random random) {
        if (straitOrder) {
            return new SequentialOrder(size);
        }
        else {
            return new RandomOrder(size, random);
        }
    }

    @Override
    public int getGridSize() {
        return gridSize;
    }

    @Override
    public int getClusterSize() {
        return clusterSize;
    }

    public GridGenerationRules<T> setRandomForPass(int pass, RandomCollection<VeinProvider<T>> randomCollection) {
        collection[pass] = randomCollection;
        return this;
    }
}
