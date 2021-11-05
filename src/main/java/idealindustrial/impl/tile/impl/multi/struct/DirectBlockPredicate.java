package idealindustrial.impl.tile.impl.multi.struct;

import idealindustrial.impl.entity.CubeRenderedParticle;
import idealindustrial.impl.world.util.Vector3;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

public class DirectBlockPredicate implements MatrixCoordPredicate {

    final Block block;
    final int meta, rotation, minAmount;
    int amount;

    public DirectBlockPredicate(Block block, int meta, int minAmount) {
        assert block != null && meta < 16;
        this.block = block;
        this.meta = meta;
        this.minAmount = minAmount;
        this.rotation = -1;
    }

    public DirectBlockPredicate(Block block, int meta, int rotation, int minAmount) {
        this.block = block;
        this.meta = meta;
        this.rotation = rotation;
        this.minAmount = minAmount;
    }


    @Override
    public void apply(CheckMachineParams params, Vector3 position, int rotation) {
        World w = params.world;
        switch (params.mode) {
            case BUILD:
                w.setBlock(position.x, position.y, position.z, block, meta, 3);
                break;
            case CHECK:
                if (w.getBlock(position.x, position.y, position.z) == block && (meta == -1 || w.getBlockMetadata(position.x, position.y, position.z) == meta)) {
                    amount++;
                } else {
                    MachineStructureException.invalidBlockAt(position);
                }
            case RENDER:
                Minecraft.getMinecraft().effectRenderer.addEffect(new CubeRenderedParticle(w, position, params.renderer).setTextures(block, meta));
                break;
        }
    }

    @Override
    public void resetCounters() {
        amount = 0;
    }

    @Override
    public void checkCounters() {
        if (amount < minAmount) {
            //todo throw not enough blocks
        }
    }

    @Override
    public MatrixCoordPredicate or(MatrixCoordPredicate predicate) {
        if (predicate instanceof BlockDependentPredicate) {
            ((BlockDependentPredicate) predicate).addBlockInfo(this);
        }
        return MatrixCoordPredicate.super.or(predicate);
    }
}
