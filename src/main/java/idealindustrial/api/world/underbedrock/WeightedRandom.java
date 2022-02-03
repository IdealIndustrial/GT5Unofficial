package idealindustrial.api.world.underbedrock;

import java.util.Random;

public interface WeightedRandom<T> {

    T next(Random random);

    WeightedRandom<T> add(double weight, T result);

    int size();
}
