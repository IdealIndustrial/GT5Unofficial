package idealindustrial.tile.impl.multi;

import gnu.trove.set.TLongSet;
import gregtech.api.interfaces.ITexture;
import idealindustrial.tile.interfaces.host.HostMachineTile;
import idealindustrial.tile.impl.TileFacing2Main;
import idealindustrial.tile.impl.multi.parts.TileHatch;
import idealindustrial.tile.impl.multi.parts.Hatch_Item.InputBus;
import idealindustrial.tile.impl.multi.parts.Hatch_Item.OutputBus;
import idealindustrial.tile.impl.multi.struct.DirectBlockPredicate;
import idealindustrial.tile.impl.multi.struct.HatchPredicate;
import idealindustrial.tile.impl.multi.struct.IStructuredMachine;
import idealindustrial.tile.impl.multi.struct.MultiMachineShape;
import idealindustrial.util.world.ChunkLoadingMonitor;
import idealindustrial.util.worldgen.Vector3;
import net.minecraft.block.Block;
import net.minecraft.world.World;

import java.util.List;

public abstract class MultiMachineBase<H extends HostMachineTile> extends TileFacing2Main<H> implements IStructuredMachine {

    protected MultiMachineShape shape;
    public int startUpSleep = 0;
    protected int awaitingChunks = 0;
    protected boolean assembled = false;

    public List<?> energyHatches, dynamoHatches, inputHatches, outputHatches;
    public List<InputBus> inputBuses;
    public List<OutputBus> outputBuses;

    public MultiMachineBase(H baseTile, String name, ITexture[] baseTextures, ITexture[] overlays) {
        super(baseTile, name, baseTextures, overlays);
        shape = getStructure();
    }

    protected MultiMachineBase(H baseTile, MultiMachineBase<?> copyFrom) {
        super(baseTile, copyFrom);
        this.shape = copyFrom.shape;
    }

    protected abstract MultiMachineShape getStructure();

    public MultiMachineShape getShape() {
        return getStructure();
    }

    @Override
    public void onPostTick(long timer, boolean serverSide) {
        if (awaitingChunks > 0) {
            startUpSleep = 10;
            return;
        }
        if (startUpSleep > 0) {
            startUpSleep--;
            return;
        }
        if (shape.checkMachine(this)) {
            assembled = true;
            onAssembled();
        }
    }

    @Override
    public void onFirstTick(long timer, boolean serverSide) {
        super.onFirstTick(timer, serverSide);
        if (serverSide) {
            TLongSet awaitingChunks = shape.getAwaitingChunks(this);
            this.awaitingChunks = awaitingChunks.size();
            this.awaitingChunks -= ChunkLoadingMonitor.getMonitor(getWorld()).requestChunks(awaitingChunks, this);
        }
    }

    @Override
    public void onRemoval() {
        if (hostTile.isServerSide()) {
            TLongSet awaitingChunks = shape.getAwaitingChunks(this);
            ChunkLoadingMonitor.getMonitor(getWorld()).removeTileFrom(awaitingChunks, this);
        }
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
    protected boolean isValidFacing(int side) {
        return side > 1;
    }

    @Override
    public Vector3 getPosition() {
        return new Vector3(hostTile.getXCoord(), hostTile.getYCoord(), hostTile.getZCoord());
    }

    @Override
    public World getWorld() {
        return hostTile.getWorld();
    }


    public void addHatch(TileHatch<?, ?> hatch, HatchType type) {
        switch (type) {
            case ItemIn:
                inputBuses.add((InputBus) hatch);
                break;
            case ItemOut:
                outputBuses.add((OutputBus) hatch);
                break;
            case FluidIn:
                break;
            case FluidOut:
                break;
            case EnergyIn:
                break;
            case EnergyOut:
                break;
            case DataIO:
                break;
            case DataStorage:
                break;
        }
    }

    public void chunkAdded() {
        awaitingChunks--;
    }

    public void chunkRemoved() {
        awaitingChunks++;
    }

    protected void onAssembled() {

    }


    //todo: make interface for each hatch type
    public enum HatchType {
        ItemIn, ItemOut,
        FluidIn, FluidOut,
        EnergyIn, EnergyOut,
        DataIO, DataStorage
    }


    public static DirectBlockPredicate blockPredicate(Block block, int meta) {
        return new DirectBlockPredicate(block, meta, 0);
    }

    public static DirectBlockPredicate blockPredicate(Block block, int meta, int minAmount) {
        return new DirectBlockPredicate(block, meta, minAmount);
    }

    public static HatchPredicate hatchPredicate(HatchType... types) {
        return new HatchPredicate(types);
    }
}
