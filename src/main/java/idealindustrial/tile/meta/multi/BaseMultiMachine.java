package idealindustrial.tile.meta.multi;

import gnu.trove.set.TLongSet;
import gregtech.api.interfaces.ITexture;
import idealindustrial.tile.interfaces.base.BaseMachineTile;
import idealindustrial.tile.interfaces.meta.MetaTile;
import idealindustrial.tile.meta.BaseMetaTile_Facing2Main;
import idealindustrial.tile.meta.multi.parts.BaseMetaTile_Hatch;
import idealindustrial.tile.meta.multi.struct.DirectBlockPredicate;
import idealindustrial.tile.meta.multi.struct.IStructuredMachine;
import idealindustrial.tile.meta.multi.struct.MultiMachineShape;
import idealindustrial.util.world.ChunkLoadingMonitor;
import idealindustrial.util.worldgen.Vector3;
import net.minecraft.block.Block;
import net.minecraft.world.World;

public abstract class BaseMultiMachine<BaseTileType extends BaseMachineTile> extends BaseMetaTile_Facing2Main<BaseTileType> implements IStructuredMachine {

    protected MultiMachineShape shape;
    public int startUpSleep = 0;
    protected int awaitingChunks = 0;

    public BaseMultiMachine(BaseTileType baseTile, String name, ITexture[] baseTextures, ITexture[] overlays) {
        super(baseTile, name, baseTextures, overlays);
        shape = getStructure();
    }

    protected BaseMultiMachine(BaseTileType baseTile, BaseMultiMachine<?> copyFrom) {
        super(baseTile, copyFrom);
        this.shape = copyFrom.shape;
    }

    protected abstract MultiMachineShape getStructure();

    public MultiMachineShape getShape() {
        return getStructure();
    }

    @Override
    public void onPostTick(long timer, boolean serverSide) {
        if (startUpSleep > 0) {
            startUpSleep--;
            return;
        }
    }

    @Override
    public void onFirstTick(long timer, boolean serverSide) {
        super.onFirstTick(timer, serverSide);
        TLongSet awaitingChunks = shape.getAwaitingChunks(this);
        this.awaitingChunks = awaitingChunks.size();
        ChunkLoadingMonitor.getMonitor(getWorld()).requestChunks(awaitingChunks, this);
    }


    @Override
    public int getRotation() {
        switch (mainFacing) {
            case 2:
                return 2;
            case 3:
                return 0;
            case 4:
                return 1;
            case 5:
                return -1;
            default:
                return 0;
        }
    }

    @Override
    public Vector3 getPosition() {
        return new Vector3(baseTile.getXCoord(), baseTile.getYCoord(), baseTile.getZCoord());
    }

    @Override
    public World getWorld() {
        return baseTile.getWorld();
    }

    public static DirectBlockPredicate blockPredicate(Block block, int meta) {
        return new DirectBlockPredicate(block, meta, 0);
    }

    public static DirectBlockPredicate blockPredicate(Block block, int meta, int minAmount) {
        return new DirectBlockPredicate(block, meta, minAmount);
    }

    public void addHatch(BaseMetaTile_Hatch<?,?> hatch, HatchType type) {

    }

    public void chunkAdded() {
        awaitingChunks--;
    }

    public void chunkRemoved() {
        awaitingChunks++;
    }

    public enum HatchType {
        ItemIn, ItemOut,
        FluidIn, FluidOut,
        EnergyIn, EnergyOut,
        DataIO, DataStorage
    }
}
