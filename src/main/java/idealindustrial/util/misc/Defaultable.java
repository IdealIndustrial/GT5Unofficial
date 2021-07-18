package idealindustrial.util.misc;

public interface Defaultable<T> {

    T get();

    void set(T o);

    boolean isSet();

}