package idealindustrial.util.world.underbedrock.impl;


import idealindustrial.util.world.underbedrock.OrderGenerator;

public class SequentialOrder implements OrderGenerator {

    private final int size;
    private int current;

    public SequentialOrder(int size) {
        this.size = size;
        this.current = 0;
    }

    @Override
    public boolean hasNext() {
        return current < size;
    }

    @Override
    public int next() {
        return current++;
    }
}
