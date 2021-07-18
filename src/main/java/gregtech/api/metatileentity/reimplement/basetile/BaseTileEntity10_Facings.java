package gregtech.api.metatileentity.reimplement.basetile;

import gregtech.api.interfaces.tileentity.ITurnable;
import gregtech.api.util.GT_Utility;
import net.minecraft.nbt.NBTTagCompound;

public abstract class BaseTileEntity10_Facings extends BaseTileEntity9_MachineProgress implements ITurnable {
    protected int mainFacing = 0;
    int secondFacing = 0;

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setInteger("facing1", mainFacing);
        tag.setInteger("facing2", secondFacing);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        mainFacing = tag.getInteger("facing1");
        secondFacing = tag.getInteger("facing2");

    }

    @Override
    public byte getFrontFacing() {
        return (byte) mainFacing;
    }

    @Override
    public void setFrontFacing(byte aSide) {
        mainFacing = aSide;
    }

    @Override
    public byte getBackFacing() {
        return GT_Utility.getOppositeSide(mainFacing);
    }

    @Override
    public boolean isValidFacing(byte aSide) {
        return aSide >= 2 && aSide < 6;
    }

    @Override
    public int getSecondFacing() {
        return secondFacing;
    }

    @Override
    public void setSecondFacing(int side) {
        secondFacing = side;
    }
}
