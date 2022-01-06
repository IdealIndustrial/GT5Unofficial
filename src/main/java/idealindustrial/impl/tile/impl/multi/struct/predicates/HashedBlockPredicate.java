package idealindustrial.impl.tile.impl.multi.struct.predicates;

import idealindustrial.impl.entity.CubeRenderedParticle;
import idealindustrial.impl.item.stack.HashedBlock;
import idealindustrial.impl.tile.impl.multi.struct.CheckMachineParams;
import idealindustrial.impl.tile.impl.multi.struct.IGuideRenderer;
import idealindustrial.impl.tile.impl.multi.struct.MachineStructureException;
import idealindustrial.impl.world.util.Vector3;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

import java.util.Set;

public class HashedBlockPredicate extends DirectBlockPredicate {

    Set<HashedBlock> blocks;

    public HashedBlockPredicate(Set<HashedBlock> blocks) {
        this(blocks.iterator().next(), blocks);
    }

    public HashedBlockPredicate(HashedBlock main, Set<HashedBlock> blocks) {
        super(main.getBlock(), main.getMeta(), 0);
        this.blocks = blocks;
    }


    @Override
    protected void check(CheckMachineParams params, World w, Vector3 position) {
        HashedBlock b = new HashedBlock(w.getBlock(position.x, position.y, position.z), w.getBlockMetadata(position.x, position.y, position.z));
        if (!blocks.contains(b)) {
            MachineStructureException.invalidBlockAt(position);
        }
    }

    @Override
    public void resetCounters() {

    }

    @Override
    public void checkCounters() {

    }
}
