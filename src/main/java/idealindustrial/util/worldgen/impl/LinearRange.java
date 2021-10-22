package idealindustrial.util.worldgen.impl;

import idealindustrial.util.misc.II_Util;
import idealindustrial.util.worldgen.util.Range;

import java.util.Random;

public class LinearRange implements Range {
    int min, max;

    public LinearRange(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public int get(Random random) {
        return II_Util.randomBetween(min, max, random);
    }
}
