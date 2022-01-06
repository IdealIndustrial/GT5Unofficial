package idealindustrial.impl.item.stack;

import idealindustrial.util.misc.ItemHelper;
import net.minecraft.block.Block;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


/**
 * class for use in HashMap, ignores NBT and NBT when comparing
 */
public class HashedBlock implements ItemHelper.Rehashable {
    protected int hash;
    protected int blockID;
    protected final int meta;
    protected final Block block;

    public HashedBlock(Block block, int meta) {
        this.block = block;
        this.meta = meta;
        this.blockID = Block.getIdFromBlock(block);
        this.hash = blockID | (meta << 16);
    }

    public HashedBlock(int hash) {
        meta = (hash & 0xFFFF0000) >>> 16;
        blockID = hash & 0x0000FFFF;
        this.hash = hash;
        block = Block.getBlockById(blockID);
    }

    public static Set<HashedBlock> anyMeta(Block... blocks) {
        Set<HashedBlock> out = new HashSet<>();
        for (Block b : blocks) {
            for (int i = 0; i < 16; i++) {
                out.add(new HashedBlock(b, i));
            }
        }
        return out;
    }

    public int getMeta() {//untested, so todo: check
        return (hash & 0xFFFF0000) >>> 16;
    }

    public Block getBlock() {
        return block;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HashedBlock that = (HashedBlock) o;
        return hash == that.hash;
    }

    @Override
    public int hashCode() {
        return hash;
    }

    public void fixHash() {
        blockID = Block.getIdFromBlock(block);
        this.hash = blockID | (meta << 16);
    }

}
