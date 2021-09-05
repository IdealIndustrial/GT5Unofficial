package idealindustrial.util.json;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ArrayObject<T> {
    public T[] contents;

    public ArrayObject(T[] contents) {
        this.contents = contents;
    }

    public ArrayObject() {

    }

    public static ParameterizedType getType(Class<?> subtype) {
        return new ParameterizedType() {
            @Override
            public Type[] getActualTypeArguments() {
                return new Type[]{subtype};
            }

            @Override
            public Type getRawType() {
                return ArrayObject.class;
            }

            @Override
            public Type getOwnerType() {
                return null;
            }
        };
    }
}
