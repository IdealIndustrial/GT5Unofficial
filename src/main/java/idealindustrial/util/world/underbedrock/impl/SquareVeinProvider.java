package idealindustrial.util.world.underbedrock.impl;

import idealindustrial.util.misc.II_Util;
import idealindustrial.util.world.underbedrock.GridGenerationRules;
import idealindustrial.util.world.underbedrock.Vein;
import idealindustrial.util.world.underbedrock.VeinProvider;

import java.util.Arrays;
import java.util.Random;

public class SquareVeinProvider<T> implements VeinProvider<T> {

    private final DifferenceSupplier<T> supplier;
    private double exp = 1;
    private int minSquareSize = 1, maxSquareSize = 1;
    private final long minQuantity, maxQuantity;
    private double randomInnerDifference = 0;
    private double centerBonus = 0;
    private int positionBoundLeftUp = 0, positionBoundRightDown = -1;

    public SquareVeinProvider(DifferenceSupplier<T> supplier, long minQuantity, long maxQuantity) {
        this.supplier = supplier;
        this.minQuantity = minQuantity;
        this.maxQuantity = maxQuantity;
    }

    @Override
    public Vein<T> provide(Random random, GridGenerationRules<T> rules) {
        int size = II_Util.randomBetween(minSquareSize, maxSquareSize, random);
        long[][] vein = new long[size][size];
        long globalQuantity = calculateAmount(random);
        UnderbedrockUtil.apply(vein, i -> globalQuantity);
        if (centerBonus != 0) {
            double centerCoord = (size - 1) / 2d;
            double maxDistance = distance(centerCoord, centerCoord, 0, 0);
            UnderbedrockUtil.apply(vein, (x, z, amount) -> {
                double bonus = centerBonus - distance(centerCoord, centerCoord, x, z) * centerBonus / maxDistance;
                return amount + (long) (amount * bonus);
            });
        }
        if (randomInnerDifference != 0) {
            UnderbedrockUtil.apply(vein, i -> i + (long) (i * II_Util.randomBetween(-randomInnerDifference, randomInnerDifference, random)));
        }
        int x = II_Util.randomBetween(positionBoundLeftUp, rules.getGridSize() - positionBoundRightDown, random),
                z = II_Util.randomBetween(positionBoundLeftUp, rules.getGridSize() - positionBoundRightDown, random);
        return new SquareVein<>(x, z,UnderbedrockUtil.convert(vein, supplier));
    }

    //pretty unsafe, choose balanceCoefficient wisely
    private long calculateAmount(Random random) {
        if (exp == 1) {
            return II_Util.randomBetween(minQuantity, maxQuantity, random);
        }
        return Math.min(minQuantity + (long)((maxQuantity - minQuantity) *  Math.log(1-random.nextDouble())/(-exp)) , maxQuantity);
    }

    private double distance(double x1, double z1, double x2, double z2) {
        return Math.pow(Math.pow(x1 - x2, 2) + Math.pow(z1 - z2, 2), 0.5);
    }

    public SquareVeinProvider<T> setExponentialDifference(double exp) {
        this.exp = exp;
        return this;
    }


    public SquareVeinProvider<T> setInnerDifference(double difference) {
        this.randomInnerDifference = difference;
        return this;
    }

    public SquareVeinProvider<T> setCenterBonus(double bonus) {
        this.centerBonus = bonus;
        return this;
    }

    public SquareVeinProvider<T> setOffsets(int leftUp, int rightDown) {
        this.positionBoundLeftUp = leftUp;
        this.positionBoundRightDown = rightDown;
        return this;
    }

    public SquareVeinProvider<T> setSizes(int min, int max) {
        this.minSquareSize = min;
        this.maxSquareSize = max + 1;
        if (positionBoundRightDown == -1) {
            this.positionBoundRightDown = max;
        }
        return this;
    }

//region: test
    public static void main(String[] args) {
        SquareVeinProvider<Long> provider = new SquareVeinProvider<>(i -> i, 10, 100).setSizes(1, 3).setExponentialDifference(7);
        GridGenerationRulesWeighted<Long> rules = new GridGenerationRulesWeighted<>(5, 5, 1, true);
        Random random = new Random();
        long sum = 0;
        long tests = 10000;
        for (int i = 0; i < tests; i++) {
            Object[][] o = provider.provide(random, rules).get();
            sum += ((Long) o[0][0]);
            if (((Long) o[0][0]) > 90) {
                print(o);
                System.out.println();
            }
        }
        System.out.println("Avg: " + sum / tests);
    }

    private static void print(Object[][] ar) {
        for (Object[] a : ar){
            System.out.println(Arrays.toString(a));
        }
    }
//endregion
}
