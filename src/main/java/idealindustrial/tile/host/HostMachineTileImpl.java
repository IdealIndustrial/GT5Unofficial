package idealindustrial.tile.host;

import idealindustrial.tile.IOType;
import idealindustrial.tile.covers.BaseCoverBehavior;
import idealindustrial.tile.interfaces.host.HostMachineTile;
import idealindustrial.tile.interfaces.meta.Tile;
import idealindustrial.util.energy.electric.EUConsumer;
import idealindustrial.util.energy.electric.EUProducer;
import idealindustrial.util.energy.electric.EmptyEnergyHandler;
import idealindustrial.util.energy.electric.EnergyHandler;
import idealindustrial.util.fluid.EmptyTank;
import idealindustrial.util.fluid.FluidHandler;
import idealindustrial.util.fluid.FluidInventoryRepresentation;
import idealindustrial.util.fluid.FluidInventoryReprepresentationImpl;
import idealindustrial.util.inventory.EmptyInventory;
import idealindustrial.util.inventory.interfaces.InternalInventory;
import idealindustrial.util.misc.II_TileUtil;
import idealindustrial.util.misc.II_Util;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class HostMachineTileImpl extends HostTileImpl implements HostMachineTile {
    protected boolean inventoryModified = false;
    protected InternalInventory in, out;
    protected int[] slots;
    protected int inSize, outSize;
    protected boolean hasTank = false;
    protected FluidHandler inTank, outTank;
    protected boolean[] itemIO = II_Util.trueAr(12), fluidIO = II_Util.trueAr(12), energyIO = II_Util.trueAr(12);

    protected EnergyHandler handler = EmptyEnergyHandler.INSTANCE;


    protected List<Consumer<IOType>> ioListeners = new ArrayList<>();

    @Override
    public void onPreTick(long timer, boolean serverSide) {
        super.onPreTick(timer, serverSide);
        if (serverSide && (timer + 1) % 4 == 0) {
            handler.onUpdate();
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        in.nbtSave(tag, "in");
        out.nbtSave(tag, "out");

        inTank.nbtSave(tag, "in");
        outTank.nbtSave(tag, "out");

        handler.nbtSave(tag, "eu");
    }


    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        in.nbtLoad(tag, "in");
        out.nbtLoad(tag, "out");

        inTank.nbtLoad(tag, "in");
        outTank.nbtLoad(tag, "out");

        handler.nbtLoad(tag, "eu");
        if (tile != null) {
            onIOConfigurationChanged(IOType.ALL);
        }
    }

    @Override
    protected void setMetaTileEntity(Tile<?> tile) {
        super.setMetaTileEntity(tile);
        reloadIOContainers(tile);
    }

    @Override
    public void reloadIOContainers(Tile<?> tile) {
        if (this.tile.hasInventory()) {
            in = this.tile.getInputsHandler();
            out = this.tile.getOutputsHandler();
            inSize = in.size();
            outSize = out.size();
            slots = IntStream.iterate(0, i -> i + 1).limit(inSize + outSize).toArray();
        } else {
            in = out = EmptyInventory.INSTANCE;
            slots = new int[0];
        }

        if (this.tile.hasFluidTank()) {
            hasTank = true;
            inTank = this.tile.getInputTank();
            outTank = this.tile.getOutputTank();
        } else {
            inTank = outTank = EmptyTank.INSTANCE;
        }

        if (this.tile.hasEnergy()) {
            handler = this.tile.getEnergyHandler();
        } else {
            handler = EmptyEnergyHandler.INSTANCE;//todo this
        }

        onIOConfigurationChanged(IOType.ALL);
    }

    @Override
    public int getSizeInventory() {
        return inSize + outSize;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        assert index >= 0 && index < inSize + outSize;
        if (index >= inSize) {
            return out.get(index - inSize);
        }
        return in.get(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int amount) {
        assert index >= 0 && index < inSize + outSize;
        if (index >= inSize) {
            tile.onInInventoryModified(1);
            return out.reduce(index - inSize, amount);
        }
        return in.reduce(index, amount);
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        inventoryModified = true;
        assert index >= 0 && index < inSize + outSize;
        if (index >= inSize) {
            out.set(index - inSize, stack);
            tile.onInInventoryModified(1);
            return;
        }
        in.set(index, stack);
        tile.onInInventoryModified(0);
    }

    @Override
    public String getInventoryName() {
        if (alive())
            return tile.getInventoryName();
        return "";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }


    @Override
    public boolean isUseableByPlayer(EntityPlayer aPlayer) {
        return getTileEntityOffset(0, 0, 0) == this && aPlayer.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
    }


    @Override
    public int getInventoryStackLimit() {
        if (alive())
            return tile.getInventoryStackLimit();
        return 64;
    }

    @Override
    public void openInventory() {
        if (alive())
            tile.onOpenGui();
    }

    @Override
    public void closeInventory() {
        if (alive())
            tile.onCloseGui();
    }

    @Override
    public boolean isItemValidForSlot(int aIndex, ItemStack aStack) {
        return alive();
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int aSide) {
        if (itemIO[aSide] || itemIO[aSide + 6])
            return slots;
        return new int[0];
    }

    /**
     * Can put aStack into Slot at Side
     */
    @Override
    public boolean canInsertItem(int aIndex, ItemStack aStack, int aSide) {
        return itemIO[aSide] && aIndex < in.size() && in.allowInput(aIndex, aStack);
    }

    /**
     * Can pull aStack out of Slot from Side
     */
    @Override
    public boolean canExtractItem(int aIndex, ItemStack aStack, int aSide) {
        return itemIO[aSide + 6] && aIndex >= inSize && aIndex < inSize + outSize;
    }

    @Override
    public int fill(ForgeDirection aSide, FluidStack aFluid, boolean doFill) {
        if (aSide != ForgeDirection.UNKNOWN && fluidIO[aSide.ordinal()])
            return inTank.fill(aSide, aFluid, doFill);
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection aSide, FluidStack aFluid, boolean doDrain) {
        if (aSide != ForgeDirection.UNKNOWN && fluidIO[aSide.ordinal() + 6])
            return outTank.drain(aSide, aFluid, doDrain);
        return null;
    }


    @Override
    public FluidStack drain(ForgeDirection aSide, int maxDrain, boolean doDrain) {
        if (aSide != ForgeDirection.UNKNOWN && fluidIO[aSide.ordinal() + 6])
            return outTank.drain(aSide, maxDrain, doDrain);
        return null;
    }

    @Override
    public boolean canFill(ForgeDirection aSide, Fluid fluid) {
        if (aSide != ForgeDirection.UNKNOWN && fluidIO[aSide.ordinal()])
            return inTank.canFill(aSide, fluid);
        return false;
    }

    @Override
    public boolean canDrain(ForgeDirection aSide, Fluid aFluid) {
        if (aSide != ForgeDirection.UNKNOWN && fluidIO[aSide.ordinal() + 6])
            return outTank.canDrain(aSide, aFluid);
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection side) {
        int sideI = side.ordinal();
        if (hasTank && alive() && (side == ForgeDirection.UNKNOWN || (fluidIO[sideI] && fluidIO[sideI + 6])))
            return outTank.getTankInfo(side);
        return new FluidTankInfo[]{};
    }

    @Override
    public EUProducer getProducer(int side) {
        return handler.getProducer(side);
    }

    @Override
    public EUConsumer getConsumer(int side) {
        return handler.getConsumer(side);
    }

    @Override
    public Container getServerGUI(EntityPlayer player, int internalID) {
        return tile.getServerGUI(player, internalID);
    }

    @Override
    public GuiContainer getClientGUI(EntityPlayer player, int internalID) {
        return tile.getClientGUI(player, internalID);
    }


    @Override
    public int[] getInventorySizes() {
        return new int[0];
    }

    @Override
    public InternalInventory getIn() {
        return in;
    }

    @Override
    public InternalInventory getOut() {
        return out;
    }

    @Override
    public InternalInventory getSpecial() {
        return tile.getSpecialHandler();
    }

    @Override
    public FluidInventoryRepresentation getFluidRepresentation() {
        return new FluidInventoryReprepresentationImpl(II_Util.nonNull(inTank.getFluids()), II_Util.nonNull(outTank.getFluids()));
    }

    @Override
    public EnergyHandler getEnergyHandler() {
        return handler;
    }

    public void onBufferFull() {
        tile.onBufferFull();
    }

    @Override
    public void overVoltage() {

    }


    @Override
    public boolean[] getIO(IOType type) {
        switch (type) {
            case ITEM:
                return itemIO;
            case FLUID:
                return fluidIO;
            case ENERGY:
                return energyIO;
        }
        return new boolean[12];
    }

    @Override
    public boolean calculateIOatSide(int side, IOType type, boolean input) {
        return (covers[side] == null || covers[side].getIO(type, input)) && tile.getIOatSide(side, type, input);
    }

    @Override
    public void onIOConfigurationChanged(IOType type) {
        if (type.is(IOType.ITEM)) {
            updateIOArray(itemIO, IOType.ITEM);
        }
        if (type.is(IOType.FLUID)) {
            updateIOArray(fluidIO, IOType.FLUID);
        }
        if (type.is(IOType.ENERGY)) {
            updateIOArray(energyIO, IOType.ENERGY);
        }
        for (Consumer<IOType> listener : ioListeners) {
            listener.accept(type);
        }
    }

    @Override
    public void onIOConfigurationChanged(Consumer<IOType> listener) {
        ioListeners.add(listener);
    }

    protected void updateIOArray(boolean[] array, IOType type) {
        for (int i = 0; i < 6; i++) {
            array[i] = calculateIOatSide(i, type, false);
        }
        for (int i = 0; i < 6; i++) {
            array[i + 6] = calculateIOatSide(i, type, true);
        }
    }

    @Override
    public void notifyOnIOConfigChange(IOType type) {
        for (int i = 0; i < 6; i++) {
            Tile<?> machineTile = II_TileUtil.getMetaTileAtSide(this, i);
            if (machineTile != null) {
                machineTile.receiveNeighbourIOConfigChange(type);
            }
        }
    }

    @Override
    protected void setCoverAtSide(int side, int id, BaseCoverBehavior<?> cover) {
        super.setCoverAtSide(side, id, cover);
        onIOConfigurationChanged(IOType.ALL);
        notifyOnIOConfigChange(IOType.ALL);
    }

    @Override
    public boolean hasFluidTank() {
        return hasTank;
    }

    @Override
    public FluidHandler getInTank() {
        return inTank;
    }

    @Override
    public FluidHandler getOutTank() {
        return outTank;
    }

    @Override
    public boolean onRightClick(EntityPlayer player, ItemStack item, int side, float hitX, float hitY, float hitZ) {
        if (super.onRightClick(player, item, side, hitX, hitY, hitZ)) {
            return true;
        }
        if (isServerSide() && !player.isSneaking() && getServerGUI(player, 0) != null) {
            openGUI(player, 0);
            return true;
        }
        return true;
    }

    @Override
    public void placedByPlayer(EntityPlayer player) {
        if (tile != null) {
            tile.placedByPlayer(player);
        }
    }
}
