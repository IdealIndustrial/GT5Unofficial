package idealindustrial.impl.world.underbedrock;

import idealindustrial.api.world.underbedrock.DifferenceSupplier;
import idealindustrial.api.world.underbedrock.GridGenerationRules;
import idealindustrial.api.world.underbedrock.Vein;
import idealindustrial.api.world.underbedrock.VeinProvider;
import idealindustrial.util.misc.II_Util;

import java.util.Arrays;
import java.util.Random;

public class SquareVeinProvider<T> implements VeinProvider<T> {

    protected final DifferenceSupplier<T> supplier;
    protected double exp = 1;
    protected int minSquareSize = 1, maxSquareSize = 1;
    protected final long minQuantity, maxQuantity;
    protected double randomInnerDifference = 0;
    protected double centerBonus = 0;
    protected int positionBoundLeftUp = 0, positionBoundRightDown = -1;

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
        return init(x, z, vein);
    }

    protected Vein<T> init(int x, int z, long[][] vein) {
        return new SquareVein<>(x, z, UnderbedrockUtil.convert(vein, supplier));
    }

    //pretty unsafe, choose balanceCoefficient wisely
    private long calculateAmount(Random random) {
        if (exp == 1) {
            return II_Util.randomBetween(minQuantity, maxQuantity, random);
        }
        return Math.min(minQuantity + (long) ((maxQuantity - minQuantity) * Math.log(1 - random.nextDouble()) / (-exp)), maxQuantity);
    }

    private double distance(double x1, double z1, double x2, double z2) {
        return Math.pow(Math.pow(x1 - x2, 2) + Math.pow(z1 - z2, 2), 0.5);
    }

    public void setExponentialDifference(double exp) {
        this.exp = exp;
    }


    public void setInnerDifference(double difference) {
        this.randomInnerDifference = difference;
    }

    public void setCenterBonus(double bonus) {
        this.centerBonus = bonus;
    }

    public void setOffsets(int leftUp, int rightDown) {
        this.positionBoundLeftUp = leftUp;
        this.positionBoundRightDown = rightDown;
    }

    public void setSizes(int min, int max) {
        this.minSquareSize = min;
        this.maxSquareSize = max + 1;
        if (positionBoundRightDown == -1) {
            this.positionBoundRightDown = max;
        }
    }

    //region: test
    public static void main(String[] args) {
//        SquareVeinProvider<Long> provider = new SquareVeinProvider<>(i -> i, 10, 100).setSizes(1, 3).setExponentialDifference(7);
//        GridGenerationRulesWeighted<Long> rules = new GridGenerationRulesWeighted<>(5, 5, 1, true);
//        Random random = new Random();
//        long sum = 0;
//        long tests = 10000;
//        for (int i = 0; i < tests; i++) {
//            Object[][] o = provider.provide(random, rules).get();
//            sum += ((Long) o[0][0]);
//            if (((Long) o[0][0]) > 90) {
//                print(o);
//                System.out.println();
//            }
//        }
//        System.out.println("Avg: " + sum / tests);
    }

    private static void print(Object[][] ar) {
        for (Object[] a : ar) {
            System.out.println(Arrays.toString(a));
        }
    }
//endregion
}
