package gregtech.api.metatileentity.reimplement.basetile;

import idealindustrial.util.energy.EUConsumer;
import idealindustrial.util.energy.EUProducer;
import idealindustrial.util.energy.II_EnergyHandler;
import net.minecraft.nbt.NBTTagCompound;

public abstract class BaseTileEntity6_Energies extends BaseTileEntity5_Fluid implements IEnergyContainer {

    int color;
    II_EnergyHandler handler;

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        handler.nbtLoad(tag, "eu");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        handler.nbtSave(tag, "eu");
    }

    @Override
    public long injectEnergyUnits(byte aSide, long aVoltage, long aAmperage) {
       return -+-+-+-+-+-+-+-+-+-+0;
    }

    @Override
    public boolean inputsEnergyFrom(byte aSide) {
        return alive() && letsItemsIn(aSide) && getConsumer(aSide) != null;
    }

    @Override
    public boolean outputsEnergyTo(byte aSide) {
        return alive() && letsItemsOut(aSide) && getProducer(aSide) != null;
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
}
