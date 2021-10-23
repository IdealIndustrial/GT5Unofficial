package idealindustrial.blocks.plants;

import idealindustrial.util.misc.II_NBTSerializable;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Random;

public class PlantStats implements II_NBTSerializable {
    public BiomeStats biome;
    public int growth, gain;

    public PlantStats(boolean canSpread, BiomeStats biome, int growth, int gain) {
        this.biome = biome;
        this.growth = growth;
        this.gain = gain;
    }

    public PlantStats mutate(int mutateFactor, Random random) {
        return this;
    }

    @Override
    public void nbtSave(NBTTagCompound tag, String prefix) {
        tag.setInteger("gr", growth);
        tag.setInteger("ga", gain);
        tag.setInteger("biome", biome.toInt());
    }

    @Override
    public void nbtLoad(NBTTagCompound tag, String prefix) {
        growth = tag.getInteger("gr");
        gain = tag.getInteger("ga");
        biome = BiomeStats.fromInt(tag.getInteger("biome"));
    }
}
