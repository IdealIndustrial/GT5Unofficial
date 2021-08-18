package idealindustrial.util.json;

import com.google.gson.GsonBuilder;
import idealindustrial.recipe.II_MachineEnergyParams;
import idealindustrial.util.item.II_StackSignature;
import net.minecraftforge.fluids.FluidStack;

public class II_JsonUtil {

    public static GsonBuilder getRecipeGson() {
        GsonBuilder builder = new GsonBuilder();
        NbtToJson.registerSmartNBTSerializer(builder);
        builder.registerTypeAdapter(II_StackSignature.class, new JsonStackSignatureSerializer());
        builder.registerTypeAdapter(FluidStack.class, new FluidStackSerializer());
        builder.registerTypeAdapter(II_MachineEnergyParams.class, new MachineParamsSerializer());
        return builder;
    }
}
