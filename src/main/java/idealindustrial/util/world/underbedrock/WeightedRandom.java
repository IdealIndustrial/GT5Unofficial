package idealindustrial.util.world.underbedrock;

import idealindustrial.util.misc.RandomCollection;

import java.util.Random;

public interface WeightedRandom<T> {

    WeightedRandom<T> copy();

    T next(Random random);

    WeightedRandom<T> add(double weight, T result);
}
