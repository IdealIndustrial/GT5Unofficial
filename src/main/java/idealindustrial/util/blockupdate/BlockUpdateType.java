package idealindustrial.util.blockupdate;

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
