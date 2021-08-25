package idealindustrial.util.json;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ParamTest implements ParameterizedType {

    Class<?> myClass;
    @Override
    public Type[] getActualTypeArguments() {
        return new Type[0];
    }

    @Override
    public Type getRawType() {
        return myClass;
    }

    @Override
    public Type getOwnerType() {
        return null;
    }
}
