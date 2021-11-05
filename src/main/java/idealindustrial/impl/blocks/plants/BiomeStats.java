package idealindustrial.impl.blocks.plants;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenHell;

public class BiomeStats {

    public final Humidity humidity;
    public final Temperature temperature;

    public BiomeStats(Humidity humidity, Temperature temperature) {
        this.humidity = humidity;
        this.temperature = temperature;
    }

    public BiomeStats(BiomeGenBase biome) {
        this(Humidity.getFromBiome(biome), Temperature.getFromBiome(biome));
    }

    public BiomeStats copy() {
        return new BiomeStats(humidity, temperature);
    }

    public enum Humidity {
        ARID, NORMAL, DAMP;


        public static Humidity getFromValue(float rawHumidity) {
            if (rawHumidity > 0.85f) { // matches BiomeGenBase.isHighHumidity()
                return DAMP;
            }
            else if (rawHumidity >= 0.3f) {
                return NORMAL;
            }
            else {
                return ARID;
            }
        }

        public static Humidity getFromBiome(BiomeGenBase biome) {
            return getFromValue(biome.rainfall);
        }
    }

    public enum Temperature {
        HOT, WARM, NORMAL, COLD, ICY, HELLISH
        ;
        public static Temperature getFromValue(float rawTemp) {
            if (rawTemp > 1.00f) {
                return HOT;
            }
            else if (rawTemp > 0.85f) {
                return WARM;
            }
            else if (rawTemp > 0.35f) {
                return NORMAL;
            }
            else if (rawTemp > 0.0f) {
                return COLD;
            }
            else {
                return ICY;
            }
        }

        public static Temperature getFromBiome(BiomeGenBase biomeGenBase) {
            if (biomeGenBase instanceof BiomeGenHell) {
                return HELLISH;
            }
            return getFromValue(biomeGenBase.temperature);
        }

    }

    public int toInt() {
        return humidity.ordinal() * 1000 + temperature.ordinal();
    }

    public static BiomeStats fromInt(int i) {
        return new BiomeStats(Humidity.values()[i / 1000], Temperature.values()[i % 1000]);
    }
}
