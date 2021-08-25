package idealindustrial.util.json;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class II_ArrayObject<T> {
    public T[] contents;

    public II_ArrayObject(T[] contents) {
        this.contents = contents;
    }

    public II_ArrayObject() {

    }

    public static ParameterizedType getType(Class<?> subtype) {
        return new ParameterizedType() {
            @Override
            public Type[] getActualTypeArguments() {
                return new Type[]{subtype};
            }

            @Override
            public Type getRawType() {
                return II_ArrayObject.class;
            }

            @Override
            public Type getOwnerType() {
                return null;
            }
        };
    }
}
