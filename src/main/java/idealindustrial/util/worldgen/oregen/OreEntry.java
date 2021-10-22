package idealindustrial.util.worldgen.oregen;

import idealindustrial.autogen.material.II_Material;
import idealindustrial.autogen.material.Prefixes;
import idealindustrial.util.item.HashedBlock;
import idealindustrial.util.item.ItemHelper;
import idealindustrial.util.worldgen.util.Range;
import idealindustrial.util.worldgen.util.Shape3D;
import net.minecraft.init.Blocks;

import java.util.HashSet;
import java.util.Set;

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
    protected double fillRatio = 70.0d;

    public OreEntry(II_Material material, Prefixes prefix, Shape3D shape, Range heightRange) {
        this.material = material;
        this.prefix = prefix;
        this.shape = shape;
        this.heightRange = heightRange;
    }
}
