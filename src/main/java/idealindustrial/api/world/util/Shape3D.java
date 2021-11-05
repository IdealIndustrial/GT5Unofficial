package idealindustrial.api.world.util;

import idealindustrial.impl.world.util.BoundingBox;
import idealindustrial.impl.world.util.Vector3;

public interface Shape3D {

    boolean contains(Vector3 pos);

    BoundingBox getBoundingBox();

    default boolean contains(Vector3 pos, Vector3 shapeCenter) {
        return contains(pos.minus(shapeCenter));
    }

    default BoundingBox getBoundingBox(Vector3 shapeCenter) {
        return getBoundingBox().move(shapeCenter);
    }

    default double getModifier(Vector3 pos) {
        return 1D;
    }

    default void forEach(Vector3 center, CoordConsumer coordConsumer) {
        BoundingBox box = getBoundingBox(center);
        box.forEach(vec -> {
            if (contains(vec, center)) {
                coordConsumer.apply(vec);
            }
        });
    }
}
