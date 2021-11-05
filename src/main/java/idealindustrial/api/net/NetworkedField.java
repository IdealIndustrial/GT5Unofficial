package idealindustrial.api.net;

public interface NetworkedField<T extends IPacketWriteable> {

    void set(T t);

    void accept(short t);

    T get();
}
