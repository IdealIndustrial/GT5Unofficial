package idealindustrial.impl.world.underbedrock;

import idealindustrial.api.world.underbedrock.DifferenceSupplier;
import idealindustrial.api.world.underbedrock.Vein;

public class CircleVeinProvider<T> extends SquareVeinProvider<T> {
    public CircleVeinProvider(DifferenceSupplier<T> supplier, long minQuantity, long maxQuantity) {
        super(supplier, minQuantity, maxQuantity);
    }

    @Override
    protected Vein<T> init(int globalX, int globalZ, long[][] vein) {
        int size = vein.length;
        double center = (size - 1) / 2d;
        double radSq = center * center;
        T[][] ar = UnderbedrockUtil.convert(vein, supplier);
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                double val = (x - center) * (x - center) + (y - center) * (y - center);
                if (val > radSq) {
                    ar[x][y] = null;
                }
            }
        }
        return new SquareVein<>(globalX, globalZ, ar);
    }
}
