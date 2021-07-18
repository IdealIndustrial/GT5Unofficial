package idealindustrial.util.misc;

public class DefaultableImpl<T> implements Defaultable<T> {

    T defaultValue;
    T value;

    public DefaultableImpl(T defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public T get() {
        return value == null ? defaultValue : value;
    }

    @Override
    public void set(T o) {
        value = o;
    }

    @Override
    public boolean isSet() {
        return value != null;
    }
}
