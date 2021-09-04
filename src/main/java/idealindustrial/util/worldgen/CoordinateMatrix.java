package idealindustrial.util.worldgen;

import java.util.List;

public class CoordinateMatrix<M, T extends ICoordConsumer<M>> implements ICoordManipulable<M> {

    //x, y, z
    protected T[][][] matrix;
    protected Vector3 center;
    protected List<ChildEntry<M, ICoordManipulable<M>>> subMatrices;


    @Override
    public ICoordManipulator<M> getManipulator() {
        return new ICoordManipulator<M>() {
            Vector3 start = center;
            int rotation = 0;

            @Override
            public void start(M mode) {
                Vector3d rotationCenter = new Vector3d(matrix.length / 2d, matrix[0].length / 2d, matrix[0][0].length / 2d);
                for (int x = 0; x < matrix.length; x++) {
                    for (int y = 0; y < matrix[0].length; y++) {
                        for (int z = 0; z < matrix[0][0].length; z++) {
                            T element = matrix[x][y][z];
                            Vector3 position = new Vector3(x, y, z);
                            position.rotateY(rotation, rotationCenter);
                            element.apply(mode, position.add(start), rotation);
                        }
                    }
                }
                for (ChildEntry<M, ICoordManipulable<M>> entry : subMatrices) {
                    Vector3 position = entry.position;
                    position.rotateY(rotation + entry.rotation, rotationCenter);
                    ICoordManipulator<M> manipulator = entry.element.getManipulator();
                    manipulator.rotateY(entry.rotation + rotation);
                    manipulator.move(position.add(start));
                    manipulator.start(mode);
                }
            }

            @Override
            public void move(Vector3 vector) {
                start = start.add(vector);
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
