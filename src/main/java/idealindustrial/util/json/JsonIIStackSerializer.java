package idealindustrial.util.json;

import com.google.gson.*;
import cpw.mods.fml.common.registry.GameRegistry;
import idealindustrial.autogen.oredict.II_OreDict;
import idealindustrial.autogen.oredict.II_OreInfo;
import idealindustrial.util.item.II_ItemStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.lang.reflect.Type;

public class JsonIIStackSerializer implements JsonSerializer<II_ItemStack>, JsonDeserializer<II_ItemStack> {

    @Override
    public JsonElement serialize(II_ItemStack src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.addProperty("mod", src.getUniqueID().modId);
        object.addProperty("name", src.getUniqueID().name);
        object.addProperty("damage", src.getDamageValue());
        if (src.getTagCompound() != null) {
            object.add("nbt", context.serialize(src.getTagCompound()));
        }
        object.addProperty("amount", src.amount);
        return object;
    }

    @Override
    public II_ItemStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        int amount = object.get("amount").getAsInt();
        NBTTagCompound nbtTagCompound = object.has("nbt") ? context.deserialize(object.get("nbt"), NBTTagCompound.class) : null;
        ItemStack is;
        if (object.has("ore")) {
            String ore = object.get("ore").getAsString();
            II_OreInfo info = II_OreDict.get(ore);
            if (info == null) {
                return null;
            }
            is = info.getMain().toItemStack(amount);
        }
        else {
            String mod = object.get("mod").getAsString();
            String name = object.get("name").getAsString();
            int damage = object.get("damage").getAsInt();
            is = new ItemStack(GameRegistry.findItem(mod, name), amount, damage);
        }
        is.setTagCompound(nbtTagCompound);
        return new II_ItemStack(is);//todo make properly constructor for II_Stack with nbt and without vanilla ItemStack
    }
}
