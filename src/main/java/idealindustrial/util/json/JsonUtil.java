package idealindustrial.util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import idealindustrial.impl.recipe.RecipeEnergyParams;
import idealindustrial.impl.item.stack.II_ItemStack;
import idealindustrial.impl.item.stack.II_StackSignature;
import net.minecraftforge.fluids.FluidStack;

public class JsonUtil {

    public static final Gson recipeDefaultGson = getRecipeGson().create();

    public static GsonBuilder getRecipeGson() {
        GsonBuilder builder = new GsonBuilder();
        NbtToJson.registerSmartNBTSerializer(builder);
        builder.registerTypeAdapter(II_StackSignature.class, new JsonStackSignatureSerializer());
        builder.registerTypeAdapter(FluidStack.class, new FluidStackSerializer());
        builder.registerTypeAdapter(RecipeEnergyParams.class, new JsonRecipeEnergyParamsSerializer());
        builder.registerTypeAdapter(II_ItemStack.class, new JsonIIStackSerializer());
        return builder;
    }
}
