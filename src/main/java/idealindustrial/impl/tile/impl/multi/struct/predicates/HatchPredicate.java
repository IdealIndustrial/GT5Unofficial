package idealindustrial.impl.tile.impl.multi.struct.predicates;

import idealindustrial.api.textures.INetworkedTexture;
import idealindustrial.impl.textures.NetworkedTexture;
import idealindustrial.impl.tile.impl.multi.MultiMachineBase;
import idealindustrial.impl.tile.impl.multi.MultiMachineBase.HatchType;
import idealindustrial.impl.tile.impl.multi.parts.TileHatch;
import idealindustrial.api.tile.meta.Tile;
import idealindustrial.impl.tile.impl.multi.struct.CheckMachineParams;
import idealindustrial.impl.tile.impl.multi.struct.MachineStructureException;
import idealindustrial.util.misc.II_StreamUtil;
import idealindustrial.util.misc.II_TileUtil;
import idealindustrial.impl.world.util.Vector3;
import net.minecraft.block.Block;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class HatchPredicate implements MatrixCoordPredicate, BlockDependentPredicate {

    protected Set<HatchType> types = new HashSet<>();
    Block block;
    int meta, side;

    public HatchPredicate(HatchType... types) {
        this.types.addAll(Arrays.asList(types));
    }

    @Override
    public void addBlockInfo(DirectBlockPredicate directBlockPredicate) {
        if (block == null) {
            block = directBlockPredicate.block;
            meta = directBlockPredicate.meta;
            side = 2;
        }
    }

    @Override
    public void resetCounters() {

    }

    @Override
    public void checkCounters() {

    }

    @Override
    public void setChar(char ch) {

    }

    @Override
    public void apply(CheckMachineParams mode, Vector3 position, int rotation) {
        assert block != null;
        if (mode.mode == CheckMachineParams.CheckMode.CHECK) {
            Tile<?> tile = II_TileUtil.getMetaTile(mode.world, position);
            if (tile instanceof TileHatch<?, ?>) {
                TileHatch<?, ?> hatch = (TileHatch<?, ?>) tile;
                if (types.contains(hatch.getType())) {
                    hatch.setTextures(II_StreamUtil.arrayOf(new NetworkedTexture(block, meta, side), new INetworkedTexture[8]));
                    hatch.addToStructure((MultiMachineBase<?>) mode.multiTile);//todo move to face, no cast
                } else {
                    throw new MachineStructureException("Not expected this hatch here: " + position);
                }
            } else {
                MachineStructureException.notEnoughInfo();
            }
        }
    }
}
