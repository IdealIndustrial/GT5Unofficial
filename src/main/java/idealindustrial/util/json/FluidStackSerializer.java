package idealindustrial.util.json;

import com.google.gson.*;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.lang.reflect.Type;

public class FluidStackSerializer implements JsonSerializer<FluidStack>, JsonDeserializer<FluidStack> {

    @Override
    public JsonElement serialize(FluidStack src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.addProperty("fluid", src.getFluid().getName());
        object.addProperty("amount", src.amount);
        return object;
    }

    @Override
    public FluidStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        String fluid = object.get("fluid").getAsString();
        int amount = object.get("amount").getAsInt();
        return new FluidStack(FluidRegistry.getFluid(fluid), amount);
    }
}
