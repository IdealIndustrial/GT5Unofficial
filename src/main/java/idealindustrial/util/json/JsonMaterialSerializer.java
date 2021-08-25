package idealindustrial.util.json;

import com.google.gson.*;
import idealindustrial.autogen.material.II_Material;
import idealindustrial.autogen.material.II_Materials;

import java.lang.reflect.Type;

public class JsonMaterialSerializer implements JsonSerializer<II_Material>, JsonDeserializer<II_Material> {
    @Override
    public II_Material deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return II_Materials.materialForName(json.getAsString());
    }

    @Override
    public JsonElement serialize(II_Material src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.name().toLowerCase());
    }
}
