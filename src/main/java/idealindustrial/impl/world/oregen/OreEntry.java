package idealindustrial.impl.world.oregen;

import cpw.mods.fml.common.IWorldGenerator;
import idealindustrial.impl.autogen.material.II_Material;
import idealindustrial.impl.autogen.material.Prefixes;
import idealindustrial.impl.item.stack.HashedBlock;
import idealindustrial.util.misc.ItemHelper;
import idealindustrial.api.world.util.Range;
import idealindustrial.api.world.util.Shape3D;
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
