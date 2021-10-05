package idealindustrial.tile.meta.multi.struct;

import gregtech.api.enums.Materials;
import idealindustrial.textures.INetworkedTexture;
import idealindustrial.textures.NetworkedTexture;
import idealindustrial.tile.interfaces.meta.MetaTile;
import idealindustrial.tile.meta.multi.BaseMultiMachine.HatchType;
import idealindustrial.tile.meta.multi.parts.BaseMetaTile_Hatch;
import idealindustrial.util.misc.II_StreamUtil;
import idealindustrial.util.misc.II_TileUtil;
import idealindustrial.util.worldgen.Vector3;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class HatchPredicate implements ICoordPredicate, BlockDependentPredicate {

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
    public void apply(CheckMachineParams mode, Vector3 position, int rotation) {
        assert block != null;
        if (mode.mode == CheckMachineParams.CheckMode.CHECK) {
            MetaTile<?> metaTile = II_TileUtil.getMetaTile(mode.world, position);
            if (metaTile instanceof BaseMetaTile_Hatch<?, ?>) {
                BaseMetaTile_Hatch<?, ?> hatch = (BaseMetaTile_Hatch<?, ?>) metaTile;
                if (types.contains(hatch.getType())) {
                    hatch.setTextures(II_StreamUtil.arrayOf(new NetworkedTexture(block, meta, side), new INetworkedTexture[8]));
                } else {
                    throw new MachineStructureException("Not expected this hatch here: " + position);
                }
            }
            MachineStructureException.notEnoughInfo();
        }
    }
}
