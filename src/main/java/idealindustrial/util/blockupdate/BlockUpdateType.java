package idealindustrial.util.blockupdate;

import idealindustrial.tile.meta.connected.II_MetaConnected_Cable;
import idealindustrial.util.misc.II_TileUtil;

public enum BlockUpdateType {

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
