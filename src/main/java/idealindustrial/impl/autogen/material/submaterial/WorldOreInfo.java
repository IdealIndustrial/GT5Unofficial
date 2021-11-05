package idealindustrial.impl.autogen.material.submaterial;

import idealindustrial.impl.autogen.material.Prefixes;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Function;

public class WorldOreInfo {
    public Function<Random, ArrayList<ItemStack>> dropsFunction;
    public Function<Random, ArrayList<ItemStack>> smallDropsFunction;

    public WorldOreInfo(Function<Random, ArrayList<ItemStack>> dropsFunction, Function<Random, ArrayList<ItemStack>> smallDropsFunction) {
        this.dropsFunction = dropsFunction;
        this.smallDropsFunction = smallDropsFunction;
    }

    public ArrayList<ItemStack> apply(Prefixes prefix, Random rand) {
        if (prefix == Prefixes.ore) {
            return dropsFunction.apply(rand);
        }
        else if (prefix == Prefixes.oreSmall) {
            return smallDropsFunction.apply(rand);
        }
        return new ArrayList<>();
    }
}
