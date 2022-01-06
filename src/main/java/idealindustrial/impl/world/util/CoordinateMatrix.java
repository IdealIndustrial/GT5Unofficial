package idealindustrial.impl.world.util;

import idealindustrial.api.world.util.ICoordManipulable;
import idealindustrial.api.world.util.ICoordManipulator;
import idealindustrial.api.world.util.MatrixCoordConsumer;

import java.util.List;

public class CoordinateMatrix<M, T extends MatrixCoordConsumer<M>> implements ICoordManipulable<M> {

    //x, y, z
    protected T[][][] matrix;
    protected Vector3 center;
    protected List<ChildEntry<M, ICoordManipulable<M>>> subMatrices;


    @Override
    public ICoordManipulator<M> getManipulator() {
        return new ICoordManipulator<M>() {
            final Vector3 start = new Vector3();
            int rotation = 0;
            boolean xInverted = false, zInverted = false;

            @Override
            public void start(M mode) {
//                if (rotation % 2 == 1) {
//                    boolean t = xInverted;
//                    xInverted = zInverted;
//                    zInverted = t;
//                }
                Vector3 newCenter = new Vector3(xInverted ? matrix.length - center.x - 1 : center.x,
                        center.y,
                        zInverted ? matrix[0][0].length - center.z - 1 : center.z);
                Vector3d rotationCenter = new Vector3d(0, 0, 0);
                start.addm(newCenter.invert().rotateY(rotation, rotationCenter));
                for (int x = 0; x < matrix.length; x++) {
                    for (int y = 0; y < matrix[0].length; y++) {
                        for (int z = 0; z < matrix[0][0].length; z++) {
                            T element = matrix[xInverted ? matrix.length - x - 1 : x][y][zInverted ? matrix[0][0].length - z - 1 : z];
                            Vector3 position = new Vector3(x, y, z);
                            position.rotateYm(rotation, rotationCenter);
                            element.apply(mode, position.addm(start), rotation);
                        }
                    }
                }
                for (ChildEntry<M, ICoordManipulable<M>> entry : subMatrices) {
                    Vector3 position = entry.position;
                    position.rotateYm(rotation + entry.rotation, rotationCenter);
                    ICoordManipulator<M> manipulator = entry.element.getManipulator();
                    manipulator.rotateY(entry.rotation + rotation);
                    manipulator.move(position.addm(start));
                    manipulator.start(mode);
                }
            }

            @Override
            public BoundingBox getBox() {
                Vector3 newCenter = new Vector3(xInverted ? matrix.length - center.x - 1 : center.x,
                        center.y,
                        zInverted ? matrix[0][0].length - center.z - 1 : center.z);

                BoundingBox.BoxBuilder builder = BoundingBox.builder();
                Vector3d rotationCenter = new Vector3d(0, 0, 0);
                start.addm(newCenter.invert().invert(xInverted, false, zInverted).rotateY(rotation, rotationCenter));

                for (int x : new int[]{0, matrix.length - 1}) {
                    for (int y : new int[]{0, matrix[0].length - 1}) {
                        for (int z : new int[]{0, matrix[0][0].length - 1}) {
                            Vector3 position = new Vector3(x, y, z);
                            position.rotateYm(rotation, rotationCenter);
                            position.addm(start);
                            builder.addPosition(position);
                        }
                    }
                }

                for (ChildEntry<M, ICoordManipulable<M>> entry : subMatrices) {
                    Vector3 position = entry.position;
                    position.rotateYm(rotation + entry.rotation, rotationCenter);
                    position.addm(start);
                    builder.addPosition(position);
                }
                return builder.getBox();
            }

            @Override
            public void move(Vector3 vector) {
                start.addm(vector);
            }

            @Override
            public void rotateY(int angle) {
                rotation += angle;
            }

            public void invert(boolean x, boolean z) {
                if (x) {
                    xInverted = !xInverted;
                }
                if (z) {
                    zInverted = !zInverted;
                }
            }


        };
    }

    static class ChildEntry<M, T extends ICoordManipulable<M>> {
        Vector3 position;
        int rotation;
        T element;
    }

}
