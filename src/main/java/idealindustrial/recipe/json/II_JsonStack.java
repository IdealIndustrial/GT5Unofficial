package idealindustrial.recipe.json;

import net.minecraft.nbt.NBTTagCompound;

public class II_JsonStack {

    public static class OreDict {
        String material;
        String prefix;
        int amount;
    }

    public static class Direct {
        String mod;
        String name;
        int amount;
    }

    public static class DirectWithNBT {
        NBTTagCompound nbtTagCompound;
        int id;
        int damage;
        int amount;
    }
}
