package idealindustrial.tile.meta.multi.struct;

import idealindustrial.entity.II_CubeRenderedParticle;
import idealindustrial.util.worldgen.Vector3;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

public class DirectBlockPredicate implements ICoordPredicate{

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
                Minecraft.getMinecraft().effectRenderer.addEffect(new II_CubeRenderedParticle(w, position, params.renderer));
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
}
