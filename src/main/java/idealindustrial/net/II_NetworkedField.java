package idealindustrial.net;

public interface II_NetworkedField<T extends IPacketWriteable> {

    void set(T t);

    void accept(short t);

    T get();
}
