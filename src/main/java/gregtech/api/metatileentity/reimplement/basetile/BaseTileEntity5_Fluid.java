package gregtech.api.metatileentity.reimplement.basetile;

import gregtech.api.metatileentity.MetaTileEntity;
import idealindustrial.util.fluid.II_FluidHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public abstract class BaseTileEntity5_Fluid extends BaseTileEntity4_Inventory implements IFluidHandler {

    boolean hasTank = false;
    II_FluidHandler inTank, outTank;

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        inTank.nbtSave(tag, "in");
        outTank.nbtSave(tag, "out");
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        inTank.nbtLoad(tag, "in");
        outTank.nbtLoad(tag, "out");
    }

    @Override
    protected void setMetaTileEntity(MetaTileEntity metaTileEntity) {
        super.setMetaTileEntity(metaTileEntity);
        if (metaTileEntity.hasFluidTank()) {
            hasTank = true;
            inTank = metaTileEntity.getInputTank();
            outTank = metaTileEntity.getOutputTank();
        }
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
        return false;
    }

    protected boolean letsFluidOut(int side) {
        return false;
    }
}
