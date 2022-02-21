package idealindustrial.impl.tile.module;

import idealindustrial.api.tile.host.HostTile;
import idealindustrial.api.tile.module.TileModule;
import idealindustrial.impl.item.stack.HashedBlock;
import idealindustrial.impl.world.util.Vector3;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;

public class MinerModule implements TileModule {

    enum Mode {
        Surface,
        Underbedrock
    }

    int radius;

    Mode mode = Mode.Surface;
    int currBlockHardness = -1;
    Vector3 position;
    HostTile host;


    @Override
    public void useEnergy(long voltage) {
        //todo: impl
    }

    @Override
    public void useKineticEnergy(int speed, double satisfaction) {

    }

    protected void work(int amount) {
        if (mode == Mode.Surface) {
            if (currBlockHardness > 0) {
                currBlockHardness -= amount;
                return;
            }
            setMiningPipe(position);
            position.addm(0, -1, 0);
            HashedBlock block = host.getHBlock(position);
            if (position.y < 0 || block.getBlock() == Blocks.bedrock) {
                mode = Mode.Underbedrock;
                return;
            }
            currBlockHardness = 200;
        } else {

        }
    }

    protected void setMiningPipe(Vector3 pos) {

    }

    @Override
    public void onPostTick(long timer, boolean serverSide) {

    }

    @Override
    public void saveToNBT(String prefix, NBTTagCompound nbt) {

    }

    @Override
    public void loadFromNBT(String prefix, NBTTagCompound nbt) {

    }

}
