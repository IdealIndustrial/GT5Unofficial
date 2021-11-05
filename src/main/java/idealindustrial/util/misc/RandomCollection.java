package idealindustrial.util.misc;

import idealindustrial.api.world.underbedrock.WeightedRandom;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomCollection<E> implements WeightedRandom<E> {

    private final List<RandomEntry> randomEntries = new ArrayList<>();
    private final double weightLowering;

    public RandomCollection(double weightLowering) {
        this.weightLowering = weightLowering;
    }


    public RandomCollection<E> add(double weight, E result) {
        if (weight <= 0) return this;
        randomEntries.add(new RandomEntry(weight, result));
        return this;
    }

    public E next(Random random) {
        return next(random, 1, 2);
    }

    public E next(Random random, int x, int y) {
        RandomEntry next = get(random);
        if (weightLowering != 1d) {
            next.setWeight(next.getWeight() * weightLowering);
        }
        return next.getValue();
    }

    private RandomEntry get(Random random) {
        double completeWeight = 0.0;
        for (RandomEntry randomEntry : randomEntries)
            completeWeight += randomEntry.getWeight();
        double r = random.nextDouble() * completeWeight;
        double countWeight = 0.0;
        for (RandomEntry randomEntry : randomEntries) {
            countWeight += randomEntry.getWeight();
            if (countWeight >= r)
                return randomEntry;
        }
        throw new RuntimeException("Should never be shown.");
    }

    public RandomCollection<E> copy() {
        RandomCollection<E> ret = new RandomCollection<>(weightLowering);
        for (RandomEntry entry : randomEntries) {
            ret.add(entry.getWeight(), entry.getValue());
        }
        return ret;
    }

    public int size() {
        return randomEntries.size();
    }

    class RandomEntry {
        double weight;
        E value;

        public RandomEntry(double weight, E value) {
            this.weight = weight;
            this.value = value;
        }

        public double getWeight() {
            return weight;
        }

        public E getValue() {
            return value;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }
    }

}
