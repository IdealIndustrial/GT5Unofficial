package idealindustrial.util.worldgen.oregen;

import cpw.mods.fml.common.IWorldGenerator;
import idealindustrial.autogen.material.II_Material;
import idealindustrial.autogen.material.Prefixes;
import idealindustrial.blocks.plants.PlantDef;
import idealindustrial.util.item.HashedBlock;
import idealindustrial.util.item.ItemHelper;
import idealindustrial.util.worldgen.util.Range;
import idealindustrial.util.worldgen.util.Shape3D;
import net.minecraft.init.Blocks;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;

public class OreEntry {
    protected static Set<HashedBlock> defaultBlocks = ItemHelper.querySet(new HashSet<>());
    static {
        defaultBlocks.add(new HashedBlock(Blocks.stone, 0));
    }


    protected II_Material material;
    protected Prefixes prefix;
    protected Shape3D shape;
    protected Range heightRange;
    protected Set<HashedBlock> baseBlocks = defaultBlocks;
    protected IWorldGenerator onSuccess = null;
    protected double fillRatio = 70.0d;

    public OreEntry(II_Material material, Prefixes prefix, Shape3D shape, Range heightRange) {
        this.material = material;
        this.prefix = prefix;
        this.shape = shape;
        this.heightRange = heightRange;
    }

    public OreEntry addGenerator(IWorldGenerator generator) {
        onSuccess = generator;
        return this;
    }
}
