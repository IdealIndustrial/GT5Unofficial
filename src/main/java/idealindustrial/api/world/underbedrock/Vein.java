package idealindustrial.api.world.underbedrock;


import idealindustrial.impl.world.underbedrock.BoxCollider;

public interface Vein<T> {

    BoxCollider getCollider();

    T[][] get();
}
