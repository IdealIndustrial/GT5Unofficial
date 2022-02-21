package idealindustrial.impl.world;

import idealindustrial.api.world.underbedrock.GridGenerationRules;
import idealindustrial.api.world.underbedrock.OrderGenerator;
import idealindustrial.api.world.underbedrock.VeinProvider;
import idealindustrial.api.world.underbedrock.WeightedRandom;
import idealindustrial.impl.autogen.material.II_Material;
import idealindustrial.impl.autogen.material.II_Materials;
import idealindustrial.impl.autogen.material.Prefixes;
import idealindustrial.impl.world.underbedrock.ChunkStorageProvider;
import idealindustrial.impl.world.underbedrock.GridChunk;
import idealindustrial.impl.world.underbedrock.RandomOrder;
import idealindustrial.util.misc.NBTSerializer;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Random;

public class UndergroundOre implements ChunkStorageProvider.DataHandler {

    private II_Material material;
    private Prefixes prefix;

    private long quantity;

    public UndergroundOre(II_Material material, Prefixes prefix, long quantity) {
        this.material = material;
        this.prefix = prefix;
        this.quantity = quantity;
    }

    private UndergroundOre() {

    }

    private GridChunk<?> gridChunk;

    public II_Material getMaterial() {
        return material;
    }

    public Prefixes getPrefix() {
        return prefix;
    }

    public long getQuantity() {
        return quantity;
    }

    @Override
    public void loaded(GridChunk<?> gridChunk) {
        this.gridChunk = gridChunk;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
        gridChunk.setModified(true);
    }

    public static class Serializer implements NBTSerializer<UndergroundOre> {

        @Override
        public UndergroundOre load(NBTTagCompound nbt) {
            UndergroundOre ore = new UndergroundOre();
            ore.quantity = nbt.getLong("quantity");
            ore.material = II_Materials.materialForID(nbt.getInteger("matID"));
            ore.prefix = Prefixes.values()[nbt.getInteger("prefID")];
            return ore;
        }

        @Override
        public NBTTagCompound save(UndergroundOre undergroundOre, NBTTagCompound nbt) {
            nbt.setLong("quantity", undergroundOre.quantity);
            nbt.setInteger("matID", undergroundOre.material.getID());
            nbt.setInteger("prefID", undergroundOre.prefix.ordinal());
            return nbt;
        }
    }
}
