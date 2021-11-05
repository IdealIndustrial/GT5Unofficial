package idealindustrial.impl.world.underbedrock;

import idealindustrial.api.world.underbedrock.OrderGenerator;

import java.util.Random;
import java.util.stream.IntStream;

public class RandomOrder implements OrderGenerator {

    private final int[] order;
    private int i;

    public RandomOrder(int size, Random random) {
        this.order = IntStream.iterate(0, i -> i + 1).limit(size).toArray();
        for (int i = 0; i < size; i++) {
            int a = random.nextInt(size), b = random.nextInt(size);
            int t = order[a];
            order[a] = order[b];
            order[b] = t;
        }
        i = 0;
    }

    @Override
    public boolean hasNext() {
        return i < order.length;
    }

    @Override
    public int next() {
        return order[i++];
    }
}
