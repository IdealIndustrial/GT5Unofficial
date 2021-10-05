package idealindustrial.tile.meta.multi.struct;

import idealindustrial.entity.CubeRenderedParticle;
import idealindustrial.tile.meta.multi.BaseMultiMachine;
import idealindustrial.tile.meta.multi.BaseMultiMachine.HatchType;
import idealindustrial.util.worldgen.Vector3;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DirectBlockPredicate implements ICoordPredicate {

    final Block block;
    final int meta, rotation, minAmount;
    int amount;

    public DirectBlockPredicate(Block block, int meta, int minAmount) {
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
    public ICoordPredicate or(ICoordPredicate predicate) {
        if (predicate instanceof BlockDependentPredicate) {
            ((BlockDependentPredicate) predicate).addBlockInfo(this);
        }
        return ICoordPredicate.super.or(predicate);
    }
}
