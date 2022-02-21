package idealindustrial.impl.world.underbedrock;

import idealindustrial.api.world.underbedrock.DifferenceSupplier;
import idealindustrial.api.world.underbedrock.Vein;

public class EllipsoidVeinProvider<T> extends SquareVeinProvider<T> {

    double a, b;

    public EllipsoidVeinProvider(DifferenceSupplier<T> supplier, long minQuantity, long maxQuantity) {
        super(supplier, minQuantity, maxQuantity);
    }

    @Override
    protected Vein<T> init(int globalX, int globalZ, long[][] vein) {
        int size = vein.length;
        double center = (size - 1) / 2d;
        T[][] ar = UnderbedrockUtil.convert(vein, supplier);
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                double val = (x - center) * (x - center) / (a * a) + (y - center) * (y - center) / (b * b);
                if (val > 1) {
                    ar[x][y] = null;
                }
            }
        }
        return new SquareVein<>(globalX, globalZ, ar);
    }

    public void setAB(double a, double b) {
        this.a = a;
        this.b = b;
    }
}
