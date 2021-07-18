package gregtech.api.metatileentity.reimplement.basetile;

import gregtech.api.interfaces.tileentity.IRedstoneTileEntity;

public abstract class BaseTileEntity7_Redstone extends BaseTileEntity6_Energies implements IRedstoneTileEntity {

    byte[] sidedRedstone = new byte[]{0,0,0,0,0,0};
    boolean needsBlockUpdate = false;
    byte strongRedstone = 0;
    boolean redstoneEnabled = true;

    @Override
    public void setGenericRedstoneOutput(boolean aOnOff) {
        redstoneEnabled = aOnOff;
    }

    @Override
    public void issueBlockUpdate() {
        needsBlockUpdate = true;
    }

    @Override
    public byte getOutputRedstoneSignal(byte aSide) {
        return 0;
    }

    @Override
    public void setOutputRedstoneSignal(byte aSide, byte aStrength) {
        sidedRedstone[aSide] = aStrength;
    }

    @Override
    public byte getStrongOutputRedstoneSignal(byte aSide) {
        return 0;
    }

    @Override
    public void setStrongOutputRedstoneSignal(byte aSide, byte aStrength) {

    }

    @Override
    public byte getComparatorValue(byte aSide) {
        if (!alive()) {
            return 0;
        }
        return metaTileEntity.getComparatorValue(aSide);
    }

    @Override
    public byte getInputRedstoneSignal(byte aSide) {
        return 0;
    }

    @Override
    public byte getStrongestRedstone() {
        return 0;
    }

    @Override
    public boolean getRedstone() {
        return false;
    }

    @Override
    public boolean getRedstone(byte aSide) {
        return false;
    }
}
