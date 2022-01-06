package idealindustrial.impl.tile.impl.multi.struct.predicates;

import idealindustrial.impl.entity.CubeRenderedParticle;
import idealindustrial.impl.tile.impl.multi.struct.CheckMachineParams;
import idealindustrial.impl.tile.impl.multi.struct.IGuideRenderer;
import idealindustrial.impl.tile.impl.multi.struct.MachineStructureException;
import idealindustrial.impl.world.util.Vector3;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

public class DirectBlockPredicate extends MatrixCoordPredicateBase {

    Block block;
    int meta, rotation, minAmount;
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
    protected void build(CheckMachineParams params, World w, Vector3 position) {
        w.setBlock(position.x, position.y, position.z, block, meta, 3);
    }

    @Override
    protected void render(CheckMachineParams params, World w, Vector3 position, IGuideRenderer renderer) {
        Minecraft.getMinecraft().effectRenderer.addEffect(new CubeRenderedParticle(w, position, renderer).setTextures(block, meta));
    }

    @Override
    protected void check(CheckMachineParams params, World w, Vector3 position) {
        if (w.getBlock(position.x, position.y, position.z) == block && (meta == -1 || w.getBlockMetadata(position.x, position.y, position.z) == meta)) {
            amount++;
        } else {
            MachineStructureException.invalidBlockAt(position);
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
        return super.or(predicate);
    }

    public DirectBlockPredicate withMin(int minAmount) {
        this.minAmount = minAmount;
        return this;
    }
}
