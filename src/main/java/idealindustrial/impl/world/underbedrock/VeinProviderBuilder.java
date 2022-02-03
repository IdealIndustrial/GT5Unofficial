package idealindustrial.impl.world.underbedrock;

import idealindustrial.api.world.underbedrock.DifferenceSupplier;
import idealindustrial.api.world.underbedrock.VeinProvider;

public class VeinProviderBuilder<T> {

    enum ProviderType {
        SQUARE {
            @Override
            <V> SquareVeinProvider<V> init(DifferenceSupplier<V> s, long min, long max) {
                return new SquareVeinProvider<>(s, min, max);
            }
        },
        CIRCLE {
            @Override
            <V> SquareVeinProvider<V> init(DifferenceSupplier<V> s, long min, long max) {
                return new CircleVeinProvider<>(s, min, max);
            }
        }
        ;

        abstract <V> SquareVeinProvider<V> init(DifferenceSupplier<V> s, long min, long max);
    }

    SquareVeinProvider<T> provider;

    public VeinProviderBuilder(ProviderType type, DifferenceSupplier<T> supplier, long minQuantity, long maxQuantity) {
        provider = type.init(supplier, minQuantity, maxQuantity);
    }

    public VeinProviderBuilder<T> setExponentialDifference(double exp) {
       provider.setExponentialDifference(exp);
       return this;
    }


    public VeinProviderBuilder<T> setInnerDifference(double difference) {
        provider.setInnerDifference(difference);
        return this;
    }

    public VeinProviderBuilder<T> setCenterBonus(double bonus) {
       provider.setCenterBonus(bonus);
       return this;
    }

    public VeinProviderBuilder<T> setOffsets(int leftUp, int rightDown) {
        provider.setOffsets(leftUp, rightDown);
        return this;
    }

    public VeinProviderBuilder<T> setSizes(int min, int max) {
        provider.setSizes(min, max);
        return this;
    }

    public VeinProvider<T> get() {
        return provider;
    }
}
