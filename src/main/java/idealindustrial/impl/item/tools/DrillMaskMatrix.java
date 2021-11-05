package idealindustrial.impl.item.tools;

import idealindustrial.impl.world.util.CoordinateMatrix;
import idealindustrial.api.world.util.MatrixCoordConsumer;
import idealindustrial.impl.world.util.Vector3;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class DrillMaskMatrix extends CoordinateMatrix<DrillMaskMatrix.Action, MatrixCoordConsumer<DrillMaskMatrix.Action>> {

    int radius;

    @SuppressWarnings("unchecked")
    public DrillMaskMatrix(int radius, boolean vertical) {
        this.radius = radius;
        this.subMatrices = new ArrayList<>();
        if (vertical) {
            this.matrix = new MatrixCoordConsumer[radius * 2 + 1][radius * 2 + 1][1];
            initMatrix();
            this.center = new Vector3(radius, 1, 0);
        }
        else {
            this.matrix = new MatrixCoordConsumer[radius * 2 + 1][1][radius * 2 + 1];
            initMatrix();
            this.center = new Vector3(radius, 0, radius);
        }
    }

    protected void initMatrix() {
        for (int x = 0; x < matrix.length; x++) {
            for (int y = 0; y < matrix[0].length; y++) {
                for (int z = 0; z < matrix[0][0].length; z++) {
                    matrix[x][y][z] = (action, position, rotation) -> {
                        action.eval(position);
                    };
                }
            }
        }
    }

    public interface Action {
        void eval(Vector3 position);
    }
}
