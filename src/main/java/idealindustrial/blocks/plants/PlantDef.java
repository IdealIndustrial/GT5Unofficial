package idealindustrial.blocks.plants;

import idealindustrial.textures.*;
import idealindustrial.util.item.II_ItemStack;
import idealindustrial.util.misc.BlockPredicate;
import idealindustrial.util.misc.II_Paths;
import idealindustrial.util.misc.RandomCollection;
import idealindustrial.util.world.underbedrock.WeightedRandom;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.Random;
import java.util.function.BiPredicate;

public class PlantDef {

    public String name;
    public int id, maxLevel = 3, levelAfterHarvest = 1;
    public int[] xpPerLevel;
    public int minerals = 1, mineralConsumption = 1;
    public ITexture[] textures;
    public WeightedRandom<II_ItemStack> drops = new RandomCollection<>(1d);
    public BiomeStats biome = new BiomeStats(BiomeGenBase.plains);
    public boolean canSpread, superFastGrowth;
    public RenderType renderType = RenderType.Cross;
    public int additionalGrowthChance = 7001; //over 9000 thing
    public BlockPredicate allowedSoil = (b, m) -> b == Blocks.grass || b == Blocks.dirt || b == Blocks.farmland;

    public PlantDef mutate(int mutateFactor, Random random) {
        return this;
    }

    public PlantStats getDefaultStats() {
        return new PlantStats(true, biome.copy(), 3, 3);
    }

    public PlantDef(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public PlantDef setLevels(int maxLevel, int[] xps, int levelAfterHarvest) {
        this.maxLevel = maxLevel;
        this.xpPerLevel = xps;
        this.levelAfterHarvest = levelAfterHarvest;
        return this;
    }

    public static class TextureConfigurationPlant implements TextureConfiguration {
        int levels;

        public TextureConfigurationPlant(int levels) {
            this.levels = levels;
        }

        @Override
        public IconContainer[] loadAll(String prefixPath) {
            IconContainer[] out = new IconContainer[levels];
            for (int i = 0; i < levels; i++) {
                out[i] = TextureManager.INSTANCE.blockTexture(prefixPath + (i + 1));
            }
            return out;
        }
    }

    public PlantDef setTextures(RenderType type) {
        textures = TextureUtil.loadTextures(new TextureConfigurationPlant(maxLevel + 1), "plants/" + name + "/");
        renderType = type;
        return this;
    }


    public enum RenderType {
        Cross, //like X from top
        Sharp; // like # from top
    }


}
