package idealindustrial.util.misc;

import idealindustrial.api.world.underbedrock.WeightedRandom;

import java.util.*;

public class RandomCollection<E> implements WeightedRandom<E> {

    private final NavigableMap<Double, E> map = new TreeMap<>();
    private double total = 0;

    public RandomCollection() {
    }


    public RandomCollection<E> add(double weight, E result) {
        if (weight <= 0) return this;
        total += weight;
        map.put(total, result);
        return this;
    }

    public E next(Random random) {
        double value = random.nextDouble() * total;
        return map.higherEntry(value).getValue();
    }

    public int size() {
        return map.size();
    }


}
