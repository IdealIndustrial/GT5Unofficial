package idealindustrial.util.json;

import com.google.gson.*;
import idealindustrial.recipe.II_MachineEnergyParams;
import idealindustrial.recipe.II_RecipeEnergyParams;

import java.lang.reflect.Type;

public class JsonRecipeEnergyParamsSerializer implements JsonSerializer<II_RecipeEnergyParams>, JsonDeserializer<II_RecipeEnergyParams> {
    @Override
    public II_RecipeEnergyParams deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        return new II_RecipeEnergyParams(jsonObject.get("voltage").getAsLong(), jsonObject.get("amperage").getAsLong(), jsonObject.get("duration").getAsLong());
    }

    @Override
    public JsonElement serialize(II_RecipeEnergyParams src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.addProperty("voltage", src.voltage);
        object.addProperty("amperage", src.amperage);
        object.addProperty("duration", src.duration);
        return object;
    }
}
