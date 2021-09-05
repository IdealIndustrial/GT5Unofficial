package idealindustrial.util.json;

import com.google.gson.*;
import idealindustrial.recipe.RecipeEnergyParams;

import java.lang.reflect.Type;

public class JsonRecipeEnergyParamsSerializer implements JsonSerializer<RecipeEnergyParams>, JsonDeserializer<RecipeEnergyParams> {
    @Override
    public RecipeEnergyParams deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        return new RecipeEnergyParams(jsonObject.get("voltage").getAsLong(), jsonObject.get("amperage").getAsLong(), jsonObject.get("duration").getAsLong());
    }

    @Override
    public JsonElement serialize(RecipeEnergyParams src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.addProperty("voltage", src.voltage);
        object.addProperty("amperage", src.amperage);
        object.addProperty("duration", src.duration);
        return object;
    }
}
