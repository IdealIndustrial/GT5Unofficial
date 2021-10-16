package idealindustrial.util.world.underbedrock;


import idealindustrial.util.world.underbedrock.impl.BoxCollider;

public interface Vein<T> {

    BoxCollider getCollider();

    T[][] get();
}
