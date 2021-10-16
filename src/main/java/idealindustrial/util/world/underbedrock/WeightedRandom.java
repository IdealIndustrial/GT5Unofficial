package idealindustrial.util.world.underbedrock;

import java.util.Random;

public interface WeightedRandom<T> {

    WeightedRandom<T> copy();

    T next(Random random);
}
