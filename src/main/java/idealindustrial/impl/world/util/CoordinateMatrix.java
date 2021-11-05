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

            @Override
            public void start(M mode) {
                Vector3d rotationCenter = new Vector3d(0, 0, 0);
                start.addm(center.invert().rotateY(rotation, rotationCenter));
                for (int x = 0; x < matrix.length; x++) {
                    for (int y = 0; y < matrix[0].length; y++) {
                        for (int z = 0; z < matrix[0][0].length; z++) {
                            T element = matrix[x][y][z];
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
                BoundingBox.BoxBuilder builder = BoundingBox.builder();
                Vector3d rotationCenter = new Vector3d(0, 0, 0);
                start.addm(center.invert().rotateY(rotation, rotationCenter));

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

        };
    }

    static class ChildEntry<M, T extends ICoordManipulable<M>> {
        Vector3 position;
        int rotation;
        T element;
    }

}
