package gregtech.api.metatileentity.reimplement.basetile;


public abstract class BaseTileEntity2_Updatable extends BaseTileEntity1_LightAndClient implements IUpdatableTileEntity {

    protected long timer = 0;
    boolean running = false;

    @Override
    public long getTimer() {
        return timer;
    }


    @Override
    public void updateEntity() {
        running = true;
        super.updateEntity();
        boolean serverSide = isServerSide();
        if (timer == 0) {
            onFirstTick(timer, serverSide);
        }
        onPreTick(timer, serverSide);
        onTick(timer, serverSide);
        onPostTick(timer, serverSide);
        running = false;
    }

    @Override
    public void onFirstTick(long timer, boolean serverSide) {

    }

    @Override
    public void onPreTick(long timer, boolean serverSide) {

    }

    @Override
    public void onTick(long timer, boolean serverSide) {

    }

    @Override
    public void onPostTick(long timer, boolean serverSide) {

    }

}
