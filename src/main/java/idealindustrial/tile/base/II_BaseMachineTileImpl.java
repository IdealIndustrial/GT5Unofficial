package idealindustrial.tile.base;

import idealindustrial.tile.meta.II_MetaTile;
import idealindustrial.util.energy.EUConsumer;
import idealindustrial.util.energy.EUProducer;
import idealindustrial.util.energy.II_EmptyEnergyHandler;
import idealindustrial.util.energy.II_EnergyHandler;
import idealindustrial.util.fluid.II_EmptyTank;
import idealindustrial.util.fluid.II_FluidHandler;
import idealindustrial.util.fluid.II_FluidInvReprImpl;
import idealindustrial.util.fluid.II_FluidInventoryRepresentation;
import idealindustrial.util.inventory.II_EmptyInventory;
import idealindustrial.util.inventory.II_InternalInventory;
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

import java.util.stream.IntStream;

public class II_BaseMachineTileImpl extends II_BaseTileImpl implements II_BaseMachineTile {
    protected boolean inventoryModified = false;
    protected II_InternalInventory in, out;
    protected int[] slots;
    protected int inSize, outSize;
    protected boolean hasTank = false;
    protected II_FluidHandler inTank, outTank;
    protected boolean[] itemIO = II_Util.trueAr(6), fluidIO = II_Util.trueAr(6), energyIO = II_Util.trueAr(6);

    protected II_EnergyHandler handler = new II_EmptyEnergyHandler();

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        in.nbtSave(tag, "in");
        out.nbtSave(tag, "out");

        inTank.nbtSave(tag, "in");
        outTank.nbtSave(tag, "out");

        handler.nbtLoad(tag, "eu");
    }


    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        in.nbtLoad(tag, "in");
        out.nbtLoad(tag, "out");

        inTank.nbtLoad(tag, "in");
        outTank.nbtLoad(tag, "out");

        handler.nbtLoad(tag, "eu");
    }

    @Override
    protected void setMetaTileEntity(II_MetaTile metaTileEntity) {
        super.setMetaTileEntity(metaTileEntity);
        if (metaTileEntity.hasInventory()) {
            in = metaTileEntity.getInputsHandler();
            out = metaTileEntity.getOutputsHandler();
            inSize = in.size();
            outSize = out.size();
            slots = IntStream.iterate(0, i -> i + 1).limit(inSize + outSize).toArray();
        } else {
            in = out = II_EmptyInventory.INSTANCE;
            slots = new int[0];
        }

        if (metaTileEntity.hasFluidTank()) {
            hasTank = true;
            inTank = metaTileEntity.getInputTank();
            outTank = metaTileEntity.getOutputTank();
        } else {
            inTank = outTank = II_EmptyTank.INSTANCE;
        }
        handler = new II_EmptyEnergyHandler();//todo this
    }

    @Override
    public int getSizeInventory() {
        if (alive()) {
            return inSize + outSize;
        }
        return 0;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        if (alive()) {
            assert index >= 0 && index < inSize + outSize;
            if (index >= inSize) {
                return out.get(index - inSize);
            }
            ItemStack is = in.get(index);
            return is;
        }
        return null;
    }

    @Override
    public ItemStack decrStackSize(int index, int amount) {
        if (alive()) {
            assert index >= 0 && index < inSize + outSize;
            if (index >= inSize) {
                return out.reduce(index - inSize, amount);
            }
            return in.reduce(index, amount);
        }
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        inventoryModified = true;
        if (alive()) {
            assert index >= 0 && index < inSize + outSize;
            if (index >= inSize) {
                out.set(index - inSize, stack);
                return;
            }
            in.set(index, stack);
        }
    }

    @Override
    public String getInventoryName() {
        if (alive())
            return metaTileEntity.getInventoryName();
        return "";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }


    @Override
    public boolean isUseableByPlayer(EntityPlayer aPlayer) {
        return alive() && timer > 5 && getTileEntityOffset(0, 0, 0) == this && aPlayer.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
    }


    @Override
    public int getInventoryStackLimit() {
        if (alive())
            return metaTileEntity.getInventoryStackLimit();
        return 64;
    }

    @Override
    public void openInventory() {
        if (alive())
            metaTileEntity.onOpenGui();
    }

    @Override
    public void closeInventory() {
        if (alive())
            metaTileEntity.onCloseGui();
    }

    @Override
    public boolean isItemValidForSlot(int aIndex, ItemStack aStack) {
        return alive();
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int aSide) {
        if (alive() && letsItemsOut(aSide) && letsItemsOut(aSide))
            return slots;
        return new int[0];
    }

    /**
     * Can put aStack into Slot at Side
     */
    @Override
    public boolean canInsertItem(int aIndex, ItemStack aStack, int aSide) {
        return alive() && letsItemsIn(aSide) && aIndex < in.size() && in.allowInput(aIndex, aStack);
    }

    /**
     * Can pull aStack out of Slot from Side
     */
    @Override
    public boolean canExtractItem(int aIndex, ItemStack aStack, int aSide) {
        return alive() && letsItemsOut(aSide) && aIndex >= inSize && aIndex < inSize + outSize;
    }

    protected boolean letsItemsIn(int side) {
        return true;
    }

    protected boolean letsItemsOut(int side) {
        return true;
    }

    @Override
    public int fill(ForgeDirection aSide, FluidStack aFluid, boolean doFill) {
        if (allowIn(aSide))
            return inTank.fill(aSide, aFluid, doFill);
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection aSide, FluidStack aFluid, boolean doDrain) {
        if (allowOut(aSide))
            return outTank.drain(aSide, aFluid, doDrain);
        return null;
    }


    @Override
    public FluidStack drain(ForgeDirection aSide, int maxDrain, boolean doDrain) {
        if (allowOut(aSide))
            return outTank.drain(aSide, maxDrain, doDrain);
        return null;
    }

    @Override
    public boolean canFill(ForgeDirection aSide, Fluid fluid) {
        if (allowIn(aSide))
            return inTank.canFill(aSide, fluid);
        return false;
    }

    @Override
    public boolean canDrain(ForgeDirection aSide, Fluid aFluid) {
        if (allowOut(aSide))
            return outTank.canDrain(aSide, aFluid);
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection aSide) {
        if (hasTank && alive() && (aSide == ForgeDirection.UNKNOWN || (letsFluidIn(aSide.ordinal()) && letsFluidOut(aSide.ordinal()))))
            return outTank.getTankInfo(aSide);
        return new FluidTankInfo[]{};
    }

    private boolean allowIn(ForgeDirection aSide) {
        return hasTank && timer > 5 && alive() && (aSide == ForgeDirection.UNKNOWN || letsFluidIn(aSide.ordinal()));
    }

    private boolean allowOut(ForgeDirection aSide) {
        return hasTank && timer > 5 && alive() && (aSide == ForgeDirection.UNKNOWN || (letsFluidOut(aSide.ordinal())));
    }


    protected boolean letsFluidIn(int side) {
        return true;
    }

    protected boolean letsFluidOut(int side) {
        return true;
    }

    @Override
    public long injectEnergyUnits(byte aSide, long aVoltage, long aAmperage) {
        return 0;
    }

    @Override
    public boolean inputsEnergyFrom(byte aSide) {
        return alive() && lestEnergyIn(aSide) && getConsumer(aSide) != null;
    }

    @Override
    public boolean inputsEnergyFrom(byte aSide, boolean waitForActive) {
        return inputsEnergyFrom(aSide);
    }

    @Override
    public boolean outputsEnergyTo(byte aSide) {
        return alive() && lestEnergyOut(aSide) && getProducer(aSide) != null;
    }

    @Override
    public boolean outputsEnergyTo(byte aSide, boolean waitForActive) {
        return outputsEnergyTo(aSide);
    }

    @Override
    public byte getColorization() {
        return (byte) color;
    }

    @Override
    public byte setColorization(byte aColor) {
        return (byte) (color = aColor);
    }

    @Override
    public EUProducer getProducer(int side) {
        return handler.getProducer(side);
    }

    @Override
    public EUConsumer getConsumer(int side) {
        return handler.getConsumer(side);
    }

    protected boolean lestEnergyIn(int side) {
        return false;
    }

    protected boolean lestEnergyOut(int side) {
        return false;
    }


    @Override
    public Container getServerGUI(EntityPlayer player, int internalID) {
        return metaTileEntity.getServerGUI(player, internalID);
    }

    @Override
    public GuiContainer getClientGUI(EntityPlayer player, int internalID) {
        return metaTileEntity.getClientGUI(player, internalID);
    }


    @Override
    public int[] getInventorySizes() {
        return new int[0];
    }

    @Override
    public II_InternalInventory getIn() {
        return in;
    }

    @Override
    public II_InternalInventory getOut() {
        return out;
    }

    @Override
    public II_InternalInventory getSpecial() {
        return metaTileEntity.getSpecialHandler();
    }

    @Override
    public II_FluidInventoryRepresentation getFluidRepresentation() {
        return new II_FluidInvReprImpl(II_Util.nonNull(inTank.getFluids()), II_Util.nonNull(outTank.getFluids()));
    }

    @Override
    public II_EnergyHandler getEnergyHandler() {
        return handler;
    }

    @Override
    public boolean hasFluidTank() {
        return hasTank;
    }

    @Override
    public II_FluidHandler getInTank() {
        return inTank;
    }

    @Override
    public II_FluidHandler getOutTank() {
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
        return false;
    }
}
