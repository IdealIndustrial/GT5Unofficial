/*
 *  Copyright (c) 2014, Lukas Tenbrink.
 *  * http://lukas.axxim.net
 */

package idealindustrial.util.json;

import com.google.gson.*;
import net.minecraft.nbt.NBTBase;

import java.lang.reflect.Type;

/**
 * Created by lukas on 30.05.14.
 */
public class NBTBaseSerializerSmart implements JsonSerializer<NBTBase>, JsonDeserializer<NBTBase>
{
    @Override
    public NBTBase deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        Class<? extends NBTBase> aClass = NbtToJson.getNBTTypeSmart(json);

        if (aClass != null)
            return NbtToJson.nbtJson.fromJson(json, aClass);
        else
            throw new JsonParseException("Unknown NBT type");
    }

    @Override
    public JsonElement serialize(NBTBase src, Type typeOfSrc, JsonSerializationContext context)
    {
        return NbtToJson.nbtJson.toJsonTree(src, typeOfSrc);
    }
}
