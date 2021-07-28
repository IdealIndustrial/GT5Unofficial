package idealindustrial.util.blockupdate;

import idealindustrial.tile.meta.connected.II_MetaConnected_Cable;
import idealindustrial.util.misc.II_TileUtil;

public enum BlockUpdateType {
    CABLE_SYSTEM(((world, x, y, z) -> II_TileUtil.getMetaTile(world, x, y, z) instanceof II_MetaConnected_Cable),
            ((world, x, y, z) -> ))
    ;

    private final BlockValidator validator;
    private final BlockUpdater updater;

    BlockUpdateType(BlockValidator validator, BlockUpdater updater) {
        this.validator = validator;
        this.updater = updater;
    }

    public BlockValidator getValidator() {
        return validator;
    }

    public BlockUpdater getUpdater() {
        return updater;
    }
}
