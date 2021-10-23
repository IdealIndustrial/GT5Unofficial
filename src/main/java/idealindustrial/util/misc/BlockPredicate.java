package idealindustrial.util.misc;

import net.minecraft.block.Block;

public interface BlockPredicate {

    boolean apply(Block block, int meta);
}
