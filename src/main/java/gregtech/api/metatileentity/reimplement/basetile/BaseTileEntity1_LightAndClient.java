package gregtech.api.metatileentity.reimplement.basetile;

import gregtech.api.metatileentity.BaseTileEntity;
import gregtech.api.metatileentity.MetaTileEntity;
import net.minecraft.nbt.NBTTagCompound;

public abstract class BaseTileEntity1_LightAndClient extends BaseTileEntity {

    protected MetaTileEntity metaTileEntity;
    protected int metaTileID;

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setInteger("mID", metaTileID);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        metaTileID = tag.getInteger("mID");
    }

    public boolean alive() {
        return !isDead;
    }

    protected void setMetaTileEntity(MetaTileEntity metaTileEntity) {
        this.metaTileEntity = metaTileEntity;
    }
}
