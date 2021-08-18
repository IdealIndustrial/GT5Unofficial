package idealindustrial.util.json;

import com.google.gson.*;
import idealindustrial.recipe.II_MachineEnergyParams;

import java.lang.reflect.Type;

public class MachineParamsSerializer implements JsonSerializer<II_MachineEnergyParams>, JsonDeserializer<II_MachineEnergyParams> {
    @Override
    public II_MachineEnergyParams deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        return new II_MachineEnergyParams(jsonObject.get("tier").getAsInt(), jsonObject.get("voltage").getAsLong(), jsonObject.get("amperage").getAsLong());
    }

    @Override
    public JsonElement serialize(II_MachineEnergyParams src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.addProperty("tier", src.tier);
        object.addProperty("voltage", src.voltage);
        object.addProperty("amperage", src.amperage);
        return object;
    }
}
