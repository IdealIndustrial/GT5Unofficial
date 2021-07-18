package gregtech.api.metatileentity.reimplement.basetile;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import gregtech.api.GregTech_API;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.tileentity.ICoverable;
import gregtech.api.objects.GT_ItemStack;
import gregtech.api.util.GT_CoverBehavior;
import gregtech.api.util.GT_Utility;
import idealindustrial.util.misc.II_StreamUtil;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class BaseTileEntity8_Covers extends BaseTileEntity7_Redstone implements ICoverable {
    protected GT_CoverBehavior[] coverBehaviors = new GT_CoverBehavior[6];
    protected int[] coverIDs = new int[6];
    protected int[] coverData = new int[6];
    protected boolean coverUpdate = false;

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setIntArray("covers", coverIDs);
        tag.setIntArray("coverData", coverData);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        coverData = tag.getIntArray("coverData");
        coverIDs = tag.getIntArray("covers");
        coverData = coverData.length != 6 ? new int[6] : coverData;
        coverIDs = coverIDs.length != 6 ? new int[6] : coverIDs;
        for (int i = 0; i < 6; i++) {
            coverBehaviors[i] = GregTech_API.getCoverBehavior(coverIDs[i]);
        }
    }

    @Override
    public boolean canPlaceCoverIDAtSide(byte aSide, int aID) {
        return coverIDs[aSide] == 0;
    }

    @Override
    public boolean canPlaceCoverItemAtSide(byte aSide, ItemStack aCover) {
        return coverIDs[aSide] == 0;
    }

    @Override
    public boolean dropCover(byte aSide, byte aDroppedSide, boolean aForced) {
        if (getCoverBehaviorAtSide(aSide).onCoverRemoval(aSide, getCoverIDAtSide(aSide), coverData[aSide], this, aForced) || aForced) {
            ItemStack tStack = getCoverBehaviorAtSide(aSide).getDrop(aSide, getCoverIDAtSide(aSide), getCoverDataAtSide(aSide), this);
            if (tStack != null) {
                tStack.setTagCompound(null);
                EntityItem tEntity = new EntityItem(worldObj, getOffsetX(aDroppedSide, 1) + 0.5, getOffsetY(aDroppedSide, 1) + 0.5, getOffsetZ(aDroppedSide, 1) + 0.5, tStack);
                tEntity.motionX = 0;
                tEntity.motionY = 0;
                tEntity.motionZ = 0;
                worldObj.spawnEntityInWorld(tEntity);
            }
            setCoverIDAtSide(aSide, 0);
            if (metaTileEntity.hasSidedRedstoneOutputBehavior()) {
                setOutputRedstoneSignal(aSide, (byte) 0);
            } else {
                setOutputRedstoneSignal(aSide, (byte) 15);
            }
            return true;
        }
        return false;
    }

    @Override
    public void setCoverDataAtSide(byte aSide, int aData) {
        coverData[aSide] = aData;
    }

    @Override
    public void setCoverIDAtSide(byte aSide, int aID) {
        coverIDs[aSide] = aID;
        coverBehaviors[aSide] = GregTech_API.getCoverBehavior(aID);
    }

    @Override
    public void setCoverItemAtSide(byte aSide, ItemStack aCover) {
        GregTech_API.getCoverBehavior(aCover).placeCover(aSide, aCover, this);
    }

    @Override
    public int getCoverDataAtSide(byte aSide) {
        return coverData[aSide];
    }

    @Override
    public int getCoverIDAtSide(byte aSide) {
        return coverIDs[aSide];
    }

    @Override
    public ItemStack getCoverItemAtSide(byte aSide) {
        return GT_Utility.intToStack(getCoverIDAtSide(aSide));
    }

    @Override
    public GT_CoverBehavior getCoverBehaviorAtSide(byte aSide) {
        return coverBehaviors[aSide];
    }

    @Override
    public byte getInternalInputRedstoneSignal(byte aSide) {
        return 0;
    }

    @Override
    public void setInternalOutputRedstoneSignal(byte aSide, byte aStrength) {

    }

    @Override
    public void onPreTick(long timer, boolean serverSide) {
        if (timer < 10 || !serverSide) {
            return;
        }
        for (byte i = 0; i < 6; i++)
            if (getCoverIDAtSide(i) != 0) {
                GT_CoverBehavior tCover = getCoverBehaviorAtSide(i);
                int tCoverTickRate = tCover.getTickRate(i, getCoverIDAtSide(i), coverData[i], this);
                if (tCoverTickRate > 0 && timer % tCoverTickRate == 0) {
                    coverData[i] = tCover.doCoverThings(i, getInputRedstoneSignal(i), getCoverIDAtSide(i), coverData[i], this, timer);

                }
            }
    }

    @Override
    public void onFirstTick(long timer, boolean serverSide) {
        super.onFirstTick(timer, serverSide);
        if (serverSide) for (byte i = 0; i < 6; i++)
            if (getCoverIDAtSide(i) != 0)
                if (!metaTileEntity.allowCoverOnSide(i, new GT_ItemStack(getCoverIDAtSide(i))))
                    dropCover(i, i, true);
    }

    @Override
    public void issueCoverUpdate(byte aSide) {
        coverUpdate = true;
    }

    @Override
    protected boolean letsItemsIn(int side) {
        if (coverIDs[side] == 0) {
            return true;
        }
        return coverBehaviors[side].letsItemsIn((byte) side, coverIDs[side], coverData[side], -1, this);
    }

    @Override
    protected boolean letsItemsOut(int side) {
        if (coverIDs[side] == 0) {
            return true;
        }
        return coverBehaviors[side].letsItemsOut((byte) side, coverIDs[side], coverData[side], -1, this);
    }

    @Override
    protected boolean letsFluidIn(int side) {
        if (coverIDs[side] == 0) {
            return true;
        }
        return coverBehaviors[side].letsFluidIn((byte) side, coverIDs[side], coverData[side], null, this);
    }

    @Override
    protected boolean letsFluidOut(int side) {
        if (coverIDs[side] == 0) {
            return true;
        }
        return coverBehaviors[side].letsFluidOut((byte) side, coverIDs[side], coverData[side], null, this);
    }

    @Override
    protected boolean lestEnergyIn(int side) {
        if (coverIDs[side] == 0) {
            return true;
        }
        return coverBehaviors[side].letsEnergyIn((byte) side, coverIDs[side], coverData[side],  this);
    }

    @Override
    protected boolean lestEnergyOut(int side) {
        if (coverIDs[side] == 0) {
            return true;
        }
        return coverBehaviors[side].letsEnergyOut((byte) side, coverIDs[side], coverData[side],  this);
    }


    public ITexture getCoverTexture(byte aSide) {
        return GregTech_API.sCovers.get(new GT_ItemStack(getCoverIDAtSide(aSide)));
    }

    @Override
    public void writeClientData(ByteArrayDataOutput data) {
        super.writeClientData(data);
        II_StreamUtil.writeIntArray(coverIDs, data);
        II_StreamUtil.writeIntArray(coverData, data);
    }

    @Override
    public void readClientData(ByteArrayDataInput data) {
        super.readClientData(data);
        coverIDs = II_StreamUtil.readIntArray(data);
        coverData = II_StreamUtil.readIntArray(data);
    }
}
