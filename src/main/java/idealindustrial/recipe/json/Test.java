package idealindustrial.recipe.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import idealindustrial.util.item.II_StackSignature;
import idealindustrial.util.json.JsonStackSignatureSerializer;
import idealindustrial.util.json.NbtToJson;

public class Test {

    public static void main(String[] args) {

    }

    public static II_StackSignature test(String str) {
        Gson gson = NbtToJson.registerSmartNBTSerializer(new GsonBuilder())
                .registerTypeAdapter(II_StackSignature.class, new JsonStackSignatureSerializer())
                .create();
        return gson.fromJson(str, II_StackSignature.class);
    }
}
