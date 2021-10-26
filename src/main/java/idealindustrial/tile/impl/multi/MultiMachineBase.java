package idealindustrial.tile.impl.multi;

import gnu.trove.set.TLongSet;
import idealindustrial.textures.ITexture;
import idealindustrial.tile.impl.TileFacing2Main;
import idealindustrial.tile.impl.multi.parts.Hatch_Energy.EnergyHatch;
import idealindustrial.tile.impl.multi.parts.Hatch_Item.InputBus;
import idealindustrial.tile.impl.multi.parts.Hatch_Item.OutputBus;
import idealindustrial.tile.impl.multi.parts.TileHatch;
import idealindustrial.tile.impl.multi.struct.DirectBlockPredicate;
import idealindustrial.tile.impl.multi.struct.HatchPredicate;
import idealindustrial.tile.impl.multi.struct.IStructuredMachine;
import idealindustrial.tile.impl.multi.struct.MultiMachineShape;
import idealindustrial.tile.interfaces.host.HostMachineTile;
import idealindustrial.util.energy.electric.EmptyEnergyHandler;
import idealindustrial.util.energy.electric.MultiEnergyHandler;
import idealindustrial.util.fluid.EmptyTank;
import idealindustrial.util.inventory.EmptyInventory;
import idealindustrial.util.inventory.StupidMultipartInv;
import idealindustrial.util.misc.II_DirUtil;
import idealindustrial.util.world.ChunkLoadingMonitor;
import idealindustrial.util.worldgen.util.Vector3;
import net.minecraft.block.Block;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class MultiMachineBase<H extends HostMachineTile> extends TileFacing2Main<H> implements IStructuredMachine {

    protected MultiMachineShape shape;
    public int startUpSleep = 0;
    protected int awaitingChunks = 0;
    protected boolean assembled = false, structUpdate = true;

    public List<EnergyHatch> energyHatches = new ArrayList<>();
    public List<?> dynamoHatches = new ArrayList<>(), inputHatches = new ArrayList<>(), outputHatches = new ArrayList<>();
    public List<InputBus> inputBuses = new ArrayList<>();
    public List<OutputBus> outputBuses = new ArrayList<>();

    public MultiMachineBase(H baseTile, String name, ITexture[] baseTextures, ITexture[] overlays) {
        super(baseTile, name, baseTextures, overlays);
        shape = getStructure();
    }

    protected MultiMachineBase(H baseTile, MultiMachineBase<?> copyFrom) {
        super(baseTile, copyFrom);
        this.shape = copyFrom.getShape();
    }

    protected abstract MultiMachineShape getStructure();

    public MultiMachineShape getShape() {
        return getStructure();
    }

    @Override
    public void onPostTick(long timer, boolean serverSide) {
        if (serverSide && (!assembled || structUpdate)) {
            if (awaitingChunks > 0) {
                startUpSleep = 10;
                return;
            }
            if (startUpSleep > 0) {
                startUpSleep--;
                return;
            }
            if (structUpdate && checkMachine()) {
                assembled = true;
                onAssembled();
            }
            structUpdate = false;
        }
    }

    protected boolean checkMachine() {
        for (List<?> list : Arrays.asList(energyHatches, dynamoHatches, inputBuses, inputHatches, outputBuses, outputHatches)) {
            list.clear();
        }
        return shape.checkMachine(this);
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
        return II_DirUtil.getRotationForDirectionFromNormal(mainFacing);
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
                energyHatches.add((EnergyHatch) hatch);
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

        inventoryIn = new StupidMultipartInv(inputBuses.stream().map(b -> b.getHost().getIn()).collect(Collectors.toList()));
        inventoryOut = new StupidMultipartInv(outputBuses.stream().map(b -> b.getHost().getOut()).collect(Collectors.toList()));
        inventorySpecial = EmptyInventory.INSTANCE;

        MultiEnergyHandler handler = new MultiEnergyHandler();
        energyHatches.forEach(h -> handler.addIn(h.energyHandler));
        energyHandler = handler.isEmpty() ? EmptyEnergyHandler.INSTANCE : handler;

        tankIn = EmptyTank.INSTANCE;
        tankOut = EmptyTank.INSTANCE;

    }

    @Override
    public void receiveStructureUpdate() {
        structUpdate = true;
        startUpSleep = 50;
        System.out.println("block update receiveed");
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
