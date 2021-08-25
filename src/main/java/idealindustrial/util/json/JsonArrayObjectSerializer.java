package idealindustrial.util.json;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

public class JsonArrayObjectSerializer<T> implements JsonSerializer<II_ArrayObject<T>>, JsonDeserializer<II_ArrayObject<T>> {

    @Override
    public II_ArrayObject<T> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        assert typeOfT instanceof ParameterizedType;
        ParameterizedType parameterizedType = (ParameterizedType) typeOfT;

        return null;
    }

    @Override
    public JsonElement serialize(II_ArrayObject<T> src, Type typeOfSrc, JsonSerializationContext context) {
        return null;
    }

    protected static class Strange {
        int a = 0;
        int b = 13;
        int c = 1234;

        @Override
        public String toString() {
            return "Strange{" +
                    "a=" + a +
                    ", b=" + b +
                    ", c=" + c +
                    '}';
        }
    }

    public static void main(String[] args) {
        String[] ar = new String[]{"a", "b", "C"};
        II_ArrayObject<Strange> object = new II_ArrayObject<>(new Strange[]{new Strange()});
        Gson gson = new GsonBuilder().create();
        System.out.println(gson.toJson(object));
        System.out.println(load(Strange.class));
    }

    private static <T extends Strange> II_ArrayObject<T> load(Class<T> clazz) {
        Gson gson = new GsonBuilder().create();
        String json = "{\"contents\":[{\"a\":0,\"b\":13,\"c\":125434}]}";
        II_ArrayObject<T> object = gson.fromJson(json, new TypeToken<II_ArrayObject<T>>(){}.getType());
        System.out.println(Arrays.toString(object.contents));
        return object;
    }
}
